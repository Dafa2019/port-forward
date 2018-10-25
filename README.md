# 端口转发程序

### 运行环境
* jdk8
* maven(仅构建时需要)

### 构建
```
mvn clean package
```
### 使用
```
wget https://s.inurl.org/releases/port-forward-1.0.jar
java -jar port-forward-1.0.jar
# web ui http://localhost:56666
```
后台运行
```
nohup java -jar -Dserver.port=80 port-forward-1.0.jar > /dev/null 2>&1 &
```
修改运行端口
```
java -jar -Dserver.port=8000 port-forward-1.0.jar
```
修改日志级别 [error,warn,info,debug,trace]
```
java -jar -Dlogging.level.root=error port-forward-1.0.jar
```

### 其他
[web ui source](https://github.com/raylax/port-forward-ui)