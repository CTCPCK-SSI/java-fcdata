package vn.com.ssi.Data.DataContract;

import java.util.ArrayList;

public class SecuritiesDetailResponse {
    public String RType;
    public String ReportDate;
    public String TotalNoSym;
    public ArrayList<Info> RepeatedInfo = new ArrayList<Info>();

    public class Info{
        public String Isin;
        public String Symbol;
        public String SymbolName;
        public String SymbolEngName;
        public String SecType;
        public String MarketId;
        public String Exchange;
        public String Issuer;
        public String LotSize;
        public String IssueDate;
        public String MaturityDate;
        public String FirstTradingDate;
        public String LastTradingDate;
        public String ContractMultiplier;
        public String SettlMethod;
        public String Underlying;
        public String PutOrCall;
        public String ExercisePrice;
        public String ExerciseStyle;
        public String ExcerciseRatio;
        public String ListedShare;
        public String TickPrice1;
        public String TickIncrement1;
        public String TickPrice2 ;
        public String TickIncrement2 ;
        public String TickPrice3 ;
        public String TickIncrement3 ;
        public String TickPrice4;
        public String TickIncrement4 ;
    }
}
