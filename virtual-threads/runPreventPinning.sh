#!/usr/bin/env bash

#make sure the util module is compiled before running this script

java -cp ../util/target/classes/ --enable-preview --source=20 \
-Djdk.tracePinnedThreads=short src/main/java/org/jugistanbul/virtualthread/pin/PreventPinning.java
