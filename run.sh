#!/bin/bash

# Ensure the bin directory exists
mkdir -p bin

# Compile all .java files in src directory and place .class files in bin directory
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
else
    echo "Compilation successful."

    # Run the specific Java class (adjust ClassName as needed)
    java -cp bin Main
fi
