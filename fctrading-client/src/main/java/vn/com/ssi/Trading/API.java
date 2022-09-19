package vn.com.ssi.Trading;

public class API {

    final static String ACCESS_TOKEN = "/api/v2/Trading/AccessToken";
    final static String NEW_ORDER = "/api/v2/Trading/NewOrder";
    final static String MODIFY_ORDER = "/api/v2/Trading/ModifyOrder";
    final static String CANCEL_ORDER = "/api/v2/Trading/CancelOrder";
    final static String GET_ORDER_HISTORY = "/api/v2/Trading/orderHistory";
    final static String GET_DER_POSITION = "/api/v2/Trading/derivPosition";
    final static String GET_STOCK_POSITION = "/api/v2/Trading/stockPosition";
    final static String GET_MAX_BUY_QUANTITY = "/api/v2/Trading/maxBuyQty";
    final static String GET_MAX_SELL_QUANTITY = "/api/v2/Trading/maxSellQty";
    final static String GET_ACCOUNT_BALANCE = "/api/v2/Trading/cashAcctBal";
    final static String GET_DER_ACCOUNT_BALANCE = "/api/v2/Trading/derivAcctBal";
    final static String GET_PPMMRACCOUNT = "/api/v2/Trading/ppmmraccount";

    final static String HUB_NAME = "BroadcastHubV2";
    final static String HUB_EVENT="Broadcast";
}
