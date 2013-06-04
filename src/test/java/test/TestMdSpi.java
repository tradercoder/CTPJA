package test;

import org.bridj.Pointer;
import org.bridj.ann.Ptr;
import org.bridj.ann.Virtual;
import thostmduserapi.*;

/**
 * Copyright (c) 2011-2013, z16304607@163.com 
 * Created with IntelliJ IDEA.
 * User: trade
 * Date: 13-4-12
 * Time: 下午12:49
 * To change this template use File | Settings | File Templates.
 */
public final class TestMdSpi extends CThostFtdcMdSpi
{
    private CThostFtdcMdApi m_api ;
    public TestMdSpi( CThostFtdcMdApi api )
    {
        this.m_api = api ;
    }

    /**
     * \u5f53\u5ba2\u6237\u7aef\u4e0e\u4ea4\u6613\u540e\u53f0\u5efa\u7acb\u8d77\u901a\u4fe1\u8fde\u63a5\u65f6\uff08\u8fd8\u672a\u767b\u5f55\u524d\uff09\uff0c\u8be5\u65b9\u6cd5\u88ab\u8c03\u7528\u3002<br>
     * Original signature : <code>void OnFrontConnected()</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:17</i>
     */
    @Virtual(0)
    public void OnFrontConnected()
    {
        System.out.println( "OnFrontConnected" ) ;
        //登陆
        CThostFtdcReqUserLoginField userLoginField = new CThostFtdcReqUserLoginField();
       // userLoginField.setBrokerID("1111");
//		userLoginField.setUserID("Kingnew_014");
//		userLoginField.setPassword("888888");

        this.m_api.ReqUserLogin(  Pointer.pointerTo( userLoginField ) , 1 ) ;
    }
    /**
     * 0x2003 \u6536\u5230\u9519\u8bef\u62a5\u6587<br>
     * Original signature : <code>void OnFrontDisconnected(int)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:26</i>
     */
    @Virtual(1)
    public void OnFrontDisconnected(int nReason)
    {
        System.out.println( "OnFrontDisconnected" + nReason ) ;
    }
    /**
     * @param nTimeLapse \u8ddd\u79bb\u4e0a\u6b21\u63a5\u6536\u62a5\u6587\u7684\u65f6\u95f4<br>
     * Original signature : <code>void OnHeartBeatWarning(int)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:30</i>
     */
    @Virtual(2)
    public void OnHeartBeatWarning(int nTimeLapse)
    {
        System.out.println( "OnHeartBeatWarning" ) ;
    }
    /**
     * \u767b\u5f55\u8bf7\u6c42\u54cd\u5e94<br>
     * Original signature : <code>void OnRspUserLogin(CThostFtdcRspUserLoginField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:34</i>
     */
    @Virtual(3)
    public void OnRspUserLogin(Pointer<CThostFtdcRspUserLoginField> pRspUserLogin, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "OnRspUserLogin" ) ;

        int nRet = this.m_api.SubscribeMarketData( Pointer.pointerToCStrings( "IF1305" ) , 1 ) ;
        nRet = this.m_api.SubscribeMarketData( Pointer.pointerToCStrings( "cu1309" ) , 1 ) ;
        nRet = this.m_api.SubscribeMarketData( Pointer.pointerToCStrings( "ru1309" ) , 1 ) ;

        System.out.println( "OnRspUserLogin " + nRet ) ;
    }

    /**
     * \u767b\u51fa\u8bf7\u6c42\u54cd\u5e94<br>
     * Original signature : <code>void OnRspUserLogout(CThostFtdcUserLogoutField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:37</i>
     */
    @Virtual(4)
    public void OnRspUserLogout(Pointer<CThostFtdcUserLogoutField> pUserLogout, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "OnRspUserLogout" ) ;
    }

    /**
     * \u9519\u8bef\u5e94\u7b54<br>
     * Original signature : <code>void OnRspError(CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:40</i>
     */
    @Virtual(5)
    public void OnRspError(Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "OnRspError" ) ;
    }

    /**
     * \u8ba2\u9605\u884c\u60c5\u5e94\u7b54<br>
     * Original signature : <code>void OnRspSubMarketData(CThostFtdcSpecificInstrumentField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:43</i>
     */
    @Virtual(6)
    public void OnRspSubMarketData(Pointer<CThostFtdcSpecificInstrumentField> pSpecificInstrument, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "OnRspSubMarketData" ) ;
    }

    /**
     * \u53d6\u6d88\u8ba2\u9605\u884c\u60c5\u5e94\u7b54<br>
     * Original signature : <code>void OnRspUnSubMarketData(CThostFtdcSpecificInstrumentField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:46</i>
     */
    @Virtual(7)
    public void OnRspUnSubMarketData(Pointer<CThostFtdcSpecificInstrumentField > pSpecificInstrument, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "OnRspUnSubMarketData" ) ;
    }

    /**
     * \u6df1\u5ea6\u884c\u60c5\u901a\u77e5<br>
     * Original signature : <code>void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField*)</code><br>
     * <i>native declaration : ThostFtdcMdApi.h:49</i>
     */
    @Virtual(8)
    public void OnRtnDepthMarketData(Pointer<CThostFtdcDepthMarketDataField > pDepthMarketData)
    {
        //System.out.println( "OnRtnDepthMarketData : in " ) ;
        CThostFtdcDepthMarketDataField data = pDepthMarketData.get( ) ;
        System.out.println( "OnRtnDepthMarketData : " + this.ToString_CThostFtdcDepthMarketDataField( data ) ) ;
        //System.out.println( "OnRtnDepthMarketData : out " ) ;
    }

    private String ToString_CThostFtdcDepthMarketDataField( CThostFtdcDepthMarketDataField data )
    {
        return String.format( "%s %s.%03d %f %d %f %d %f %d" ,
                data.InstrumentID().getCString( )  ,
                data.UpdateTime().getCString( ) ,
                data.UpdateMillisec( ) ,
                data.LastPrice( ) ,
                data.Volume( ) ,
                data.AskPrice1( ) ,
                data.AskVolume1( ) ,
                data.BidPrice1( ) ,
                data.BidVolume1( )
                 ) ;
    }
}
