package vn.com.ssi.Trading.DataContract;

public class DerivativeBalanceResponse {
    public String account;
    public double accountBalance;
    public double fee;
    public double commission;
    public double interest;
    public double loan;
    public double deliveryAmount;
    public double floatingPL;
    public double totalPL;
    public double marginable;
    public double depositable;
    public double rcCall;
    public double withdrawable;
    public double nonCashDrawableRCCall;
    public InternalAssets InternalAssetsObject;
    public ExchangeAssets ExchangeAssetsObject;
    public InternalMargin InternalMarginObject;
    public ExchangeMargin ExchangeMarginObject;


    public class ExchangeMargin {
        public double marginReq;
        public double accountRatio;
        public double usedLimitWarningLevel1;
        public double usedLimitWarningLevel2;
        public double usedLimitWarningLevel3;
        public double marginCall;


    }

    public class InternalMargin {
        public double initialMargin;
        public double deliveryMargin;
        public double marginReq;
        public double accountRatio;
        public double usedLimitWarningLevel1;
        public double usedLimitWarningLevel2;
        public double usedLimitWarningLevel3;
        public double marginCall;

    }

    public class ExchangeAssets {
        public double cash;
        public double validNonCash;
        public double totalValue;
        public double maxValidNonCash;
        public double cashWithdrawable;
        public double ee;


    }

    public class InternalAssets {
        public double cash;
        public double validNonCash;
        public double totalValue;
        public double maxValidNonCash;
        public double cashWithdrawable;
        public double ee;


    }


}