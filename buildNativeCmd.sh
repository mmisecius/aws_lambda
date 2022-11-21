#!/bin/sh

MAIN_CLASS=mis055.runtime.GraalVMRuntime

mkdir -p /project/app
mkdir -p /project/app
cp -r docker/main/layers/libs /project/app/libs
cp -r docker/main/layers/classes /project/app/classes
cp -r docker/main/layers/resources /project/app/resources
cp -r docker/main/layers/application.jar /project/app/application.jar
cp -r docker/main/config-dirs /project/app/config-dirs

mkdir -p distributions/native
native-image -cp /project/app/libs/*.jar:/project/app/resources:/project/app/application.jar --no-fallback -H:Name=distributions/native/native_app.bin -H:ConfigurationFileDirectories=/project/app/config-dirs -H:Class=$MAIN_CLASS

# generate bootstrap
echo "#!/bin/sh" >> distributions/native/bootstrap
echo "set -euo pipefail" >> distributions/native/bootstrap
echo "./native_app.bin \$_HANDLER -XX:MaximumHeapSizePercent=80 -Dio.netty.allocator.numDirectArenas=0 -Dio.netty.noPreferDirect=true -Djava.library.path=$(pwd)" >> distributions/native/bootstrap

# grant exec permissions
chmod 755 distributions/native/bootstrap
chmod 755 distributions/native/native_app.bin

# generate function.zip file
zip -j distributions/native/function.zip distributions/native/bootstrap distributions/native/native_app.bin