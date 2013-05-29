javac -classpath .:../lib/log4j.jar:../lib/commons-lang-2.2.jar:../src-swfparser/:../lib/spring-core.jar:../lib/spring-context.jar:../lib/spring-beans.jar:../src-jswiff/:../lib/commons-logging-1.0.4.jar org/swfparser/tests/ASBatchDumper.java

# compile all files on this dir explicitly, some are referred to only by the XML file
javac -classpath .:../lib/log4j.jar:../lib/commons-lang-2.2.jar:../src-swfparser/:../lib/spring-core.jar:../lib/spring-context.jar:../lib/spring-beans.jar:../src-jswiff/:../lib/commons-logging-1.0.4.jar org/swfparser/*.java
