# 第一阶段：构建 Vue 应用
FROM node:18-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm install

# 复制项目文件
COPY . .

# 构建生产版本
RUN npm run build

# 第二阶段：部署应用
FROM nginx:stable-alpine as production-stage

# 从构建阶段复制构建结果到 nginx 服务目录
COPY --from=build-stage /app/dist /usr/share/nginx/html

COPY ssl /etc/nginx/ssl

# 复制自定义 nginx 配置（如果有）
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露 80 端口
EXPOSE 80

# 启动 nginx 服务
CMD ["nginx", "-g", "daemon off;"]