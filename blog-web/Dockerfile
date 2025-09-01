FROM nginx:stable-alpine

# 从构建阶段复制构建结果到 nginx 服务目录
COPY dist /usr/share/nginx/html

COPY ssl /etc/nginx/ssl

# 复制自定义 nginx 配置（如果有）
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露 80 端口
EXPOSE 80

# 启动 nginx 服务
CMD ["nginx", "-g", "daemon off;"]