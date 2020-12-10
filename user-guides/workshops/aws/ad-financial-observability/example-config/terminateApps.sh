#!/bin/bash

echo "CloudWorkshop|INFO| - Stopping Local Docker Application"
echo " "

/home/ec2-user/environment/deployment/pre-mod-docker/stop.sh

echo " "
echo "CloudWorkshop|INFO| - Finished Stopping Local Docker Application"

echo " "

echo "CloudWorkshop|INFO| - Starting EKS Cluster Deletion"
echo " "

eksctl delete cluster -f /home/ec2-user/environment/deployment/post-mod-kube-cluster/cluster.yaml

echo " "
echo "CloudWorkshop|INFO| - Finished EKS Cluster Deletion"
