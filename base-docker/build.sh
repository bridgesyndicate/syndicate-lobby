#!/bin/bash
set -ve
VERSION=14
echo version $VERSION
echo building with the contents of lobby-home
echo edit if you don\'t have this directory
tar -cJf lobby-home.${VERSION}.tar.xz lobby-home/
aws s3 cp lobby-home.${VERSION}.tar.xz s3://syndicate-minecraft-artifacts/
mv lobby-home.${VERSION}.tar.xz lobby-home.tar.xz
# aws s3 cp s3://syndicate-minecraft-artifacts/lobby-home.${VERSION}.tar.xz lobby-home.tar.xz
REPOSITORY_HOST=595508394202.dkr.ecr.us-west-2.amazonaws.com
REPOSITORY_URI=$REPOSITORY_HOST/syn-lobby-base
aws ecr get-login-password  | docker login --username AWS --password-stdin $REPOSITORY_HOST
docker build -t $REPOSITORY_URI:latest .
docker push $REPOSITORY_URI:latest
rm lobby-home.tar.xz
