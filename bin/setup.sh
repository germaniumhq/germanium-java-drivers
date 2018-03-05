#!/usr/bin/env bash

apt update -y
apt install -y firefox-esr

echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list
wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
apt-get update -y
apt-get install -y google-chrome-stable

