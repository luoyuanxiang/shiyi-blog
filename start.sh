#!/bin/bash
set -e

# 启动 Spring Boot 应用
# 使用 exec 以便信号（如 SIGTERM）能正确传递给 Java 进程
echo "Starting Spring Boot application..."
exec java -jar /app/app.jar > /proc/1/fd/1 2>/proc/1/fd/2 &
# 等待几秒让 Spring Boot 启动
sleep 10

# 启动 Nginx in foreground
echo "Starting Nginx..."
exec nginx -g 'daemon off;'