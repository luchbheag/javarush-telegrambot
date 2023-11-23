#!/bin/bash

# Pull new changes
git pull

# Prepare JAR
mvn clean
mvn package

# ENsure, that docker-compose stopped
docker-compose stop

# Add enviroment variables
export BOT_NAME=$1
export BOT_TOKEN=$2

# Start new deployment
docker-compose up --build -d