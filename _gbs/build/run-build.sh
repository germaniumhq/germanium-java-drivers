#!/usr/bin/env bash

echo "mvn ${MAVEN_EXTRA_PARAMETERS} --settings /m2/settings.xml clean install"
mvn ${MAVEN_EXTRA_PARAMETERS} --settings /m2/settings.xml clean install
