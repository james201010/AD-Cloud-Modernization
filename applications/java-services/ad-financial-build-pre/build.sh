#!/usr/bin/env bash

cp -a -r ../src ./src

docker build -t adfinmod_java_services_pre .

pwd

rm -r -f ./src
