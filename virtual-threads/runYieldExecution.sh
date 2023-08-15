#!/usr/bin/env bash

#make sure the YieldExecution class is compiled before running this script

java -cp ./target/classes/ --add-exports java.base/jdk.internal.vm=ALL-UNNAMED --enable-preview \
org.jugistanbul.virtualthread.continuation.YieldExecution
