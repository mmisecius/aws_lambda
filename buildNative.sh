#!/bin/sh

gw clean assemble
cp -r buildNativeCmd.sh build/buildNativeCmd.sh
cp -r config-dirs build/docker/main/config-dirs
docker run --rm -it -v $(pwd)/build:/project -w /project mm-native-builder-arm64v8:latest ./buildNativeCmd.sh myTestFileName


#docker run --rm -it -v $(pwd)/build:/project -w /project mm-native-builder-amd64:latest ./buildNativeCmd.sh
#docker run --rm -it -v $(pwd)/build:/project -w /project public.ecr.aws/j1v3l8o0/graalvm-arm64-11-pub:latest ./buildNativeCmd.sh
