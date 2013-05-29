#sh

#@ECHO ****************************** ----- 1 compile
mkdir target/classes

$JAVA_HOME/bin/javac -g -classpath ./lib/asm-4.1.jar:./lib/org.osgi.core-4.1.0.jar:./lib/bridj-0.6.2.jar ./src/test/java/test/*.java -sourcepath ./src/main/java -d ./target/classes

#@ECHO ****************************** ----- 3 run
mkdir target/debug
cd target/debug

$JAVA_HOME/bin/java -Xmx512M -Dbridj.verbose=true -classpath $JRE_HOME/lib/jsse.jar:$JRE_HOME/lib/jfr.jar:$JRE_HOME/lib/jfxrt.jar:$JRE_HOME/lib/javaws.jar:$JRE_HOME/lib/management-agent.jar:$JRE_HOME/lib/plugin.jar:$JRE_HOME/lib/deploy.jar:$JRE_HOME/lib/charsets.jar:$JRE_HOME/lib/resources.jar:$JRE_HOME/lib/rt.jar:$JRE_HOME/lib/jce.jar:$JRE_HOME/lib/ext/dnsns.jar:$JRE_HOME/lib/ext/sunjce_provider.jar:$JRE_HOME/lib/ext/sunec.jar:$JRE_HOME/lib/ext/zipfs.jar:$JRE_HOME/lib/ext/sunpkcs11.jar:$JRE_HOME/lib/ext/localedata.jar:../../lib/asm-4.1.jar:../../lib/org.osgi.core-4.1.0.jar:../../lib/bridj-0.6.2.jar:../classes test.TestMD

cd ..
cd ..

