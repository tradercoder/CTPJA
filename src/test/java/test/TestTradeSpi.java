package test;

import org.bridj.Pointer;
import org.bridj.ann.Ptr;
import org.bridj.ann.Virtual;
import java.nio.charset.Charset;

import thosttraderapi.*;


/**
 * Copyright (c) 2011-2013, z16304607@163.com 
 * Created with IntelliJ IDEA.
 * User: trade
 * Date: 13-5-16
 * Time: 下午10:25
 * To change this template use File | Settings | File Templates.
 */
public class TestTradeSpi extends CThostFtdcTraderSpi
{
    private String m_brokerID = "1002" ;
    private String m_userID = "00000063" ;
    private String m_password = "123456" ;
    private String m_testInstrumentID = "IF1306" ;
    private double m_testLimitPrice = 2569.40 ;
    private int m_frontID ;
    private int m_sessionID ;

    private CThostFtdcTraderApi m_api ;

    public TestTradeSpi( CThostFtdcTraderApi api )
    {
        this.m_api = api ;
    }

    /**
     * \ufffd\ufffd\ufffd\u037b\ufffd\ufffd\ufffd\ufffd\ubf7b\ufffd\u05fa\ufffd\u0328\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\ufffd\ufffd\ufffd\u03b4\ufffd\ufffd\u00bc\u01f0\ufffd\ufffd\ufffd\ufffd\ufffd\u00f7\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u00e1\ufffd<br>
     * Original signature : <code>void OnFrontConnected()</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:22</i>
     */
    @Virtual(0)
    public void OnFrontConnected( )
    {
        System.out.println( "TestTradeSpi OnFrontConnected" );

        CThostFtdcReqUserLoginField login = new CThostFtdcReqUserLoginField( ) ;
        login.setBrokerID( this.m_brokerID ) ;
        login.setUserID( this.m_userID );
        login.setPassword( this.m_password );

        this.m_api.ReqUserLogin( Pointer.pointerTo( login ) , 1 ) ;
    }

    /**
     * 0x2003 \ufffd\u0575\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd<br>
     * Original signature : <code>void OnFrontDisconnected(int)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:31</i>
     */
    @Virtual(1)
    public void OnFrontDisconnected(int nReason)
    {
        System.out.println( "TestTradeSpi OnFrontDisconnected" );
    }

    /**
     * @param nTimeLapse \ufffd\ufffd\ufffd\ufffd\ufffd\u03f4\u03bd\ufffd\ufffd\u0571\ufffd\ufffd\u0135\ufffd\u02b1\ufffd\ufffd<br>
     * Original signature : <code>void OnHeartBeatWarning(int)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:35</i>
     */
    @Virtual(2)
    public void OnHeartBeatWarning(int nTimeLapse)
    {
        System.out.println( "TestTradeSpi OnHeartBeatWarning" );
    }

