#!/usr/bin/env bash

CURRENT_FOLDER=$(readlink -f "$(dirname "$0")")

set -e
chmod +x $CURRENT_FOLDER/*
$CURRENT_FOLDER/download-drivers.sh

