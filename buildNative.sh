#!/bin/sh

#gw clean assemble
cp -r buildNativeCmd.sh build/buildNativeCmd.sh
cp -r config-dirs build/docker/main
docker run --rm -it -v $(pwd)/build:/project -w /project mm-native-builder-arm64v8:latest ./buildNativeCmd.sh
