@echo off

rem Ensure the bin directory exists
if not exist bin mkdir bin

rem Compile all .java files in src directory and place .class files in bin directory
javac -d bin src/*.java

rem Check if compilation was successful
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
) else (
    echo Compilation successful.
    
    rem Run the specific Java class (adjust ClassName as needed)
    java -cp bin Main
)
