#!/usr/bin/env bash

DOWNLOAD_HOST="https://s.inurl.org/releases/"
JAR_FILE="port-forward-1.0.jar"
VM_ARGS="-Dserver.port=56666 -Dauth.code=123456"

pid=`ps -ef | grep ${JAR_FILE} | grep -v grep | awk '{print $2}'`
work_path=$(cd `dirname $0`; pwd)

start() {
    if [ ! -f "$work_path/$JAR_FILE" ]; then
        echo "jar file does not exist, downloading ..."
        wget "$DOWNLOAD_HOST$JAR_FILE"
    fi

    if [ -z "$pid" ]; then
        echo "starting service ..."
        nohup java -jar ${VM_ARGS} ${JAR_FILE} > ${JAR_FILE}.log 2>&1 &
        echo "service started"
    else
        echo "service is running PID: $pid"
    fi
}

stop() {
    if [ -n "$pid" ]; then
        echo "stopping service ..."
        kill -9 ${pid}
        echo "service stopped"
    else
        echo "service not running"
    fi
}

restart() {
    stop
    pid=`ps -ef | grep ${JAR_FILE} | grep -v grep | awk '{print $2}'`
    start
}

status() {
    if [ -n "$pid" ]; then
        echo "service is running PID: $pid"
    else
        echo "service not running"
    fi
}

case $1 in
    start)
        start;;
    stop)
        stop;;
    restart)
        restart;;
    status)
        status;;
    *)
        echo "use $0 start|stop|restart|status";;
esac