package com.cone.redis.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置类
 * @author Cone
 *
 */
@Configuration
//@PropertySource("classpath:redis.properties")
//@Slf4j
public class RedisConfig {
//	//redis服务器地址
//	@Value("${spring.redis.host}")
//	private String host;
//	//端口
//	@Value("${spring.redis.port}")
//	private int port;
//	//密码
//	@Value("${spring.redis.password}")
//	private String password;
//	//连接超时时间（毫秒）
//	@Value("${spring.redis.timeout}")
//	private int timeout;
//	//连接池中的最大空闲连接
//	@Value("${spring.redis.jedis.pool.max-idle}")
//	private int maxIdle;
//	//连接池最大阻塞等待时间（使用负值表示没有限制）
//	@Value("${spring.redis.jedis.pool.max-wait}")
//	private int maxWaitMillis;
//	//redis配置结束
//	@Value("${spring.redis.block-when-exhausted}")
//	private boolean blockWhenExhausted;
//	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
//	@Value("${spring.redis.testOnBorrow}")
//	private boolean testOnBorrow;
	
	
//	@Bean
//	public JedisPool redisPoolFactory() {
//		log.info("JedisPool注入成功！");
//		log.info("redis地址" + host + ":" + port);
//		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//		jedisPoolConfig.setMaxIdle(maxIdle);
//		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//		jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
//		jedisPoolConfig.setJmxEnabled(true);
//		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
//		return jedisPool;
//	}
	
	
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//		jedisConnectionFactory.setPort(6379);
//		jedisConnectionFactory.setHostName("127.0.0.1");
//		return jedisConnectionFactory;
//	}
	
	

	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		//key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		//hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		//value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
