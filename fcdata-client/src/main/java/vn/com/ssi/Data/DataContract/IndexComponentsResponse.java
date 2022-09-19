package vn.com.ssi.Data.DataContract;

import java.util.ArrayList;

public class IndexComponentsResponse {
    public String IndexCode;
    public String IndexName;
    public String Exchange;
    public String TotalSymbolNo;
    public ArrayList<IndexComponent> IndexComponent = new ArrayList<IndexComponent>();
    public class IndexComponent{
        public String Isin;
        public String StockSymbol;
    }
}
