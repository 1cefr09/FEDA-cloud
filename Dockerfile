# 使用官方的 OpenJDK 运行时作为基础镜像
FROM openjdk:11-jre-slim

# 设置容器中的工作目录
WORKDIR /app

# 将打包好的 JAR 文件复制到容器中的 /app 目录
COPY FEDA-server/target/FEDA-server-1.0-SNAPSHOT.jar /app/FEDA-server-1.0-SNAPSHOT.jar

# 暴露端口 8080
EXPOSE 8080

# 运行应用程序
CMD ["java", "-jar", "FEDA-server-1.0-SNAPSHOT.jar"]