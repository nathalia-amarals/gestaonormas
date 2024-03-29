# we are extending everything from tomcat:8.0 image ...
FROM tomcat:9.0-alpine

LABEL MAINTAINER=nathalia_amaral

#EXPOSE 3003

RUN sed -i 's/port="8080"/port="3003"/' ${CATALINA_HOME}/conf/server.xml

# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
ADD /target/genormas.war /usr/local/tomcat/webapps/

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-war","/usr/local/tomcat/webapps/sigo.war"]