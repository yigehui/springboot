# springboot
> 写一个springboot的项目练手,基本实现数据库的增删改查功能

## 基本的配置
```
server.port=8888

//mybatis实体类扫描
mybatis.type-aliases-package=com.yigehui.springboot.enty

//mybatis下划线转驼峰
mybatis.configuration.mapUnderscoreToCamelCase=true

//打印日志级别
logging.level.com.yigehui.springboot.mapper=debug

// 数据库配置信息
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/yigehui?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = 1234
```

## 配置文件

### 配置文件

```yaml
cat:
  name: mimi
  age: 10
  city: 郑州
```

javaBean

```java
/**
 * 将配置文件中的属性映射到这个组件中
 * 只有是容器中的组件，才能实现@ConfigurationProperties(prefix = "Cat")的功能
 */
@Component
@ConfigurationProperties(prefix = "cat")

public class Cat {

    private String name;
    private String age;
    private String city;
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
```

可以导入配置文件处理器，以后编写配置就有提示

```xml
 <!--导入配置文件中的提示-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```

### 配置文件注入

1. properties配置文件在idea中默认utf-8可能会乱码
2. @Value获取值和@ConfigurationProperties获取值比较

|            | @ConfigurationProperties | @Value     |
| ---------- | ------------------------ | ---------- |
| 功能       | 批量注入配置文件的属性   | 一个个指定 |
| 松散绑定   | 支持                     | 不支持     |
| SpEl       | 不支持                   | 支持       |
| Jsr303校验 | 支持                     | 不支持     |

### @PropertySource&@ImportResource

@PropertySource:加载指定的配置文件

```java
/**
 * 将配置文件中的属性映射到这个组件中
 * 只有是容器中的组件，才能实现@ConfigurationProperties(prefix = "Cat")的功能
 */
@PropertySource(value = {"classpath:cat.properties"})
@Component
@ConfigurationProperties(prefix = "cat")

public class Cat {

//    @Value("${cat.name}")
    private String name;
//    @Value("#{3*4}")
    private String age;
//    @Value("cat.city")
    private String city;
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
```

**@ImportResource**:导入spring的配置文件，让配置文件中的内容生效

Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；想让Spring的配置文件生效，加载进来，**@ImportResource**标注在一个配置类上

```java
@ImportResource(locations = {"classpath:bean.xml"})
导入Spring的配置文件让其生效
```

配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">



        <bean id="helloService" class="com.yigehui.springboot.sevrvice.HelloService"></bean>
</beans>
```





Spring Boot推荐给容器中添加组件的方式：推荐使用全注解的方式

1. 配置类======Spring配置文件
2. 使用@Bean给Spring添加组件

```java

/**
 * @Configuration 指明当前类是配置类：就是替代之前的配置文件
 *
 * 在配置文件中用<bean></bean>添加组件
 */
@Configuration
public class MyAppConfig {

    //将方法的返回值添加到容器中；容器中这个组件的ID就是方法名
    @Bean
    public HelloService helloService(){
        System.out.println("配置类@Bean给容器添加组件了...");
        return  new HelloService();
    }


}
```

### 配置文件占位符

1. 随机数

```java
${random.value}、${random.int}、${random.long}
${random.int(10)}、${random.int[1024,65536]}
```



2. 占位符

```properties
cat.name=弋戈辉${random.uuid}
cat.age=20
cat.city=河南${cat.name}
```

### Profile

#### 多Profile文件

我们在主配置文件编写的时候，文件名可以是application-{profile}.properties/yml

默认使用application.properties的配置;

#### yml支持多文档方式

```yaml
server:
  port: 8888
spring:
  profiles:
    active: dev
---
server:
  port: 8081

spring:
  profiles: dev
---
server:
  port: 8082

spring:
  profiles: prod
