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


