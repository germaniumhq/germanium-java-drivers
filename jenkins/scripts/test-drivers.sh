#!/usr/bin/env bash

# fail fast
set -e

#############################################################################
# Utility functions.
#############################################################################
function deactivate_proxy() {
    old_http_proxy="$http_proxy"
    old_https_proxy="$https_proxy"
    old_ftp_proxy="$ftp_proxy"
    unset http_proxy
    unset https_proxy
    unset ftp_proxy
}

function activate_proxy() {
    export http_proxy="$old_http_proxy"
    export https_proxy="$old_https_proxy"
    export ftp_proxy="$old_ftp_proxy"
}

deactivate_proxy

# we checkout the sources of the project
# we checkout the sources of the project
git clone --recursive $SOURCES_URL /tmp/project

source "/home/ciplogic/.sdkman/bin/sdkman-init.sh"
cp /scripts/settings.xml /home/ciplogic/.m2/settings.xml

cd /tmp/project
mvn clean install

