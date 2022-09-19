package vn.com.ssi.Trading.DataContract;

public class CancelOrderResponse {
    public class CancelOrder{
        public String orderID;
        public String account;
        public String marketID;
        public String instrumentID;
        public String buySell;
        public String requestID;
    }
    public String requestID;
    public CancelOrder requestData;
}
