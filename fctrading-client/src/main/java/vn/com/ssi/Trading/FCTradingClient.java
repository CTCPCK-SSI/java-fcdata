
package vn.com.ssi.Trading;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import vn.com.ssi.Trading.DataContract.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

/**
 * The main class.
 *
 * @author ducdv@ssi.com.vn
 */
public class FCTradingClient {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    // PRIVATE
    private final String _consumerId;
    private final String _consumerSecret;
    private final PrivateKey _privateKey;
    private final String _code;
    private final String _url;
    private final boolean _isSave;
    private final OkHttpClient _client = new OkHttpClient();
    private AccessTokenResponse _accessToken = null;
    // PUBLIC

    /**
     * Main class.
     */
    public FCTradingClient(String consumerId, String consumerSecret, String privateKey, String code, String url) throws NoSuchAlgorithmException, InvalidKeySpecException {
        _consumerId = consumerId;
        _consumerSecret = consumerSecret;
        _privateKey = Helper.importPrivateKey(privateKey);
        _code = code;
        _isSave = true;
        _url = url;
    }

    public FCTradingClient(String consumerId, String consumerSecret, String privateKey, String code, String url, boolean isSave) throws NoSuchAlgorithmException, InvalidKeySpecException {
        _consumerId = consumerId;
        _consumerSecret = consumerSecret;
        _privateKey = Helper.importPrivateKey(privateKey);
        _code = code;
        _isSave = isSave;
        if(url.endsWith("/"))
            _url = url.substring(0,url.length() -1) ;
        else
            _url = url;
    }

