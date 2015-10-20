/**
 *
 */
package com.appabc.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月2日 下午3:37:51
 */
public class RedisHelper {
	
	private LogUtil log = LogUtil.getLogUtil(RedisHelper.class);
	
	private ShardedJedisPool shardedJedisPool;

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
	
	/**
	 * 使用此方法获取的连接将需要自己关闭
	 * @return
	 */
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}


	/**
	 * 添加/覆盖
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value){
		ShardedJedis resource = getResource();
		String val = resource.set(key, value);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public String getString(String key){
		ShardedJedis resource = getResource();
		String val = resource.get(key);
		returnResource(resource);
		return val;
	}
	
	public int getInt(String key){
		try {
			return Integer.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}
	
	public long getLong(String key){
		try {
			return Long.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}
	
	public float getFloat(String key){
		try {
			return Float.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}
	
	public Double getDouble(String key){
		try {
			return Double.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return 0.0;
		}
	}
	
	public Short getShort(String key){
		try {
			return Short.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}
	
	public Byte getByte(String key){
		try {
			return Byte.valueOf(getString(key));
		} catch (NumberFormatException e) {
			log.error(e);
			return null;
		}
	}
	
	/**
	 * 添加/覆盖（带时间限制）
	 * @param key
	 * @param value
	 * @param seconds 有效时长(单位：秒)
	 * @return
	 */
	public String set(String key, String value, int seconds){
		ShardedJedis resource = getResource();
		String val = resource.setex(key, seconds, value);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public Long del(String key){
		ShardedJedis resource = getResource();
		Long val = resource.del(key);
		returnResource(resource);
		return val;
	}
	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public Long del(byte[] key){
		ShardedJedis resource = getResource();
		Long val = resource.del(key);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 追加，将新value追加到oldValue之后（oldValueValue）
	 * @param key
	 * @param value
	 * @return
	 */
	public Long append (String key, String value){
		ShardedJedis resource = getResource();
		Long val = resource.append(key, value);
		returnResource(resource);
		return val;
	}
	
	
	/**
	 * 存储一组键值
	 * @param key
	 * @param hashMap
	 * @return
	 */
	public String hmset(String key, Map<String , String> hashMap){
		ShardedJedis resource = getResource();
		String val = resource.hmset(key, hashMap);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 存储一组btye类型的MAP对象
	 * @param key
	 * @param hash
	 * @return
	 */
	public String hmset(byte[] key, Map<byte[],byte[]> hash){
		ShardedJedis resource = getResource();
		String val = resource.hmset(key, hash);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 获取一组btye类型的MAP对象
	 * @param key
	 * @return
	 */
	public Map<byte[],byte[]> hgetAll(byte[] key){
		ShardedJedis resource = getResource();
		Map<byte[],byte[]> val = resource.hgetAll(key);
		returnResource(resource);
		return val;
	}
	
	
	/**
	 * 获取HashMap中一到多个指定键值
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> hmget(String key, String... fields){
		ShardedJedis resource = getResource();
		List<String> val = resource.hmget(key, fields);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 删除HashMap中一到多个指定键值
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(String key, String... fields){
		ShardedJedis resource = getResource();
		Long val = resource.hdel(key, fields);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 获取存放值的个数
	 * @param key
	 * @return
	 */
	public Long hlen(String key){
		ShardedJedis resource = getResource();
		Long val = resource.hlen(key);
		returnResource(resource);
		return val;
	}
	
	
	/**
	 * 是否存在key的记录
	 * @param key
	 * @return true/false
	 */
	public boolean exists(String key){
		ShardedJedis resource = getResource();
		boolean val = resource.exists(key);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 返回Map对象中的所有key
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key){
		ShardedJedis resource = getResource();
		Set<String> val = resource.hkeys(key);
		returnResource(resource);
		return val;
	}
	
	/**
	 * 返回Map对象中的所有值
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key){
		ShardedJedis resource = getResource();
		List<String> val = resource.hvals(key);
		returnResource(resource);
		return val;
	}
	
	public boolean hexists(byte[] key, byte[] field){
		ShardedJedis resource = getResource();
		boolean val = resource.hexists(key, field);
		returnResource(resource);
		return val;
	}
	
	public byte[] hget(byte[] key, byte[] field) {
		ShardedJedis resource = getResource();
		byte[] val = resource.hget(key, field);
		returnResource(resource);
		return val;
	}
	
	public Long hdel(byte[] key, byte[]... fields) {
		ShardedJedis resource = getResource();
		Long val = resource.hdel(key, fields);
		returnResource(resource);
		return val;
	}
	
	
	/**
	 * @return
	 */
	private ShardedJedis getResource(){
		return this.shardedJedisPool.getResource();
	}
	
	/**
	 * 释放连接
	 * @param resource
	 */
	private void returnResource(ShardedJedis resource) {
		this.shardedJedisPool.returnResource(resource);
	}

}
