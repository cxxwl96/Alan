# Docker image for springboot file run
# VERSION 0.0.1
# Author: cxxwl96
# 基础镜像使用java
FROM java:8

# 作者
MAINTAINER cxxwl96 <cxxwl96@sina.com>

# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp

# 将jar包添加到容器中并更名为app.jar
ADD alan-2.0.3.jar app.jar

# 声明需要暴露的端口
EXPOSE 8080

# 运行jar包
# RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]