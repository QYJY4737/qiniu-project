#!/bin/bash
kill -9 `ps -ef|grep 'qiniu-project.jar'|awk '{print $2}'`

java -Xms512m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:ParallelGCThreads=4 -jar qiniu-project.jar --spring.application.name=qiniu-project --server.port=10510 &
