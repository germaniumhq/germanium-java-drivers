#!/usr/bin/env bash

GERMANIUM_FOLDER=$(readlink -f $(dirname $0)/../../..)

set -e

. $GERMANIUM_FOLDER/_gbs/build/prepare-build2/driver_versions

rm -fr $GERMANIUM_FOLDER/src/main/resources/binary
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary

# curl -O is set to download the file, instead of displaying it on the screen.
#      -L is set to follow redirects, like wget would do.
WGET="curl -O -L -k"

#
# Chrome drivers
#
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/chrome/linux/64
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/chrome/win/32
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/chrome/mac/64

rm -fr /tmp/germaniumdrivers
mkdir -p /tmp/germaniumdrivers
cd /tmp/germaniumdrivers
$WGET http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip
$WGET http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_win32.zip
$WGET http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_mac64.zip

cd $GERMANIUM_FOLDER/src/main/resources/binary/chrome/linux/64
unzip /tmp/germaniumdrivers/chromedriver_linux64.zip
cd $GERMANIUM_FOLDER/src/main/resources/binary/chrome/win/32
unzip /tmp/germaniumdrivers/chromedriver_win32.zip
cd $GERMANIUM_FOLDER/src/main/resources/binary/chrome/mac/64
unzip /tmp/germaniumdrivers/chromedriver_mac64.zip

#
# Firefox drivers
#
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/firefox/linux/64
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/firefox/win/64
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/firefox/mac/32

cd /tmp/germaniumdrivers
$WGET https://github.com/mozilla/geckodriver/releases/download/v$FIREFOXDRIVER_VERSION/geckodriver-v$FIREFOXDRIVER_VERSION-linux64.tar.gz
$WGET https://github.com/mozilla/geckodriver/releases/download/v$FIREFOXDRIVER_VERSION/geckodriver-v$FIREFOXDRIVER_VERSION-macos.tar.gz
$WGET https://github.com/mozilla/geckodriver/releases/download/v$FIREFOXDRIVER_VERSION/geckodriver-v$FIREFOXDRIVER_VERSION-win64.zip

cd $GERMANIUM_FOLDER/src/main/resources/binary/firefox/linux/64
tar -zxvf /tmp/germaniumdrivers/geckodriver-v$FIREFOXDRIVER_VERSION-linux64.tar.gz
cd $GERMANIUM_FOLDER/src/main/resources/binary/firefox/win/64
unzip /tmp/germaniumdrivers/geckodriver-v$FIREFOXDRIVER_VERSION-win64.zip
cd $GERMANIUM_FOLDER/src/main/resources/binary/firefox/mac/32
tar -zxvf /tmp/germaniumdrivers/geckodriver-v$FIREFOXDRIVER_VERSION-macos.tar.gz

#
# IE Driver
#
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/ie/win/32
mkdir -p $GERMANIUM_FOLDER/src/main/resources/binary/ie/win/64

cd /tmp/germaniumdrivers
$WGET http://selenium-release.storage.googleapis.com/$IEDRIVER_VERSION_MAJOR/IEDriverServer_Win32_$IEDRIVER_VERSION.zip
$WGET http://selenium-release.storage.googleapis.com/$IEDRIVER_VERSION_MAJOR/IEDriverServer_x64_$IEDRIVER_VERSION.zip

cd $GERMANIUM_FOLDER/src/main/resources/binary/ie/win/32
unzip /tmp/germaniumdrivers/IEDriverServer_Win32_$IEDRIVER_VERSION.zip
cd $GERMANIUM_FOLDER/src/main/resources/binary/ie/win/64
unzip /tmp/germaniumdrivers/IEDriverServer_x64_$IEDRIVER_VERSION.zip


