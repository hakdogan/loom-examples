#!/usr/bin/env bash

#make sure the util module is compiled before running this script

java -XX:StartFlightRecording:filename=virtualThreadPinnedEvent.jfr \
-cp ../util/target/classes/ --enable-preview --source=20 \
src/main/java/org/jugistanbul/virtualthread/pin/ThreadPinned.java


