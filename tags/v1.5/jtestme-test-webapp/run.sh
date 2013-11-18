#!/bin/sh

SERVER=$1
: ${SERVER:="tomcat7"}

case $SERVER in
	tomcat7);;
	tomcat6);;
	jetty);;
	*)SERVER=tomcat7;;
esac

echo "mvn clean compile $SERVER:run"

cd ..
cd jtestme-core
mvn clean install -D skipTests
cd ..
cd jtestme-test-webapp
mvn clean compile $SERVER:run
