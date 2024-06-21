package com.taipham.studentmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.experimental.NonFinal;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
  @NonFinal
  @Value("${spring.data.redis.host}")
  protected String HOST;

  @NonFinal
  @Value("${spring.data.redis.port}")
  protected int PORT;

  @NonFinal
  @Value("${spring.data.redis.password}")
  protected String PASSWORD;
  @Bean
  public JedisConnectionFactory redisConnectionFactory() {

    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(HOST, PORT);
    // config.setPassword("AXfpAAIncDFlMjE4MjNiMGI0MGU0ZDJjOTFiNzhmMzQwNTVlYjQ1MXAxMzA2OTc");
    return new JedisConnectionFactory(config);

    // JedisPoolConfig poolConfig = new JedisPoolConfig();
    //     JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
    //     factory.setHostName(HOST);
    //     factory.setPort(PORT);
    //     factory.setPassword(PASSWORD);
    //     return factory;
  }

  // @Bean
  //   public RedisConnectionFactory redisConnectionFactory() {
  //     LettuceConnectionFactory factory = new LettuceConnectionFactory(HOST, PORT);
  //     factory.validateConnection();
  //     return factory;
  //   }

  @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Sử dụng StringRedisSerializer cho key
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Sử dụng GenericJackson2JsonRedisSerializer cho value
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
