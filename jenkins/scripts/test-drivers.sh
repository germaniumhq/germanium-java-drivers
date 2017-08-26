#!/usr/bin/env bash

# we checkout the sources of the project
git clone --recursive $SOURCES_URL /tmp/project

source "/home/ciplogic/.sdkman/bin/sdkman-init.sh"
cp /scripts/settings.xml /home/ciplogic/.m2/settings.xml

cd /tmp/project
mvn clean install

