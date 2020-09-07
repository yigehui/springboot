package com.yigehui.springboot.sevrvice;

import com.yigehui.springboot.enty.Person;
import com.yigehui.springboot.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonMapper personMapper;


    public List<Person> getAll(){
        return personMapper.getAll();
    }

    /**
     *
     * 将方法的运行结果进行缓存
     * CacheManager管理多个Cache组件，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有一个唯一的名字
     *
     * 属性：
     *      cacheNames/value：缓存组件的名字
     *
     *      key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值
     *          支持SpEl； #id 参数id的值 #a0 #p0 #root.args[0]
     *
     *      keyGenerator：key的生成器；可以指定key的生成器的组件id
     *          key/keyGenerator 二选一使用
     *
     *      cacheManager：指定缓存管理器；或者指定cacheResolver获取解析器
     *      condition：指定符合条件的情况下才缓存；
     *          condition = "#id>0"
     *
     *      unless:否定缓存；当unless指定条件为true。方法返回值就不会被缓存；可以获取到结果进行判断
     *
     *      sync：是否使用异步模式
     *
     *
     *原理：
     *
     *  1、自动配置类：CacheAutoConfiguration
     *  2、缓存的配置类
     *  org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     *  org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *  3、那个配置类生效：SimpleCacheConfiguration
     *
     *  4、给容器注册了一个CacheManager: ConcurrentMapCacheManager
     *  5、可以获取和创建ConcurrentMapCache类型的缓存组件；他可以作用将数据保存在ConcurrentMap中；
     *
     *  运行流程：
     *  @Cacheable
     *  1、方法运行之前先去查询Cache，按照cacheNames指定的名字获取
     *  2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数
     *      key是按照某种策略生成的，默认使用KeyGenerator
     *      public static Object generateKey(Object... params) {
     *         if (params.length == 0) {
     *             return SimpleKey.EMPTY;
     *         } else {
     *             if (params.length == 1) {
     *                 Object param = params[0];
     *                 if (param != null && !param.getClass().isArray()) {
     *                     return param;
     *                 }
     *             }
     *
     *             return new SimpleKey(params);
     *         }
     *     }
     *
     *
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "person")
    public Person get(Long id){
        System.out.println("查询"+id+"号员工");
        return personMapper.get(id);
    }

    /**
     * @CachePut: 即调用方法, 有更新缓存数据
     * 修改了数据库的某个数据，同时更新缓存
     * 注意这里的key值要跟缓存中的对应
     * #result.personId
     * #p.personId
     * 两个是一个意思
     * 因为这是先执行结果后更新缓存
     */
    @CachePut(cacheNames = "person",key="#p.personId")
    public Person update(Person p){
        return personMapper.update(p);

    }


    /**
     * @CacheEvict: 缓存清楚
     * key:指定清除的数据
     * allEntries = true; 指定清除这个缓存中所有的数据
     * beforeInvocation = false; 缓存删除在方法之前执行
     * 防止方法执行出错无法删除缓存
     */
    @CacheEvict(cacheNames = "person" ,beforeInvocation = true)
    public void delete(Long id){
        System.out.println("删除员工"+id);
        //personMapper.delete(id);
        int a = 10/0;
    }


}
