#!/usr/bin/env bash

set -e

PROJECT_FOLDER=$(readlink -f "$(dirname "$0")/..")
cd $PROJECT_FOLDER

bin/download-drivers.sh

echo "ENVIRONMENT: ${ENVIRONMENT}"
env

