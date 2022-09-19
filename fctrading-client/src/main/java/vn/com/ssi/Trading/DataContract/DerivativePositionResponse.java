package vn.com.ssi.Trading.DataContract;

import java.util.ArrayList;

public class DerivativePositionResponse {
    public class DerivativePositionItem {
        public String marketID;
        public String instrumentID;
        public double longQty;
        public double shortQty;
        public double net;
        public double bidAvgPrice;
        public double askAvgPrice;
        public double tradePrice;
        public double marketPrice;
        public double floatingPL;
        public double tradingPL;
    }

    public String account;
    public ArrayList<DerivativePositionItem> openPosition = new ArrayList<DerivativePositionItem>();
    public ArrayList<DerivativePositionItem> closePosition = new ArrayList<DerivativePositionItem>();
}
