#!/bin/bash
JAVA_FX_PATH=/usr/share/javafx-21/javafx-sdk-21/lib

java --module-path "$JAVA_FX_PATH" --add-modules javafx.controls,javafx.fxml \
    -jar target/code-snippet-stash-1.0-SNAPSHOT-shaded.jar
