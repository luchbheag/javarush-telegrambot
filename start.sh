#!/bin/bash

# Pull new changes
# git pull

# Prepare JAR
mvn clean package -DskipTests

# Ensure that docker-compose stopped
docker-compose stop

# Add enviroment variables
#export BOT_NAME=$1
#export BOT_TOKEN=$2
export BOT_NAME='test_javarush_tutorial_bot'
export BOT_TOKEN='6956421218:AAEX0XxaO5tmHGqaLNmg_c_FAOAWXMXvw7s'
export BOT_DB_USERNAME='dev_jrtb_db_user'
export BOT_DB_PASSWORD='dev_jrtb_db_password'

# Start new deployment
docker-compose up --build -d