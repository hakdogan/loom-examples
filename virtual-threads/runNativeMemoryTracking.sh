#!/usr/bin/env bash

#make sure the util module is compiled before running this script
#you can tune stack size with -XX:ThreadStackSize=$size

java -cp ../util/target/classes/ -XX:NativeMemoryTracking=summary --enable-preview --source=20 \
src/main/java/org/jugistanbul/virtualthread/monitor/NativeMemoryTracking.java \
10000 V
