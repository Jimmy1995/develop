package com.lonema.demo2;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @DESC 测试RedisUtil类
 * @AUTHOR JIANGCW
 * @DATE 2018-1-9下午03:57:31
 */
public class Test {
	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(1000 * 6);
		config.setMaxWaitMillis(1000 * 10);
		config.setTestOnBorrow(true);
		
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("",6379);
		info.setPassword("dream123");
		JedisShardInfo info2 = new JedisShardInfo("",6379);
		info2.setPassword("dream123");
		shards.add(info);
		shards.add(info2);
		
		RedisUtil re=new RedisUtil();
		ShardedJedisPool redisPool = new ShardedJedisPool(config, shards);
		re.setShardedJedisPool(redisPool);
		
		re.setString("name", "lonema");
		String s = re.getString("name");
		System.out.println(s);
	}
}
