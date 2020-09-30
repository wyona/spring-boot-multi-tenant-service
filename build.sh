#!/bin/bash

TOMCAT_HOME=/Users/michaelwechner/local/apache-tomcat-8.5.34

echo "INFO: Clean ..."
mvn clean

#echo "INFO: Create database"
#mvn flyway:migrate

echo "INFO: Build webapp ..."
mvn install -Dmaven.test.skip=true
#mvn install
#mvn -X install

if [ -d $TOMCAT_HOME ];then
echo "INFO: Deploy webapp ..."
rm -rf $TOMCAT_HOME/webapps/multi-tenant-service-webapp-*
rm -rf $TOMCAT_HOME/work/Catalina/localhost/multi-tenant-service-webapp-*
cp target/multi-tenant-service-webapp-0.0.1-SNAPSHOT.war $TOMCAT_HOME/webapps/.

echo "INFO: Clean log files ..."
rm -f $TOMCAT_HOME/logs/*

    echo "INFO: Startup Tomcat '$TOMCAT_HOME' and access AskKatie 'http://127.0.0.1:8080/multi-tenant-service-webapp-0.0.1-SNAPSHOT/' ...."
else
    echo "INFO: No Tomcat installed at $TOMCAT_HOME"
fi
