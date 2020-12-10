#!/bin/bash

#
# Utility script to initialize the workshop prerequisites on the Cloud9 EC2 instance
#
# export appd_workshop_user=JEIDI
#
# NOTE: All inputs are defined by external environment variables.
#       Optional variables have reasonable defaults, but you may override as needed.
#---------------------------------------------------------------------------------------------------

#set -x  # turn command display back ON.

# [MANDATORY] workshop user identity.
appd_workshop_user="${appd_workshop_user:-}"

# validate mandatory environment variables.

if [ -z "$appd_workshop_user" ]; then
    echo "CloudWorkshop|ERROR| - 'appd_workshop_user' environment variable not set or is not at least five characters in length."
  exit 1
fi

LEN=$(echo ${#appd_workshop_user})

if [ $LEN -lt 5 ]; then
    echo "CloudWorkshop|ERROR| - 'appd_workshop_user' environment variable not set or is not at least five characters in length."
  exit 1
fi


docker rm -f $(docker ps -a -q)

docker rmi -f $(docker images -q)


sudo pkill -f db-agent.jar

##### Clone workshop repo

sudo rm -r -f -v /opt/appdynamics

cd /opt

sudo mkdir appdynamics

sudo chown -R ec2-user:ec2-user /opt/appdynamics

cd /opt/appdynamics

git clone https://github.com/james201010/AD-Cloud-Modernization.git


##### Prep Lab User Assets 

cd /opt/appdynamics

mkdir dbagent

mkdir workshopuser

cd /opt/appdynamics/workshopuser

mkdir pre-mod-docker

mkdir post-mod-kube-cluster

mkdir post-mod-kube-app

mkdir post-mod-kube-ma

mkdir post-mod-kube-ca

mkdir teardown


# COPY FILES FOR PRE-MOD APP
cp /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-pre-mod/docker/*.* /opt/appdynamics/workshopuser/pre-mod-docker/

chmod 777 /opt/appdynamics/workshopuser/pre-mod-docker/start.sh

chmod 777 /opt/appdynamics/workshopuser/pre-mod-docker/stop.sh

chmod 777 /opt/appdynamics/workshopuser/pre-mod-docker/showLogs.sh


# COPY FILES FOR POST-MOD APP
cp /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-post-mod/kube-config/application/*.* /opt/appdynamics/workshopuser/post-mod-kube-app/

cp /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-post-mod/kube-config/machineagent/*.* /opt/appdynamics/workshopuser/post-mod-kube-ma/

cp /opt/appdynamics/AD-Cloud-Modernization/applications/app-deployment/ad-financial/aws-post-mod/kube-config/clusteragent/*.* /opt/appdynamics/workshopuser/post-mod-kube-ca/




cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/deployEksCluster.sh /opt/appdynamics/workshopuser/
chmod 777 /opt/appdynamics/workshopuser/deployEksCluster.sh

cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/deployEksApplication.sh /opt/appdynamics/workshopuser/
chmod 777 /opt/appdynamics/workshopuser/deployEksApplication.sh

cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/deployMachineAgent.sh /opt/appdynamics/workshopuser/
chmod 777 /opt/appdynamics/workshopuser/deployMachineAgent.sh

# !!!!!!!!!!!!!!! Cluster Agent Script Needs Arguement passed in when running
cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/deployClusterAgent.sh /opt/appdynamics/workshopuser/
chmod 777 /opt/appdynamics/workshopuser/deployClusterAgent.sh

cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/exposeEksWebsite.sh /opt/appdynamics/workshopuser/
chmod 777 /opt/appdynamics/workshopuser/exposeEksWebsite.sh




# COPY THE PRE-REQ SCRIPT 
cp /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/workshopPrereqs.sh /opt/appdynamics/workshopuser/

chmod 777 /opt/appdynamics/workshopuser/workshopPrereqs.sh

##### Download the other shell scripts


curl --silent -L https://raw.githubusercontent.com/james201010/AD-Cloud-Modernization/master/applications/setup-teardown/workshops/aws/ad-financial-modern/teardownWorkshop.sh -o /home/ec2-user/environment/teardownWorkshop.sh

chmod 777 /home/ec2-user/environment/teardownWorkshop.sh


java -DworkshopUtilsConf=/opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/workshops/aws/ad-financial-modern/workshop-setup.yaml -DworkshopLabUserPrefix=${appd_workshop_user} -DworkshopAction=setup -DshowWorkshopBanner=true -jar /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/java-workshop-utils/AD-Workshop-Utils.jar
