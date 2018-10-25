# 端口转发程序

### 运行环境
* jdk8

### 使用
```bash
wget https://github.com/raylax/port-forward/releases/download/1.0/port-forward-1.0.jar
java -jar port-forward-1.0.jar
# web ui http://localhost:56666
```
修改运行端口
```bash
java -jar port-forward-1.0.jar -Dserver.port=8000
```
修改日志级别 [error,warn,info,debug,trace]
```bash
java -jar port-forward-1.0.jar -Dlogging.level.root=error
```

### 其他
[web ui source](https://github.com/raylax/port-forward-ui)