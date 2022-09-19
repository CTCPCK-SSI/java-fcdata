@echo off
call mvn install:install-file -Dfile=libs/fcdata-client-2.0.0.jar -DgroupId=vn.com.ssi -DartifactId=fcdata-client -Dversion=2.0.0 -Dpackaging=jar
call mvn install:install-file -Dfile=libs/fctrading-client-2.0.0.jar -DgroupId=vn.com.ssi -DartifactId=fctrading-client -Dversion=2.0.0 -Dpackaging=jar
call mvn install:install-file -Dfile=libs/signalr-client-2.0.0.jar -DgroupId=vn.com.ssi -DartifactId=signalr-client -Dversion=2.0.0 -Dpackaging=jar