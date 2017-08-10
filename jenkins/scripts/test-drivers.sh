#!/usr/bin/env bash

cp /scripts/settings.xml /home/ciplogic/.m2/settings.xml

cd /src
mvn clean install

