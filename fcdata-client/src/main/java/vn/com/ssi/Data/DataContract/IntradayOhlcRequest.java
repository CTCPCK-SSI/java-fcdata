package vn.com.ssi.Data.DataContract;

public class IntradayOhlcRequest {
    public String symbol = "";
    public String fromDate = "";
    public String toDate = "";
    public int resolution=60;
    public int pageIndex = 1;
    public int pageSize = 100;
    public boolean ascending = false;
}
