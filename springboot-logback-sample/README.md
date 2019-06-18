#### Spring Boot Logback

---

##### 配置样例

在`application.yml`中配置如下：

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
| `%c{length}`<br>`%logger{length}` | 输出所属的类目，通常就是所在类的全名 |
| `%d{pattern}`<br>`%date{pattern}` | 输出日志的日期或时间 |
| `%F`<br>`%file` | 输出发出日志记录请求的Java源文件的文件名 |
| `%L`<br>`%line` | 输出日志事件的发生行号信息 |
| `%m`<br>`%msg`<br>`%message` | 输出程序代码中指定的消息 |
| `%p`<br>`%le`<br>`%level` | 输出日志级别信息 |
| `%t`<br>`%thread` | 输出产生该日志事件的线程名 |
| `%n` | 输出一个平台的回车换行符 |

> `%logger{length}`：如果信息内容长度大于给定的`length`的值，则保留内容最右边的完整单词，左边的每个单词只保留第一个字符。如`org.fanlychie.DemoService`(25个字符长度)示例：<br><table><thead><tr><th>示例</th><th>结果</th></tr></thead><tbody><tr><td>%logger</td><td>org.fanlychie.DemoService</td></tr><tr><td>%logger{0}</td><td>DemoService</td></tr><tr><td>%logger{10}</td><td>o.f.DemoService</td></tr><tr><td>%logger{24}</td><td>o.fanlychie.DemoService</td></tr><tr><td>%logger{100}</td><td>org.fanlychie.DemoService</td></tr></tbody></table>

> `%date{pattern}`：模式语法与`java.text.SimpleDateFormat`兼容。如`%date{yyyy-MM-dd HH:mm:ss}`

##### 日志信息宽度和对齐方式

以`%logger`为样例，如下：

| 示例 | 描述 |
| :---- | :---- |
| %50logger | 右对齐，最小的宽度是50，长度不足50则左补空格，长度超出50则原样输出 |
| %-50logger | ‘-‘表示向左对齐，最小的宽度是50，长度不足50则右补空格，长度超出50则原样输出 |
| %.20logger | 左对齐，最大的宽度是20，如果长度超出20，将左边多出的字符直接丢掉 |
| %30.50logger | 最小宽度是30，如果长度不足30则左补空格右对齐；如果长度超出30且不足50则左对齐；如果长度超出50则将左边多出的字符直接丢掉 |

##### 扩展Logback

`Spring Boot`允许我们通过扩展`Logback`进行更高级的配置。`Spring Boot`默认从类路径加载的日志配置文件次序为：

1. `logback-spring.xml`
2. `logback-spring.groovy`
3. `logback.xml`
4. `logback.groovy`

单元测试使用的日志配置文件为`logback-test.xml`。

在类路径上创建`logback.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <!-- 自定义属性信息 -->
    <property name="LOG_PATTERN" value="%date{yyyy-MM-dd HH:mm:ss} - [%-5level] [%-8thread] %-36logger{36} : %msg%n"/>
    <property name="LOG_FILE" value="my.log"/>
    <property name="ERROR_LOG_FILE" value="error.log"/>
    <property name="LOG_FILE_MAX_SIZE" value="1MB"/>
    <property name="LOG_FILE_KEEP_MAX_DAYS" value="30"/>
    <!-- 控制台日志配置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 日志文件输出格式 -->
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <!-- 文件日志配置 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <encoder>
            <!-- 日志文件输出格式 -->
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
        <!-- 日志文件存储位置 -->
        <file>${LOG_FILE}</file>
        <!-- 日志文件滚动策略 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 历史日志名称 -->
            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd_HH-mm}.%i</fileNamePattern>
            <!-- 单个日志文件允许写入的最大文件大小 -->
            <maxFileSize>${LOG_FILE_MAX_SIZE}</maxFileSize>
            <!-- 历史日志文件最大保留的天数 -->
            <maxHistory>${LOG_FILE_KEEP_MAX_DAYS}</maxHistory>
        </rollingPolicy>
    </appender>
    <!-- 文件日志配置 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 只记录ERROR级别的日志信息 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 设置过滤级别 -->
            <level>ERROR</level>
            <!-- 用于配置符合过滤条件的操作 -->
            <onMatch>ACCEPT</onMatch>
            <!-- 用于配置不符合过滤条件的操作 -->
            <onMismatch>DENY</onMismatch>
        </filter>
        <encoder>
            <!-- 日志文件输出格式 -->
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
        <!-- 日志文件存储位置 -->
        <file>${ERROR_LOG_FILE}</file>
        <!-- 日志文件滚动策略 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 历史日志名称 -->
            <fileNamePattern>${ERROR_LOG_FILE}.%d{yyyy-MM-dd_HH-mm}.%i</fileNamePattern>
            <!-- 单个日志文件允许写入的最大文件大小 -->
            <maxFileSize>${LOG_FILE_MAX_SIZE}</maxFileSize>
            <!-- 历史日志文件最大保留的天数 -->
            <maxHistory>${LOG_FILE_KEEP_MAX_DAYS}</maxHistory>
        </rollingPolicy>
    </appender>
    <!-- rootLogger(如果没有特殊的指定, 就采用此作为默认的) -->
    <root level="INFO">
        <!-- 写到控制台 -->
        <appender-ref ref="CONSOLE"/>
        <!-- 写到文件中 -->
        <appender-ref ref="FILE"/>
        <!-- 只记录ERROR级别的日志 -->
        <appender-ref ref="ERROR_FILE"/>
    </root>
    <!-- 多环境日志配置 -->
    <!-- 开发和测试环境 -->
    <springProfile name="dev,test">
        <!-- 自定义logger -->
        <!-- additivity="false"：屏蔽rootLogger, 避免日志信息重复输出到控制台 -->
        <logger name="org.fanlychie" level="DEBUG" additivity="false">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="FILE"/>
        </logger>
    </springProfile>
    <!-- 生产环境配置 -->
    <springProfile name="prod">
        <!-- 自定义logger -->
        <logger name="org.fanlychie.mapper" level="DEBUG" additivity="false">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="FILE"/>
        </logger>
    </springProfile>
</configuration>
```