package vn.com.ssi.Trading.DataContract;

public class NewOrderResponse {
    public class NewOrder {
        private String instrumentID;
        private String market;
        private String buySell;
        private String orderType;
        private String channelID;
        private double price;
        private double quantity;
        private String account;
        private boolean stopOrder;
        private double stopPrice;
        private String stopType;
        private double stopStep;
        private double lossStep;
        private double profitStep;
    }
    public String requestID;
    public NewOrder requestData;
}
