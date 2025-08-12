# 使用官方JDK 1.8镜像作为基础镜像
FROM openjdk:8-jdk-alpine

# 维护者信息
MAINTAINER luoyuanxiang<1141306760@qq.com>

# 暴露端口
EXPOSE 8800

# 添加Spring Boot应用的JAR包到容器中
COPY mojian-server/target/*.jar app.jar

# 启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]
