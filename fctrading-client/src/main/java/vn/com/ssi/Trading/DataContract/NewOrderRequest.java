package vn.com.ssi.Trading.DataContract;

public class NewOrderRequest {
    public String instrumentID;
    public String market;
    public String buySell;
    public String orderType;
    public String channelID;
    public double price;
    public double quantity;
    public String account;
    public String requestID;
    public boolean stopOrder = false;
    public double stopPrice;
    public String stopType;
    public double stopStep;
    public double lossStep;
    public double profitStep;
    public String code;
    public boolean modifiable = true;

}
