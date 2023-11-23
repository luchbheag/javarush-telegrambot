#!/bin/bash

# Ensure that docker-compose stoped
docker-compose stop

# Ensure that the old applications won't be deployed again.
mvn clean