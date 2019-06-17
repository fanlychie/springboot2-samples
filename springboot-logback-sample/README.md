#### Spring Boot Logback

---

##### 配置文件`application.yml`配置样例

```yaml
## 日志级别
  # logging.level.* = TRACE/DEBUG/INFO/WARN/ERROR/FATAL/OFF
  # "*"可以是根级别"root"(如果没有特殊的指定, 就采用此作为默认的), 也可以是指定的包名称(特殊指定)
## 日志存储 (超过10M生成一个新的文件)
  # logging.file = logs/logfile.log (相对项目根目录的路径)
  # logging.file = /path/logs/logfile.log (绝对路径)
  # logging.path = path/logs (相对项目根目录的路径, 默认的日志文件名称为spring.log)
  # logging.path = /path/logs (绝对路径, 默认的日志文件名称为spring.log)
## 日志格式
  # logging.pattern.console: 自定义控制台的日志输出格式 (参考README说明)
  # logging.pattern.file:    自定义日志文件的日志输出格式 (参考README说明)
logging:
  # 日志级别
  level:
    root: INFO
    org.springframework: INFO
  # 日志存储
  file: my.log
  # 日志格式
  pattern:
    console: "%date{yyyy-MM-dd HH:mm:ss} - [%-5level] [%-8thread] %-36logger{36} : %msg%n"
    file: "%date{yyyy-MM-dd HH:mm:ss} - [%-5level] [%-8thread] %-36logger{36} : %msg%n"
```

##### 日志格式

| 参数 | 描述 |
| :---- | :---- |
| %c{length}<br>%logger{length} | 输出所属的类目，通常就是所在类的全名 |
| %d{pattern}<br>%date{pattern} | 输出日志的日期或时间 |
| %F<br>%file | 输出发出日志记录请求的Java源文件的文件名 |
| %L<br>%line | 输出日志事件的发生行号信息 |
| %m<br>%msg<br>%message | 输出程序代码中指定的消息 |
| %p<br>%le<br>%level | 输出日志级别信息 |
| %t<br>%thread | 输出产生该日志事件的线程名 |
| %n | 输出一个平台的回车换行符 |
