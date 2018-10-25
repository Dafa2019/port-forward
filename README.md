# 端口转发程序

### 运行环境
* jdk8

### 构建
```bash
mvn clean package
```
### 使用
```bash
wget https://github.com/raylax/port-forward/releases/download/1.0/port-forward-1.0.jar
java -jar port-forward-1.0.jar
# web ui http://localhost:56666
```
修改运行端口
```bash
java -jar -Dserver.port=8000 port-forward-1.0.jar
```
修改日志级别 [error,warn,info,debug,trace]
```bash
java -jar -Dlogging.level.root=error port-forward-1.0.jar
```

### 其他
[web ui source](https://github.com/raylax/port-forward-ui)