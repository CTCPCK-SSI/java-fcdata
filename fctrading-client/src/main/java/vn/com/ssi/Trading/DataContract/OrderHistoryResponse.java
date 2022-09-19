package vn.com.ssi.Trading.DataContract;

import java.util.ArrayList;

public class OrderHistoryResponse {
    public class OrderHistoryItem {
        public String uniqueID;
        public String orderID;
        public String buySell;
        public double price;
        public long quantity;
        public long filledQty;
        public String orderStatus;
        public String marketID;
        public String inputTime;
        public String modifiedTime;
        public String instrumentID;
        public String orderType;
        public long cancelQty;
        public double avgPrice;
        public String isForcesell;
        public String isShortsell;
    }

    public ArrayList<OrderHistoryItem> orderHistories = new ArrayList<OrderHistoryItem>();
    public String account;

}
