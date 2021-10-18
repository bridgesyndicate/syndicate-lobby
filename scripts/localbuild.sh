#!/usr/bin/env bash
set -e
TAG=latest
IMAGE=595508394202.dkr.ecr.us-west-2.amazonaws.com/syn-lobby
echo "doing the build"
rm -rf tmp
./scripts/localbuild.rb | grep -v 'aws ecr' | bash
rm -f plugins.tar *.jar

if [[ -n $1 ]]
then
    echo "running without bungee"
    TAG=no-bungee
    docker build -t $IMAGE:$TAG -f scripts/Dockerfile.nobungee .
fi
docker run \
       -e AWS_REGION=us-west-2 \
       -e AWS_ACCESS_KEY_ID=$DEV_AWS_ACCESS_KEY_ID \
       -e AWS_SECRET_KEY=$DEV_AWS_SECRET_KEY \
       -p 25565:25565 \
       --add-host=host.docker.internal:host-gateway \
       -it \
       ${IMAGE}:${TAG}
