#!/usr/bin/env bash

docker rm -f $(docker ps -a -q)

docker rmi -f $(docker images -q)

docker volume rm $(docker volume ls -q)

sudo rm -r -f -v /opt/appdynamics

cd /opt

sudo mkdir appdynamics

sudo chown -R ec2-user:ec2-user /opt/appdynamics

cd /opt/appdynamics

git clone https://github.com/james201010/AD-Cloud-Modernization.git

docker login

# build java-repositories docker
cd /opt/appdynamics/AD-Cloud-Modernization/applications/java-repositories/ad-financial
chmod 754 build.sh
./build.sh
docker image tag adfinmod_java_repositories_pre james201010/adfinmod_java_repositories_pre:latest
docker image push james201010/adfinmod_java_repositories_pre:latest

# build java-services docker - pre-modernization
cd /opt/appdynamics/AD-Cloud-Modernization/applications/java-services/ad-financial-build-pre
chmod 754 build.sh
./build.sh
docker image tag adfinmod_java_services_pre james201010/adfinmod_java_services_pre:latest
docker image push james201010/adfinmod_java_services_pre:latest

# build java-services docker - post-modernization
cd /opt/appdynamics/AD-Cloud-Modernization/applications/java-services/ad-financial-build-post
chmod 754 build.sh
./build.sh
docker image tag adfinmod_java_services_post james201010/adfinmod_java_services_post:latest
docker image push james201010/adfinmod_java_services_post:latest

# build java-webapps docker
cd /opt/appdynamics/AD-Cloud-Modernization/applications/java-webapps/ad-financial
chmod 754 build.sh
./build.sh
docker image tag adfinmod_java_webapps_pre james201010/adfinmod_java_webapps_pre:latest
docker image push james201010/adfinmod_java_webapps_pre:latest


# build load-generation docker
cd /opt/appdynamics/AD-Cloud-Modernization/applications/load-generation/ad-financial/docker
chmod 754 build.sh
./build.sh
docker image tag adfinmod_loadgen_pre james201010/adfinmod_loadgen_pre:latest
docker image push james201010/adfinmod_loadgen_pre:latest

cd /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial
chmod 754 remove-all-docker.sh
chmod 754 build-tag-all-docker.sh

cd /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-pre-mod/docker
chmod 754 showLogs.sh
chmod 754 start.sh
chmod 754 stop.sh

cd /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-post-mod
chmod 754 showLogs.sh
chmod 754 start.sh
chmod 754 stop.sh