    public void init() throws Exception {
        AccessTokenRequest req = new AccessTokenRequest();
        req.consumerID = _consumerId;
        req.consumerSecret = _consumerSecret;
        req.twoFactorType = 0;
        req.code = _code;
        req.isSave = _isSave;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new ObjectMapper().writeValueAsString(req);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(_url + API.ACCESS_TOKEN)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            Response<AccessTokenResponse> access = objectMapper.readValue(rString, new TypeReference<Response<AccessTokenResponse>>() {
            });
            if (access.status == 200)
                _accessToken = access.data;
            else
                System.out.println(access.message);
            System.out.println("AccessToken = " + _accessToken.accessToken);
        } catch (Exception e) {
            throw e;
        }
    }

    public <TRequest, TResponse> Response<TResponse> post(TRequest reqObj, String urlPath, Class<TResponse> classOfResponse) throws Exception {
        assert _accessToken != null;
        ObjectMapper objectMapper = new ObjectMapper();
        String payLoad = objectMapper.writeValueAsString(reqObj);
        String signature = Helper.sign(_privateKey, payLoad.getBytes(StandardCharsets.UTF_8));

        RequestBody body = RequestBody.create(payLoad, JSON);
        Request request = new Request.Builder()
                .url(_url + urlPath)
                .addHeader("Authorization", "Bearer " + _accessToken.accessToken)
                .addHeader("X-Signature", signature)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            return objectMapper.readValue(rString, new TypeReference<Response<TResponse>>() {
            });
        } catch (Exception e) {
            throw e;
        }
    }
    public String GetAccessToken() throws Exception {
        if(_accessToken == null)
            this.init();
        assert _accessToken != null;
        return  _accessToken.accessToken;
    }
    public <TRequest, TResponse> Response<TResponse> get(TRequest reqObj, String urlPath, Class<TResponse> classOfResponse) throws Exception {
        assert _accessToken != null;
        ObjectMapper objectMapper = new ObjectMapper();
        String param = objectMapper.convertValue(reqObj, UriFormat.class).toString();
        Request request = new Request.Builder()
                .url(_url + urlPath + "?" + param)
                .addHeader("Authorization", "Bearer " + _accessToken.accessToken)
                .get()
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            return objectMapper.readValue(rString, new TypeReference<Response<TResponse>>() {
            });
        }
    }

    public Response<NewOrderResponse> NewOrder(NewOrderRequest req) throws Exception {
        return post(req, API.NEW_ORDER, NewOrderResponse.class);
    }
    public Response<ModifyOrderResponse> ModifyOrder(ModifyOrderRequest req) throws Exception {
        return post(req, API.MODIFY_ORDER, ModifyOrderResponse.class);
    }
    public Response<CancelOrderResponse> CancelOrder(CancelOrderRequest req) throws Exception {
        return post(req, API.NEW_ORDER, CancelOrderResponse.class);
    }

    public Response<DerivativeBalanceResponse> GetDerivativeBalance(AccountBalanceRequest req) throws Exception {
        return get(req, API.GET_DER_ACCOUNT_BALANCE, DerivativeBalanceResponse.class);
    }

    public Response<CashBalanceResponse> GetCashBalance(AccountBalanceRequest req) throws Exception {
        return get(req, API.GET_ACCOUNT_BALANCE, CashBalanceResponse.class);
    }

    public Response<PPMMRAccountResponse> GetPPMMRAccount(PPMMRAccountRequest req) throws Exception {
        return get(req, API.GET_PPMMRACCOUNT, PPMMRAccountResponse.class);
    }

    public Response<StockPositionResponse> GetStockPosition(AccountPositionRequest req) throws Exception {
        return get(req, API.GET_STOCK_POSITION, StockPositionResponse.class);
    }

    public Response<DerivativePositionResponse> GetDerivativePosition(AccountPositionRequest req) throws Exception {
        return get(req, API.GET_DER_POSITION, DerivativePositionResponse.class);

    }

    public Response<MaxBuyQtyResponse> GetMaxBuyQty(MaxQtyRequest req) throws Exception {
        return get(req, API.GET_MAX_BUY_QUANTITY, MaxBuyQtyResponse.class);
    }

    public Response<MaxSellQtyResponse> GetMaxSellQty(MaxQtyRequest req) throws Exception {
        return get(req, API.GET_MAX_SELL_QUANTITY, MaxSellQtyResponse.class);
    }

    public Response<OrderHistoryResponse> GetOrderHistory(OrderHistoryRequest req) throws Exception {
        return get(req, API.GET_ORDER_HISTORY, OrderHistoryResponse.class);
    }

    public static void main(String[] args) throws Exception {
        FCTradingClient client = new FCTradingClient("", ""
                , ""
                , ""
                , "http://192.168.213.98:1150"
                , true
        );
        client.init();

        NewOrderRequest newOrderRequest = new NewOrderRequest();
        newOrderRequest.account = "0000038";
        newOrderRequest.buySell = "B";
        newOrderRequest.channelID = "TA";
        newOrderRequest.instrumentID = "VN30F2208";
        newOrderRequest.market = "VNFE";
        newOrderRequest.modifiable = true;
        newOrderRequest.orderType = "LO";
        newOrderRequest.price = 1133.6;
        newOrderRequest.stopOrder = false;
        newOrderRequest.quantity = 1;
        newOrderRequest.requestID = "02";
        System.out.println("NEW ORDER =" + new ObjectMapper().writeValueAsString(client.NewOrder(newOrderRequest)));
        AccountBalanceRequest req = new AccountBalanceRequest();
        req.account = "0000038";
        System.out.println("DER BAL=" + new ObjectMapper().writeValueAsString(client.GetDerivativeBalance(req)));
        req.account = "0000038";
        System.out.println("CASH BAL=" + new ObjectMapper().writeValueAsString(client.GetCashBalance(req)));
        PPMMRAccountRequest ppmmr = new PPMMRAccountRequest();
        ppmmr.account = "0000031";
        System.out.println("PPMMR =" + new ObjectMapper().writeValueAsString(client.GetPPMMRAccount(ppmmr)));
        AccountPositionRequest positionRequest = new AccountPositionRequest();
        positionRequest.account = "0000031";
        System.out.println("GetStockPosition =" + new ObjectMapper().writeValueAsString(client.GetStockPosition(positionRequest)));
        positionRequest.account = "0000038";
        System.out.println("GetDerivativePosition =" + new ObjectMapper().writeValueAsString(client.GetDerivativePosition(positionRequest)));
        MaxQtyRequest maxQtyRequest = new MaxQtyRequest();
        maxQtyRequest.account = "0000031";
        maxQtyRequest.instrumentID = "SSI";
        maxQtyRequest.price = 20000;
        System.out.println("GetMaxBuyQty =" + new ObjectMapper().writeValueAsString(client.GetMaxBuyQty(maxQtyRequest)));
        System.out.println("GetMaxSellQty =" + new ObjectMapper().writeValueAsString(client.GetMaxSellQty(maxQtyRequest)));
        OrderHistoryRequest orderHistoryRequest = new OrderHistoryRequest();
        orderHistoryRequest.account = "0000031";
        orderHistoryRequest.startDate = "20/07/2022";
        orderHistoryRequest.endDate = "27/07/2022";
        System.out.println("GetOrderHistory =" + new ObjectMapper().writeValueAsString(client.GetOrderHistory(orderHistoryRequest)));
    }
}
