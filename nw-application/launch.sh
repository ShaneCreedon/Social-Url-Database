#!/bin/bash

shopt -s globstar
javac **/*.java
java -classpath ./src/main/java/ com.nw.socialscore.Launcher