@echo off

SET PROJECT_PATH=%CD%
cd %PROJECT_PATH%
cd ..
call mvn clean install -Dmaven.test.skip -f pom.xml
PAUSE