---
```

#### 激活指定profile

1. 在配置文件中指定spring.profiles.active = dev

2. 命令行：

   -- spring.profiles.active = dev

### 配置文件加载位置

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的你默认配置文件

-file:./config/

-file:./

-classpath:/config/

-classpath:/

优先级由高到低，高优先级的配置会覆盖低优先级的配置

SpringBoot会从这四个位置全部加载主配置文件：**互补配置**

==还可以通过spring.config.location来改变默认的配置文件位置==

项目打包好以后，可以使用命令行参数的形式，启动项目的时候来指定配置文件的新位置；指定配置文件和默认加载的这些配置文件共同起作用，形成互补配置；

### 外部加载配置顺序

**==SpringBoot也可以从以下位置加载配；优先级从高到低；高优先级的配置覆盖低优先级的配置，所有的配置会形成互补配置==**

1. 命令行参数
2. 来自java:comp/env的JNDI属性
3. java系统属性（system.getProperties()）
4. 操作系统的环境变量
5. RandomValuePropertySource配置的random.*属性
6. jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件
7. jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件
8. jar包外部的application.properties或application.yml(不带spring.profile)配置文件
9. jar包内部的application.properties或application.yml(不带spring.profile)配置文件
10. @Configuration注解类上的@PropertySource
11. 通过SpringApplication.setDefaultProperties指定的默认属性

### 自动配置原理

配置文件到底能写什么？怎么写？自动配置原理；

[官方文档](https://docs.spring.io/spring-boot/docs/2.0.9.RELEASE/reference/htmlsingle/#appendix)

#### 自动配置原理

1. SpringBoot启动的时候加载主配置类，开启了自动配置的功能==@EnableAutoConfiguration==

2. **@EnableAutoConfiguration作用：**

   -  利用AutoConfigurationImportSelector给容器中导入一些组件

   - 可以查看selectImports()方法的内容

   - List<String> configurations = getCandidateConfigurations(annotationMetadata,
     				attributes);获取候选的配置

   - ```java
     SpringFactoriesLoader.loadFactoryNames
     扫描所有jar包类路径下 META-INF/spring.factories
     把扫描到的这些文件的内容包装秤properties对象
     从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，把他们添加到容器中
     ```

     

**==将类路径下 META-INF/spring.factories 里面配的所有EnableAutoConfiguration的值加入到容器中；==**

```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
org.springframework.boot.autoconfigure.reactor.core.ReactorCoreAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityRequestMatcherProviderAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
```

每一个这样的xxxAutoConfigurtion类都是容器中的一个组件，都加入到容器中；用他们来自动配置

3. 每一个自动配置类进行自动配置的功能；
4. **以HttpEncodingAutoConfiguration为例解释自动配置原理**

```java
@Configuration //表示一个配置类，以前编写配置文件一样，也可以给容器中添加组件
@EnableConfigurationProperties(HttpProperties.class) //启动指定类的ConfigurationProperties功能；将配置文件红对应的值和HttpProperties.class绑定起来
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET) // Spring底层@Conditional注解，根据不同的条件，如果满足hiding的条件，整个配置类的配置就生效；判断当前是否是web应用，如果有，当代配置生效
@ConditionalOnClass(CharacterEncodingFilter.class) //判断当前项目有没有这个类CharacterEncodingFilter；SpringMvc中进行乱码解决的过滤器；
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled",
		matchIfMissing = true) //判断配置文件是否存在某个配置 spring.http.encoding.enabled;如果不存在，判断也是成立的
//即使我们配置文件中不配置spring.http.encoding.enabled=true,也是默认生效的
public class HttpEncodingAutoConfiguration {
    
    //他已经和SpringBoot的配置文件映射了
    private final HttpProperties.Encoding properties;
    
    //只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(HttpProperties properties) {
		this.properties = properties.getEncoding();
	}

    
    @Bean //给容器中添加一个组件，这个组件的值需要从properties文件中取
	@ConditionalOnMissingBean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
		return filter;
	}
```

根据当前的不同的条件判断，决定这个配置类是否生效？

一旦配置生效；这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中取得，这些类里面的每一个属性又和配置文件绑定的；





5. 所有在配置文件红能配置的属性都是在XXXXProperties类中封装着；配置文件能配置什么可以参照某个功能对应的这个属性类

```java
@ConfigurationProperties(prefix = "spring.http") //从配置文件中获取指定的值和bean的属性进行绑定
public static class Encoding {
	
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
```



精髓：

​	**1）、springboot启动会加载大量的自动配置类**

​	**2）、我们看我们需要的功能有没有springboot默认写好的配置类；**

​	**3）、我们再来看这个自动配置类中到底配置了那些组件；（只要我们用的组件有，我们不需要再配置了）**

​	**4）、给容器红自动配置类添加组件的时候，会从properties类红获取某些属性。我们就可以在配置文件中指定这些属性的值；**

xxxxAutoConfiguration：自动配置类；

给容器中添加组件

xxxxProperties：封装配置文件中相关属性；



#### 细节

如果想看到springboot加载了哪些自动配置可以在application.properties文件中添加`debug=true`

```java

// 匹配到的自动配置
Positive matches:
-----------------

   CodecsAutoConfiguration matched:
      - @ConditionalOnClass found required class 'org.springframework.http.codec.CodecConfigurer' (OnClassCondition)

   CodecsAutoConfiguration.JacksonCodecConfiguration matched:
      - @ConditionalOnClass found required class 'com.fasterxml.jackson.databind.ObjectMapper' (OnClassCondition)

   CodecsAutoConfiguration.JacksonCodecConfiguration#jacksonCodecCustomizer matched:
      - @ConditionalOnBean (types: com.fasterxml.jackson.databind.ObjectMapper; SearchStrategy: all) found bean 'jacksonObjectMapper' (OnBeanCondition)
          
   
//没有匹配到的自动配置          
Negative matches:
-----------------

   ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

   AopAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.aspectj.lang.annotation.Aspect' (OnClassCondition)

```



## 日志







