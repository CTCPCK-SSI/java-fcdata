package vn.com.ssi.Trading.DataContract;

import java.util.ArrayList;

public class StockPositionResponse {
    public class StockPositionItem {
        public String marketID;
        public String instrumentID;
        public double onHand;
        public double block;
        public double bonus;
        public double buyT0;
        public double buyT1;
        public double buyT2;
        public double sellT0;
        public double sellT1;
        public double sellT2;
        public double avgPrice;
        public double mortgage;
        public double sellableQty;
        public double holdForTrade;
        public double marketPrice;
    }

    public String account;
    public double totalMarketValue;
    public ArrayList<StockPositionItem> stockPositions = new ArrayList<StockPositionItem>();
}
