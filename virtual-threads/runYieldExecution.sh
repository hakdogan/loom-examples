#!/usr/bin/env bash

java -cp ./target/classes/ --add-exports java.base/jdk.internal.vm=ALL-UNNAMED --enable-preview \
org.jugistanbul.virtualthread.continuation.YieldExecution