    /**
     * \ufffd\u037b\ufffd\ufffd\ufffd\ufffd\ufffd\u05a4\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspAuthenticate(CThostFtdcRspAuthenticateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:38</i>
     */
    @Virtual(3)
    public void OnRspAuthenticate(Pointer<CThostFtdcRspAuthenticateField > pRspAuthenticateField, Pointer<CThostFtdcRspInfoField> pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "TestTradeSpi OnRspAuthenticate" );
    }

    /**
     * \ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspUserLogin(CThostFtdcRspUserLoginField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:42</i>
     */
    @Virtual(4)
    public void OnRspUserLogin(Pointer<CThostFtdcRspUserLoginField> pRspUserLogin, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println( "TestTradeSpi OnRspUserLogin" ) ;

        CThostFtdcRspInfoField rspInfo = pRspInfo.get( ) ;
        System.out.println("RspInfo.ErrorID = " + rspInfo.ErrorID() + " ErrorMsg = " + rspInfo.ErrorMsg().getCString()) ;

        CThostFtdcRspUserLoginField rspLogin = pRspUserLogin.get( ) ;

        String msg = String.format( "BrokerID = %s UserID = %s TradingDay = %s FrontID = %d SessionID = %d MaxOrderRef = %s"  ,
                                    rspLogin.BrokerID( ).getCString( ) ,
                                    rspLogin.UserID().getCString() ,
                                    rspLogin.TradingDay().getCString() ,
                                    rspLogin.FrontID() ,
                                    rspLogin.SessionID() ,
                                    rspLogin.MaxOrderRef().getCString( )
                                    ) ;

        System.out.println( msg ) ;

        this.m_frontID = rspLogin.FrontID( ) ;
        this.m_sessionID = rspLogin.SessionID( ) ;

        /**
         * 投资者结算结果确认
         */
        CThostFtdcSettlementInfoConfirmField req = new CThostFtdcSettlementInfoConfirmField( ) ;
        req.setBrokerID( this.m_brokerID ) ;
        req.setInvestorID( this.m_userID ) ;

        this.m_api.ReqSettlementInfoConfirm( Pointer.pointerTo( req ) , 1 ) ;
    }

    /**
     * \ufffd\u01f3\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspUserLogout(CThostFtdcUserLogoutField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:45</i>
     */
    @Virtual(5)
    public void OnRspUserLogout(Pointer<CThostFtdcUserLogoutField > pUserLogout, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println( "TestTradeSpi OnRspUserLogout" );
    }

    /**
     * \ufffd\u00fb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspUserPasswordUpdate(CThostFtdcUserPasswordUpdateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:48</i>
     */
    @Virtual(6)
    public void OnRspUserPasswordUpdate(Pointer<CThostFtdcUserPasswordUpdateField > pUserPasswordUpdate, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspTradingAccountPasswordUpdate(CThostFtdcTradingAccountPasswordUpdateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:51</i>
     */
    @Virtual(7)
    public void OnRspTradingAccountPasswordUpdate(Pointer<CThostFtdcTradingAccountPasswordUpdateField > pTradingAccountPasswordUpdate, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspOrderInsert(CThostFtdcInputOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:54</i>
     */
    @Virtual(8)
    public void OnRspOrderInsert(Pointer<CThostFtdcInputOrderField > pInputOrder, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "TestTradeSpi OnRspOrderInsert" );

        CThostFtdcRspInfoField rspInfo = pRspInfo.get( ) ;
        System.out.println("RspInfo.ErrorID = " + rspInfo.ErrorID() + " ErrorMsg = " + rspInfo.ErrorMsg().getCString()) ;
    }

    /**
     * \u0524\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspParkedOrderInsert(CThostFtdcParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:57</i>
     */
    @Virtual(9)
    public void OnRspParkedOrderInsert(Pointer<CThostFtdcParkedOrderField > pParkedOrder, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \u0524\ufffd\ud98f\uddf5\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspParkedOrderAction(CThostFtdcParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:60</i>
     */
    @Virtual(10)
    public void OnRspParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField > pParkedOrderAction, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspOrderAction(CThostFtdcInputOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:63</i>
     */
    @Virtual(11)
    public void OnRspOrderAction(Pointer<CThostFtdcInputOrderActionField > pInputOrderAction, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\u046f\ufffd\ufffd\udb86\ude35\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQueryMaxOrderVolume(CThostFtdcQueryMaxOrderVolumeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:66</i>
     */
    @Virtual(12)
    public void OnRspQueryMaxOrderVolume(Pointer<CThostFtdcQueryMaxOrderVolumeField > pQueryMaxOrderVolume, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \u0376\ufffd\ufffd\ufffd\u07fd\ufffd\ufffd\ufffd\ufffd\ufffd\u0237\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:69</i>
     */
    @Virtual(13)
    public void OnRspSettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField > pSettlementInfoConfirm, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "TestTradeSpi OnRspSettlementInfoConfirm" );

        /**
         * 发送一笔测试单
         */
        CThostFtdcInputOrderField inputOrder = new CThostFtdcInputOrderField( ) ;
        inputOrder.setBrokerID( this.m_brokerID  ) ;
        inputOrder.setInvestorID( this.m_userID ) ;
        inputOrder.setInstrumentID( this.m_testInstrumentID ) ;
        inputOrder.setOrderRef( "2" );
        inputOrder.setOrderPriceType( ( byte )ThosttraderapiLibrary.THOST_FTDC_OPT_LimitPrice ) ;
        inputOrder.setDirection( ( byte )ThosttraderapiLibrary.THOST_FTDC_D_Buy );
        inputOrder.setCombOffsetFlag( String.valueOf( ThosttraderapiLibrary.THOST_FTDC_OF_Open ) ) ;
        inputOrder.setCombHedgeFlag( String.valueOf( ThosttraderapiLibrary.THOST_FTDC_HF_Speculation ) ) ;
        inputOrder.setLimitPrice( this.m_testLimitPrice ) ;
        inputOrder.setVolumeTotalOriginal( 18 ) ;
        inputOrder.setTimeCondition( ( byte )ThosttraderapiLibrary.THOST_FTDC_TC_GFD ) ;
        inputOrder.setVolumeCondition( ( byte )ThosttraderapiLibrary.THOST_FTDC_VC_AV ) ;
        inputOrder.setMinVolume( 1 ) ;
        inputOrder.setContingentCondition( ( byte )ThosttraderapiLibrary.THOST_FTDC_CC_Immediately ) ;
        inputOrder.setForceCloseReason( (  byte )ThosttraderapiLibrary.THOST_FTDC_FCC_NotForceClose ) ;
        inputOrder.setIsAutoSuspend( 1 ) ;
        inputOrder.setUserForceClose( 0 ) ;

        int nRet = this.m_api.ReqOrderInsert( Pointer.pointerTo( inputOrder ) , 1 ) ;
        System.out.println( nRet == 0 ? "OrderInsert 成功" : " OrderInsert失败" ) ;
    }

    /**
     * \u027e\ufffd\ufffd\u0524\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspRemoveParkedOrder(CThostFtdcRemoveParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:72</i>
     */
    @Virtual(14)
    public void OnRspRemoveParkedOrder(Pointer<CThostFtdcRemoveParkedOrderField > pRemoveParkedOrder, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \u027e\ufffd\ufffd\u0524\ufffd\ud98f\uddf5\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspRemoveParkedOrderAction(CThostFtdcRemoveParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:75</i>
     */
    @Virtual(15)
    public void OnRspRemoveParkedOrderAction(Pointer<CThostFtdcRemoveParkedOrderActionField > pRemoveParkedOrderAction, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryOrder(CThostFtdcOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:78</i>
     */
    @Virtual(16)
    public void OnRspQryOrder(Pointer<CThostFtdcOrderField > pOrder, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u027d\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTrade(CThostFtdcTradeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:81</i>
     */
    @Virtual(17)
    public void OnRspQryTrade(Pointer<CThostFtdcTradeField > pTrade, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInvestorPosition(CThostFtdcInvestorPositionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:84</i>
     */
    @Virtual(18)
    public void OnRspQryInvestorPosition(Pointer<CThostFtdcInvestorPositionField > pInvestorPosition, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTradingAccount(CThostFtdcTradingAccountField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:87</i>
     */
    @Virtual(19)
    public void OnRspQryTradingAccount(Pointer<CThostFtdcTradingAccountField > pTradingAccount, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInvestor(CThostFtdcInvestorField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:90</i>
     */
    @Virtual(20)
    public void OnRspQryInvestor(Pointer<CThostFtdcInvestorField > pInvestor, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u05f1\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTradingCode(CThostFtdcTradingCodeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:93</i>
     */
    @Virtual(21)
    public void OnRspQryTradingCode(Pointer<CThostFtdcTradingCodeField > pTradingCode, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\u05a4\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInstrumentMarginRate(CThostFtdcInstrumentMarginRateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:96</i>
     */
    @Virtual(22)
    public void OnRspQryInstrumentMarginRate(Pointer<CThostFtdcInstrumentMarginRateField > pInstrumentMarginRate, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInstrumentCommissionRate(CThostFtdcInstrumentCommissionRateField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:99</i>
     */
    @Virtual(23)
    public void OnRspQryInstrumentCommissionRate(Pointer<CThostFtdcInstrumentCommissionRateField > pInstrumentCommissionRate, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryExchange(CThostFtdcExchangeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:102</i>
     */
    @Virtual(24)
    public void OnRspQryExchange(Pointer<CThostFtdcExchangeField > pExchange, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\u053c\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInstrument(CThostFtdcInstrumentField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:105</i>
     */
    @Virtual(25)
    public void OnRspQryInstrument(Pointer<CThostFtdcInstrumentField > pInstrument, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryDepthMarketData(CThostFtdcDepthMarketDataField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:108</i>
     */
    @Virtual(26)
    public void OnRspQryDepthMarketData(Pointer<CThostFtdcDepthMarketDataField > pDepthMarketData, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07fd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:111</i>
     */
    @Virtual(27)
    public void OnRspQrySettlementInfo(Pointer<CThostFtdcSettlementInfoField > pSettlementInfo, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTransferBank(CThostFtdcTransferBankField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:114</i>
     */
    @Virtual(28)
    public void OnRspQryTransferBank(Pointer<CThostFtdcTransferBankField > pTransferBank, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u03f8\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:117</i>
     */
    @Virtual(29)
    public void OnRspQryInvestorPositionDetail(Pointer<CThostFtdcInvestorPositionDetailField > pInvestorPositionDetail, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u037b\ufffd\u0368\u05aa\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryNotice(CThostFtdcNoticeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:120</i>
     */
    @Virtual(30)
    public void OnRspQryNotice(Pointer<CThostFtdcNoticeField > pNotice, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u03e2\u0237\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQrySettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:123</i>
     */
    @Virtual(31)
    public void OnRspQrySettlementInfoConfirm(Pointer<CThostFtdcSettlementInfoConfirmField > pSettlementInfoConfirm, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0376\ufffd\ufffd\ufffd\u07f3\u05b2\ufffd\ufffd\ufffd\u03f8\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryInvestorPositionCombineDetail(CThostFtdcInvestorPositionCombineDetailField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:126</i>
     */
    @Virtual(32)
    public void OnRspQryInvestorPositionCombineDetail(Pointer<CThostFtdcInvestorPositionCombineDetailField > pInvestorPositionCombineDetail, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\u046f\ufffd\ufffd\u05a4\ufffd\ufffd\ufffd\ufffd\u03f5\u0373\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\u02bd\ufffd\ufffd\u02fb\ufffd\ufffd\ufffd\u053f\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryCFMMCTradingAccountKey(CThostFtdcCFMMCTradingAccountKeyField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:129</i>
     */
    @Virtual(33)
    public void OnRspQryCFMMCTradingAccountKey(Pointer<CThostFtdcCFMMCTradingAccountKeyField > pCFMMCTradingAccountKey, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\u05b5\ufffd\ufffd\u06f5\ufffd\ufffd\ufffd\u03e2\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryEWarrantOffset(CThostFtdcEWarrantOffsetField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:132</i>
     */
    @Virtual(34)
    public void OnRspQryEWarrantOffset(Pointer<CThostFtdcEWarrantOffsetField > pEWarrantOffset, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u05ea\ufffd\ufffd\ufffd\ufffd\u02ee\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTransferSerial(CThostFtdcTransferSerialField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:135</i>
     */
    @Virtual(35)
    public void OnRspQryTransferSerial(Pointer<CThostFtdcTransferSerialField > pTransferSerial, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\u01e9\u053c\ufffd\ufffd\u03f5\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryAccountregister(CThostFtdcAccountregisterField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:138</i>
     */
    @Virtual(36)
    public void OnRspQryAccountregister(Pointer<CThostFtdcAccountregisterField > pAccountregister, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
     * Original signature : <code>void OnRspError(CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:141</i>
     */
    @Virtual(37)
    public void OnRspError(Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast)
    {
        System.out.println( "TestTradeSpi OnRspError" );

        CThostFtdcRspInfoField rspInfo = pRspInfo.get( ) ;
        System.out.println("RspInfo.ErrorID = " + rspInfo.ErrorID() + " ErrorMsg = " + rspInfo.ErrorMsg().getCString()) ;
    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnOrder(CThostFtdcOrderField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:144</i>
     */
    @Virtual(38)
    public void OnRtnOrder(Pointer<CThostFtdcOrderField > pOrder)
    {
        System.out.println( "TestTradeSpi OnRtnOrder" ) ;

        CThostFtdcOrderField order = pOrder.get( ) ;

        String msg = String.format( "OnRtnOrder : OrderSysID = %s InstrumentID = %s LimitPrice = %f VolumeTotalOriginal = %d OrderStatus = %c" ,
                order.OrderSysID( ).getCString( ).trim( ) ,
                order.InstrumentID( ).getCString( ) ,
                order.LimitPrice( ) ,
                order.VolumeTotalOriginal( ) ,
                order.OrderStatus( )
        ) ;

        System.out.println( msg );

        /**
         *  发送撤单
        */
        char orderStatus = ( char )order.OrderStatus( ) ;
        if ( orderStatus != ThosttraderapiLibrary.THOST_FTDC_OST_Canceled )
        {
            CThostFtdcInputOrderActionField inputOrder = new CThostFtdcInputOrderActionField( ) ;
            inputOrder.setBrokerID( this.m_brokerID ) ;
            inputOrder.setInvestorID( this.m_userID ) ;
            inputOrder.setOrderRef( order.OrderRef( ).getCString( ) ) ;
            inputOrder.setFrontID( this.m_frontID );
            inputOrder.setSessionID( this.m_sessionID ) ;
            inputOrder.setOrderSysID( order.OrderSysID( ).getCString( ) ) ;
            inputOrder.setActionFlag( ( byte )ThosttraderapiLibrary.THOST_FTDC_AF_Delete ) ;
            inputOrder.setInstrumentID( order.InstrumentID( ).getCString( ) ) ;

            int nRet = this.m_api.ReqOrderAction( Pointer.pointerTo( inputOrder ) , 1 ) ;
            System.out.println( nRet == 0 ? "ReqOrderAction 成功" : "失败" );
        }
    }

    /**
     * \ufffd\u027d\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnTrade(CThostFtdcTradeField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:147</i>
     */
    @Virtual(39)
    public void OnRtnTrade(Pointer<CThostFtdcTradeField > pTrade)
    {
        System.out.println( "TestTradeSpi OnRtnTrade" ) ;

        CThostFtdcTradeField trade = pTrade.get( ) ;
        String msg = String.format( "OnRtnTrade : TradeID = %s InstrumentID = %s Price = %f Volume = %d TradeTime = %s" ,
                                    trade.TradeID( ).getCString( ).trim( )  ,
                                    trade.InstrumentID( ).getCString( ) ,
                                    trade.Price( ) ,
                                    trade.Volume( ) ,
                                    trade.TradeTime( ).getCString( )
                                    ) ;

        System.out.println( msg );
    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\u00bc\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnOrderInsert(CThostFtdcInputOrderField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:150</i>
     */
    @Virtual(40)
    public void OnErrRtnOrderInsert(Pointer<CThostFtdcInputOrderField > pInputOrder, Pointer<CThostFtdcRspInfoField > pRspInfo)
    {
           System.out.println( "TestTradeSpi OnErrRtnOrderInsert" );
    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnOrderAction(CThostFtdcOrderActionField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:153</i>
     */
    @Virtual(41)
    public void OnErrRtnOrderAction(Pointer<CThostFtdcOrderActionField > pOrderAction, Pointer<CThostFtdcRspInfoField > pRspInfo)
    {
        System.out.println( "TestTradeSpi OnErrRtnOrderAction" );
    }

    /**
     * \ufffd\ufffd\u053c\ufffd\ufffd\ufffd\ufffd\u05f4\u032c\u0368\u05aa<br>
     * Original signature : <code>void OnRtnInstrumentStatus(CThostFtdcInstrumentStatusField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:156</i>
     */
    @Virtual(42)
    public void OnRtnInstrumentStatus(Pointer<CThostFtdcInstrumentStatusField > pInstrumentStatus)
    {
        System.out.println( "TestTradeSpi OnRtnInstrumentStatus" );
    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnTradingNotice(CThostFtdcTradingNoticeInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:159</i>
     */
    @Virtual(43)
    public void OnRtnTradingNotice(Pointer<CThostFtdcTradingNoticeInfoField > pTradingNoticeInfo)
    {
        System.out.println( "TestTradeSpi OnRtnTradingNotice" );
    }

    /**
     * \ufffd\ufffd\u02be\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0423\ufffd\ufffd\ufffd\ufffd\ufffd<br>
     * Original signature : <code>void OnRtnErrorConditionalOrder(CThostFtdcErrorConditionalOrderField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:162</i>
     */
    @Virtual(44)
    public void OnRtnErrorConditionalOrder(Pointer<CThostFtdcErrorConditionalOrderField > pErrorConditionalOrder) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u01e9\u053c\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryContractBank(CThostFtdcContractBankField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:165</i>
     */
    @Virtual(45)
    public void OnRspQryContractBank(Pointer<CThostFtdcContractBankField > pContractBank, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0524\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryParkedOrder(CThostFtdcParkedOrderField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:168</i>
     */
    @Virtual(46)
    public void OnRspQryParkedOrder(Pointer<CThostFtdcParkedOrderField > pParkedOrder, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\u0524\ufffd\ud98f\uddf5\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryParkedOrderAction(CThostFtdcParkedOrderActionField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:171</i>
     */
    @Virtual(47)
    public void OnRspQryParkedOrderAction(Pointer<CThostFtdcParkedOrderActionField > pParkedOrderAction, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\u0368\u05aa\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryTradingNotice(CThostFtdcTradingNoticeField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:174</i>
     */
    @Virtual(48)
    public void OnRspQryTradingNotice(Pointer<CThostFtdcTradingNoticeField > pTradingNotice, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\ufffd\ufffd\u05f2\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryBrokerTradingParams(CThostFtdcBrokerTradingParamsField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:177</i>
     */
    @Virtual(49)
    public void OnRspQryBrokerTradingParams(Pointer<CThostFtdcBrokerTradingParamsField > pBrokerTradingParams, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\u0379\ufffd\u02fe\ufffd\ufffd\ufffd\ufffd\ufffd\u3de8\ufffd\ufffd\u04e6<br>
     * Original signature : <code>void OnRspQryBrokerTradingAlgos(CThostFtdcBrokerTradingAlgosField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:180</i>
     */
    @Virtual(50)
    public void OnRspQryBrokerTradingAlgos(Pointer<CThostFtdcBrokerTradingAlgosField > pBrokerTradingAlgos, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnFromBankToFutureByBank(CThostFtdcRspTransferField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:183</i>
     */
    @Virtual(51)
    public void OnRtnFromBankToFutureByBank(Pointer<CThostFtdcRspTransferField > pRspTransfer) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnFromFutureToBankByBank(CThostFtdcRspTransferField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:186</i>
     */
    @Virtual(52)
    public void OnRtnFromFutureToBankByBank(Pointer<CThostFtdcRspTransferField > pRspTransfer) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromBankToFutureByBank(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:189</i>
     */
    @Virtual(53)
    public void OnRtnRepealFromBankToFutureByBank(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromFutureToBankByBank(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:192</i>
     */
    @Virtual(54)
    public void OnRtnRepealFromFutureToBankByBank(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnFromBankToFutureByFuture(CThostFtdcRspTransferField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:195</i>
     */
    @Virtual(55)
    public void OnRtnFromBankToFutureByFuture(Pointer<CThostFtdcRspTransferField > pRspTransfer) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnFromFutureToBankByFuture(CThostFtdcRspTransferField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:198</i>
     */
    @Virtual(56)
    public void OnRtnFromFutureToBankByFuture(Pointer<CThostFtdcRspTransferField > pRspTransfer) {

    }

    /**
     * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromBankToFutureByFutureManual(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:201</i>
     */
    @Virtual(57)
    public void OnRtnRepealFromBankToFutureByFutureManual(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromFutureToBankByFutureManual(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:204</i>
     */
    @Virtual(58)
    public void OnRtnRepealFromFutureToBankByFutureManual(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnQueryBankBalanceByFuture(CThostFtdcNotifyQueryAccountField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:207</i>
     */
    @Virtual(59)
    public void OnRtnQueryBankBalanceByFuture(Pointer<CThostFtdcNotifyQueryAccountField > pNotifyQueryAccount) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnBankToFutureByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:210</i>
     */
    @Virtual(60)
    public void OnErrRtnBankToFutureByFuture(Pointer<CThostFtdcReqTransferField > pReqTransfer, Pointer<CThostFtdcRspInfoField > pRspInfo) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnFutureToBankByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:213</i>
     */
    @Virtual(61)
    public void OnErrRtnFutureToBankByFuture(Pointer<CThostFtdcReqTransferField > pReqTransfer, Pointer<CThostFtdcRspInfoField > pRspInfo) {

    }

    /**
     * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnRepealBankToFutureByFutureManual(CThostFtdcReqRepealField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:216</i>
     */
    @Virtual(62)
    public void OnErrRtnRepealBankToFutureByFutureManual(Pointer<CThostFtdcReqRepealField > pReqRepeal, Pointer<CThostFtdcRspInfoField > pRspInfo) {

    }

    /**
     * \u03f5\u0373\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\u05b9\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnRepealFutureToBankByFutureManual(CThostFtdcReqRepealField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:219</i>
     */
    @Virtual(63)
    public void OnErrRtnRepealFutureToBankByFutureManual(Pointer<CThostFtdcReqRepealField > pReqRepeal, Pointer<CThostFtdcRspInfoField > pRspInfo) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0631\ufffd<br>
     * Original signature : <code>void OnErrRtnQueryBankBalanceByFuture(CThostFtdcReqQueryAccountField*, CThostFtdcRspInfoField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:222</i>
     */
    @Virtual(64)
    public void OnErrRtnQueryBankBalanceByFuture(Pointer<CThostFtdcReqQueryAccountField > pReqQueryAccount, Pointer<CThostFtdcRspInfoField > pRspInfo) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05ea\ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromBankToFutureByFuture(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:225</i>
     */
    @Virtual(65)
    public void OnRtnRepealFromBankToFutureByFuture(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0434\ufffd\ufffd\ufffd\ufffd\ufffd\u03fa\ufffd\ufffd\u0337\ufffd\ufffd\u0635\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnRepealFromFutureToBankByFuture(CThostFtdcRspRepealField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:228</i>
     */
    @Virtual(66)
    public void OnRtnRepealFromFutureToBankByFuture(Pointer<CThostFtdcRspRepealField > pRspRepeal) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\u06bb\ufffd\u04e6\ufffd\ufffd<br>
     * Original signature : <code>void OnRspFromBankToFutureByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:231</i>
     */
    @Virtual(67)
    public void OnRspFromBankToFutureByFuture(Pointer<CThostFtdcReqTransferField > pReqTransfer, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bb\ufffd\ufffd\u02bd\ufffd\u05ea\ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
     * Original signature : <code>void OnRspFromFutureToBankByFuture(CThostFtdcReqTransferField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:234</i>
     */
    @Virtual(68)
    public void OnRspFromFutureToBankByFuture(Pointer<CThostFtdcReqTransferField > pReqTransfer, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\u06bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u046f\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u04e6\ufffd\ufffd<br>
     * Original signature : <code>void OnRspQueryBankAccountMoneyByFuture(CThostFtdcReqQueryAccountField*, CThostFtdcRspInfoField*, int, bool)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:237</i>
     */
    @Virtual(69)
    public void OnRspQueryBankAccountMoneyByFuture(Pointer<CThostFtdcReqQueryAccountField > pReqQueryAccount, Pointer<CThostFtdcRspInfoField > pRspInfo, int nRequestID, boolean bIsLast) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u06bf\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnOpenAccountByBank(CThostFtdcOpenAccountField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:240</i>
     */
    @Virtual(70)
    public void OnRtnOpenAccountByBank(Pointer<CThostFtdcOpenAccountField > pOpenAccount) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnCancelAccountByBank(CThostFtdcCancelAccountField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:243</i>
     */
    @Virtual(71)
    public void OnRtnCancelAccountByBank(Pointer<CThostFtdcCancelAccountField > pCancelAccount) {

    }

    /**
     * \ufffd\ufffd\ufffd\u0437\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u02fa\ufffd\u0368\u05aa<br>
     * Original signature : <code>void OnRtnChangeAccountByBank(CThostFtdcChangeAccountField*)</code><br>
     * <i>native declaration : ctpapi/linux/ThostFtdcTraderApi.h:246</i>
     */
    @Virtual(72)
    public void OnRtnChangeAccountByBank(Pointer<CThostFtdcChangeAccountField > pChangeAccount) {

    }

}
