package com.taipham.studentmanagement.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.taipham.studentmanagement.dto.request.AuthenticationRequest;
import com.taipham.studentmanagement.dto.request.IntrospectRequest;
import com.taipham.studentmanagement.dto.request.LogoutRequest;
import com.taipham.studentmanagement.dto.request.RefreshRequest;
import com.taipham.studentmanagement.dto.response.AuthenticationResponse;
import com.taipham.studentmanagement.dto.response.IntrospectResponse;
import com.taipham.studentmanagement.entity.InvalidatedToken;
import com.taipham.studentmanagement.entity.User;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.InvalidatedTokenRepository;
import com.taipham.studentmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNKEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder().authenticated(authenticated).token(token).build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException{
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token,false);
        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    public void logout(LogoutRequest request) throws JOSEException, ParseException{
        try {
            var signToken = verifyToken(request.getToken(),false);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                                                                    .id(jit)
                                                                    .expiryTime(signToken.getJWTClaimsSet().getExpirationTime())
                                                                    .build();
            String key = "InvalidatedToken:" + invalidatedToken.getId();
            invalidatedTokenRepository.save(invalidatedToken);
            redisTemplate.expire(key, REFRESHABLE_DURATION, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("e", e);
            log.info("Token already expired");
        }
    }

    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException{
        JWSVerifier verifier = new MACVerifier(SIGNKEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = (isRefresh)
            ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli())
            :signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        log.info(signedJWT.getJWTClaimsSet().getClaim("scope").toString());

        if(!(verified && expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException{
        
        var signJWT = verifyToken(request.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                                                                .id(jit)
                                                                .expiryTime(signJWT.getJWTClaimsSet().getExpirationTime())
                                                                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        String key = "InvalidatedToken:" + invalidatedToken.getId();
        redisTemplate.expire(key,REFRESHABLE_DURATION, TimeUnit.SECONDS);
        var username = signJWT.getJWTClaimsSet().getSubject();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);
        return AuthenticationResponse.builder().authenticated(true).token(token).build();
    }

    String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                        .subject(user.getUsername())
                                        .issuer("taipham.com")
                                        .issueTime(new Date())
                                        .expirationTime(new Date(Instant.now().plus(VALID_DURATION,ChronoUnit.SECONDS).toEpochMilli()))
                                        .jwtID(UUID.randomUUID().toString())
                                        .claim("scope", buildScope(user))
                                        .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNKEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token", e);
            throw new RuntimeException(e);
        }
    }

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                log.info(role.getName());
                stringJoiner.add("ROLE_"+role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }
    
    
}
