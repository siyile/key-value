#!/bin/bash
n=1
while [ $n -le 10 ]
do
    if [ "$(curl -s http://localhost:8080/health)" = '{"status":"UP"}' ]
    then
        exit 0
    else
        echo "check server is running?"
        sleep 3s
    fi
    n=$((n+1))
done
exit 1
