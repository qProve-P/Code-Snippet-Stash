@echo off
echo Building Code Snippet Stash...
mvn clean package
echo Build complete! Run with: java -jar target/code-snippet-stash-1.0-SNAPSHOT-shaded.jar