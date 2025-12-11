#!/usr/bin/env sh

# Attempt to locate JAVA_HOME, if not set fallback to system java
if [ -z "$JAVA_HOME" ]; then
  JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
fi

APP_HOME=$(cd "$(dirname "$0")"; pwd)

JAVA_CMD="$JAVA_HOME/bin/java"

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

exec "$JAVA_CMD" -Xmx64m -Xms64m -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
