# sh

$JAVA_HOME/bin/java -Xmx1g -jar ./jnaerator-0.12-SNAPSHOT-20130323-2.jar -library thostmduserapi ./ctpapi/linux/libthostmduserapi.so ./ctpapi/linux/ThostFtdcMdApi.h -o ./ -mode Jar -runtime BridJ 

$JAVA_HOME/bin/java -Xmx1g -jar ./jnaerator-0.12-SNAPSHOT-20130323-2.jar -library thosttraderapi ./ctpapi/linux/libthosttraderapi.so ./ctpapi/linux/ThostFtdcTraderApi.h -o ./ -mode Jar -runtime BridJ 



