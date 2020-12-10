#!/usr/bin/env bash

java -DworkshopAction=test -DworkshopUtilsConf=./src/main/resources/workshop-setup.yaml -Djava.net.preferIPv4Stack=true -jar ./target/AD-Workshop-Utils.jar