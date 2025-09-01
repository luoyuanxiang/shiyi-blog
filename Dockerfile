FROM openjdk:17-jdk-slim

# 安装Nginx
RUN apt-get update && apt-get install -y nginx && rm -rf /var/lib/apt/lists/*

# 复制Spring Boot jar包
COPY /blog/mojian-server/target/*.jar /app/app.jar

# 复制Vue构建产物到Nginx
COPY /blog-admin/dist /admin/
COPY /blog-web/dist /web/

# 复制Nginx配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY ssl/ /etc/nginx/ssl

# 暴露端口
EXPOSE 80 443

# 启动脚本
COPY start.sh /start.sh
RUN chmod +x /start.sh

CMD ["/start.sh"]