#!/bin/sh

MAIN_CLASS=mis055.runtime.GraalVMRuntime

mkdir -p /project/app
#cp -r docker/main/layers/libs /project/app/libs
#cp -r docker/main/layers/classes /project/app/classes
#cp -r docker/main/layers/resources /project/app/resources
#cp -r docker/main/layers/application.jar /project/app/application.jar
#cp -r docker/main/config-dirs/ /project/app

distributionPath=distributions/native
mkdir -p distributions/native
nativeFilePath=$distributionPath/native_app.bin
bootstrapFilePath=$distributionPath/bootstrap
functionFilePath=$distributionPath/function.zip

if [ -f "docker/main/config-dirs" ]; then
  rm -rf docker/main/config-dirs
fi

mkdir -p docker/main/config-dirs
#java -agentlib:native-image-agent=experimental-class-loader-support,config-output-dir=docker/main/config-dirs -jar docker/main/layers/application.jar

#   --initialize-at-build-time=org.apache.logging.log4j,org.apache.logging.slf4j \
native-image -cp docker/main/layers/libs/*.jar:docker/main/layers/resources:docker/main/layers/application.jar \
  --no-fallback -H:Name=$nativeFilePath \
  -H:ConfigurationFileDirectories=docker/main/config-dirs \
  -H:Class=$MAIN_CLASS

if [ -f "$nativeFilePath" ]; then

  # generate bootstrap
  echo "#!/bin/sh" >> $bootstrapFilePath
  echo "set -euo pipefail" >> $bootstrapFilePath
  echo "./native_app.bin \$_HANDLER" >> $bootstrapFilePath
#  echo "./native_app.bin \$_HANDLER -XX:MaximumHeapSizePercent=80 -Dio.netty.allocator.numDirectArenas=0 -Dio.netty.noPreferDirect=true -Djava.library.path=$(pwd)" >> $bootstrapFilePath

  # grant exec permissions
  chmod 755 $bootstrapFilePath
  chmod 755 $nativeFilePath

  # generate function.zip file
  zip -j $functionFilePath $bootstrapFilePath $nativeFilePath
else
    echo "$nativeFilePath was not generated"
fi

