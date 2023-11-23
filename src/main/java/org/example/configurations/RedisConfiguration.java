package org.example.configurations;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.example.models.NewRating;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRedisRepositories
@EnableCaching
public class RedisConfiguration {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new JedisConnectionFactory(configuration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, NewRating> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, NewRating> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    @Bean
    public GenericObjectPoolConfig<?> genericObjectPoolConfig() {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(10); // Максимальное количество соединений в пуле
        poolConfig.setMaxIdle(5);   // Максимальное количество неактивных соединений в пуле
        poolConfig.setMinIdle(1);   // Минимальное количество неактивных соединений в пуле
        poolConfig.setTestOnBorrow(true); // Проверять соединение при извлечении из пула
        return poolConfig;
    }

    @Bean
    public RedisCacheManager redisCacheManager(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory);
        RedisCacheConfiguration nrsCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1));
        RedisCacheConfiguration nsCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5));
        Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("nrs",nrsCacheConfiguration);
        cacheConfigurations.put("ns",nsCacheConfiguration);
        return new RedisCacheManager(redisCacheWriter, RedisCacheConfiguration.defaultCacheConfig(),cacheConfigurations);
    }


}
