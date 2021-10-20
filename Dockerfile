FROM 595508394202.dkr.ecr.us-west-2.amazonaws.com/syn-lobby-base:latest
WORKDIR /app/lobby-home/plugins
ADD ./plugins.tar .
WORKDIR /app/lobby-home
