# Docker image for springboot file run
# VERSION 0.0.1
# Author: cxxwl96
# 基础镜像使用java

FROM java:8

# 作者
MAINTAINER cxxwl96 <cxxwl96@sina.com>

# 设置工作目录，进入到容器中的初始目录,不存在会自动创建
ENV ENVPATH /usr/web/alan
WORKDIR $ENVPATH

# 添加容器卷，方便以后的处理，关联宿主机中的目录，不存在会自动创建
VOLUME $ENVPATH

# 将jar包添加到容器中并更名为app.jar
ADD ./lib/alan-2.0.3.jar app.jar

COPY ./config/application.yml $ENVPATH/config
#COPY ../lib/app.jar $ENVPATH/lib


# 声明需要暴露的端口
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom",\
"-jar",\
"-Dspring.config.location=$ENVPATH/config/application.yml",\
"-Dloader.path=$ENVPATH/lib",\
"$ENVPATH/lib/app.jar"]