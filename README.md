CTPJA
=====

CTP Java Adapter . for quote datafeed and trade .


开发目的:
        提供Java调用接口，方便Java开发人员使用API。


设计思路:
        通过Bridj开源组件调用c++编写的so。
        Java接口完全和c++头文件定义一致，没有做任何自定义转换。



代码说明:
        1. 包 thostmduserapi 和 thosttraderapi 是c++头文件定义转换到java类定义。
        2. 依赖bridj.jar包。在lib目录下有asm,osgi,bridj jar包，项目需要引入。
        3. 在package test下有TestMD.java TestMDSpi.java TestTrade.java TestTrade                                                                                                    Spi.java是示例代码。



使用步骤:
        1. 继承CThostFtdcMdSpi和CThostFtdcTraderSpi类，具体可以参考test包下示例。或者参考官方提供的代码示例。


特别说明:
        此项目是DataFeed和交易执行模块中的一部分，希望对Java程序员有帮助。


免责说明:
        交易有风险，入市须谨慎。
        不负责由此产生的损失。

技术支持：
	z16304607@163.com


