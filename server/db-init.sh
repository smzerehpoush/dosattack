#!/usr/bin/env bash
docker pull redis
docker run -d --name da-redis -p 27026:6379 redis
docker pull mongo
docker run -d --name da-mongo -p 27027:27017 mongo
docker pull mysql
docker run -d --name da-mysql -p 27028:3306 --env="MYSQL_ROOT_PASSWORD=1353" mysql