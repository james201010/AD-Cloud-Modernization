#!/bin/bash

java -DworkshopUtilsConf=/opt/appdynamics/workshopuser/teardown/workshop-teardown.yaml -DworkshopAction=teardown -DshowWorkshopBanner=true -jar /opt/appdynamics/AD-Cloud-Modernization/applications/setup-teardown/java-workshop-utils/AD-Workshop-Utils.jar

#sudo rm -r -f /opt/appdynamics
