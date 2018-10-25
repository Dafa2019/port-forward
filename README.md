# 端口转发程序

### 运行环境
* jdk8
* maven(仅构建时需要)
### 一键使用
```
curl https://raw.githubusercontent.com/raylax/port-forward/master/install.sh | sh
```
> 默认AuthCode为123456，端口为56666 可通过`port-forward.sh`脚本中`VM_ARGS`参数修改
```
# 启动
./port-forward.sh start
# 停止
./port-forward.sh stop
# 重启
./port-forward.sh restart
# 状态
./port-forward.sh status
```
### 构建
```
mvn clean package
```
### 自定义命令使用
```
wget https://s.inurl.org/releases/port-forward-1.0.jar
java -jar port-forward-1.0.jar
# web ui http://localhost:56666
```
> 默认AuthCode为123456
后台运行
```
nohup java -jar -Dserver.port=80 port-forward-1.0.jar > /dev/null 2>&1 &
```
修改AuthCode
```
java -jar -Dauth.code=666666 port-forward-1.0.jar
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