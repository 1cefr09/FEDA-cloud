# FEDA - Forum Ease of Deployment & Administration

## Project Overview
**FEDA** is an easy-to-deploy and manage forum backend project. It is designed to provide a flexible and efficient solution that allows users to set up and maintain online forums with ease.

## 项目简介
**FEDA**是一个易于部署和管理的论坛后端项目。它旨在提供灵活、高效的解决方案，帮助用户轻松搭建和维护在线论坛。

## Configuration and Deployment

### Prerequisites

Deploying FEDA requires configuring the following environment:

- JDK 11
- MySQL 8
- Redis

部署FEDA，需要配置以下环境：

- JDK 11
- MySQL 8
- Redis

### Application Configuration

Create a file named `application-dev.yml` in the `FEDA-server/src/main/resources` directory with the following configuration:

在 `FEDA-server/src/main/resources` 目录中创建一个名为 `application-dev.yml` 的文件，内容如下：

```yaml
example:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    host: 
    port: 
    database: 
    username: 
    password: 
  redis:
    host: 
    port: 
  mail:
    host: 
    port: 
    username: 
    password: 
```

The datasource section is for MySQL database configuration.

The redis section is for Redis configuration.

The mail section is for email configuration, which is used later for the account activation feature. If you do not wish to enable this feature, you can skip this configuration.

datasource 部分用于 MySQL 数据库配置。

redis 部分用于 Redis 配置。

mail 部分用于邮箱配置，这在后续的账号激活功能中会用到。如果您不希望启用此功能，可以跳过该配置。

## Usage Guide

To start using **FEDA**, you can either directly run the `FedaApplication` class, or package the project into a JAR file and run it. After starting the application, you can call the various API endpoints defined in the `FEDA-server/src/main/java/com/example/controller/web` directory under different `Controller` classes.

All `Controller` interfaces have been documented using the Javadoc format. It is recommended to refer to the Javadoc documentation or inline comments to better understand and use the available endpoints.

您可以直接运行 `FedaApplication` 类，或将项目打包为 JAR 包并运行**FEDA**。启动应用后，您可以调用 `FEDA-server/src/main/java/com/example/controller/web` 目录下的各个 `Controller` 类中定义的API接口。

所有 `Controller` 接口已经按照 Javadoc 格式进行注释，建议参考 Javadoc 文档或注释以详细理解并使用这些接口。

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

本项目基于 MIT 许可证发布 - 详情请参阅 [LICENSE.md](LICENSE.md) 文件。
