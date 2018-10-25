#!/usr/bin/env bash

if [ -f "port-forward.sh" ]; then
    echo "port-forward.sh already exist, use [./port-forward.sh start] to start"
    exit
fi

wget https://raw.githubusercontent.com/raylax/port-forward/master/port-forward.sh
chmod +x port-forward.sh
./port-forward.sh start