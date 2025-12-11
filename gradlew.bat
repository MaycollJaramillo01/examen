@ECHO OFF
SET JAVA_EXE="%JAVA_HOME%\bin\java.exe"
IF NOT EXIST %JAVA_EXE% SET JAVA_EXE=java
SET APP_HOME=%~dp0
SET CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
"%JAVA_EXE%" -Xmx64m -Xms64m -classpath %CLASSPATH% org.gradle.wrapper.GradleWrapperMain %*
