## 踩坑记录
1. Redis版本问题
![img.png](src/main/resources/img.png)
2. Application启动失败：  
因为class中定义了两个bean，产生了冲突需要解决。可选方案是：在其中一个bean上加上@Primary注解
```
***************************
APPLICATION FAILED TO START
***************************

Description:

Constructor in org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration required a single bean, but 2 were found:
	- defaultRedisConfig: defined by method 'defaultRedisConfig' in class path resource [com/github/philsongzi/demos/spring/redis/config/RedisAutoConfig$DefaultRedisConfig.class]
	- localRedisConfig: defined by method 'localRedisConfig' in class path resource [com/github/philsongzi/demos/spring/redis/config/RedisAutoConfig$LocalRedisConfig.class]


Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```