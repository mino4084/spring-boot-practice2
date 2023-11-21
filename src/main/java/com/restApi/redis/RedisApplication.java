package com.restApi.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class RedisApplication {

	/**
	 * RestTemplate 클래스는 RedisAccessor 클래스를 상속하는 RedisOperations 인터페이스의 구현체
	 */
	/*
	CrudReposiotry 사용으로 빈 등록 삭제
	@Bean
	public RedisOperations<String, Aircraft> redisOperations(RedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Aircraft> serializer = new Jackson2JsonRedisSerializer<Aircraft>(Aircraft.class);

		RedisTemplate<String, Aircraft> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setDefaultSerializer(serializer);
		template.setKeySerializer(new StringRedisSerializer());

		return template;
	}
	 */

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}


}