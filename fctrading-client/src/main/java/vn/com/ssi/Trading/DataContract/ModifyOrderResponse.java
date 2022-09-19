package vn.com.ssi.Trading.DataContract;

public class ModifyOrderResponse {
    public class ModifyOrder{
        public String orderID;
        public double price;
        public long quantity;
        public String account;
        public String instrumentID;
        public String marketID;
        public String buySell;
        public String orderType;
    }
    public String requestID;
    public ModifyOrder requestData;
}
