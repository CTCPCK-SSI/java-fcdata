package com.ssi.fcdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import com.ssi.fcdata.API;
import com.ssi.fcdata.DataContract.AccessTokenRequest;
import com.ssi.fcdata.DataContract.AccessTokenResponse;
import com.ssi.fcdata.DataContract.*;
import com.ssi.fcdata.DataContract.Response;
import com.ssi.fcdata.UriFormat;

import java.util.List;

public class FCDataClient {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    // PRIVATE
    private final String _consumerId;
    private final String _consumerSecret;
    private final String _url;
    private final OkHttpClient _client = new OkHttpClient();
    private AccessTokenResponse _accessToken = null;
    // PUBLIC
    public FCDataClient(String consumerId, String consumerSecret, String url){
        _consumerId = consumerId;
        _consumerSecret = consumerSecret;
        _url = url;
    }
    public void init() throws Exception {
        AccessTokenRequest req = new AccessTokenRequest();
        req.consumerID = _consumerId;
        req.consumerSecret = _consumerSecret;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new ObjectMapper().writeValueAsString(req);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(_url + API.ACCESS_TOKEN)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            GenneralResponse<AccessTokenResponse> access = objectMapper.readValue(rString, new TypeReference<GenneralResponse<AccessTokenResponse>>() {
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
    public String GetAccessToken() throws Exception {
        if(_accessToken == null)
            this.init();
        assert _accessToken != null;
        return  _accessToken.accessToken;
    }

    public <TRequest, TResponse> Response<TResponse> get(TRequest reqObj, String urlPath) throws Exception {
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
    public Response<List<SecuritiesResponse>> GetSecurities(SecuritiesRequest req) throws Exception {
        return get(req, API.GET_SECURITIES);
    }
    public Response<List<SecuritiesResponse>> GetSecuritiesDetail(SecuritiesDetailRequest req) throws Exception {
        return get(req, API.GET_SECURITIES_DETAIL);
    }
    public Response<List<IndexComponentsResponse>> GetIndexComponents(IndexComponentsRequest req) throws Exception {
        return get(req, API.GET_INDEX_COMPONENTS);
    }
    public Response<List<IndexListResponse>> GetIndexList(IndexListRequest req) throws Exception {
        return get(req, API.GET_INDEX_LIST);
    }
    public Response<List<DailyOhlcResponse>> GetDailyOhlc(DailyOhlcRequest req) throws Exception {
        return get(req, API.GET_DAILY_OHLC);
    }
    public Response<List<IntradayOhlcResponse>> GetIntradayOhlc(IntradayOhlcRequest req) throws Exception {
        return get(req, API.GET_INTRADAY_OHLC);
    }
    public Response<List<DailyIndexResponse>> GetDailyIndex(DailyIndexRequest req) throws Exception {
        return get(req, API.GET_DAILY_INDEX);
    }
    public Response<List<DailyStockPriceResponse>> GetDailyStockPrice(DailyStockPriceRequest req) throws Exception {
        return get(req, API.GET_DAILY_STOCKPRICE);
    }
    public static void main(String[] args) throws Exception {
        FCDataClient client = new FCDataClient("", "","");
        client.init();


        SecuritiesRequest rq1 = new SecuritiesRequest();
        rq1.market = "HOSE";
        System.out.println("Securities =" + new ObjectMapper().writeValueAsString(client.GetSecurities(rq1)));

        SecuritiesDetailRequest rq2 = new SecuritiesDetailRequest();
        rq2.market = "HOSE";
        rq2.symbol = "SSI";
        System.out.println("SecuritiesDetailRequest =" + new ObjectMapper().writeValueAsString(client.GetSecuritiesDetail(rq2)));

        IndexComponentsRequest IndexComponentsRequest = new IndexComponentsRequest();
        IndexComponentsRequest.indexCode = "VN30";
        System.out.println("IndexComponentsRequest =" + new ObjectMapper().writeValueAsString(client.GetIndexComponents(IndexComponentsRequest)));

        IndexListRequest IndexListRequest = new IndexListRequest();
        IndexListRequest.exchange = "HOSE";
        System.out.println("IndexListRequest =" + new ObjectMapper().writeValueAsString(client.GetIndexList(IndexListRequest)));

        DailyOhlcRequest DailyOhlcRequest = new DailyOhlcRequest();
        DailyOhlcRequest.symbol = "SSI";
        DailyOhlcRequest.fromDate = "17/08/2022";
        DailyOhlcRequest.toDate = "18/08/2022";
        System.out.println("DailyOhlcRequest =" + new ObjectMapper().writeValueAsString(client.GetDailyOhlc(DailyOhlcRequest)));

        IntradayOhlcRequest IntradayOhlcRequest = new IntradayOhlcRequest();
        IntradayOhlcRequest.symbol = "SSI";
        IntradayOhlcRequest.fromDate = "17/08/2022";
        IntradayOhlcRequest.toDate = "18/08/2022";
        IntradayOhlcRequest.resolution = 60;
        System.out.println("IntradayOhlcRequest =" + new ObjectMapper().writeValueAsString(client.GetIntradayOhlc(IntradayOhlcRequest)));

        DailyIndexRequest DailyIndexRequest = new DailyIndexRequest();
        DailyIndexRequest.indexId = "VN30";
        DailyIndexRequest.fromDate = "16/08/2022";
        DailyIndexRequest.toDate = "18/08/2022";
        System.out.println("DailyIndexRequest =" + new ObjectMapper().writeValueAsString(client.GetDailyIndex(DailyIndexRequest)));

        DailyStockPriceRequest DailyStockPriceRequest = new DailyStockPriceRequest();
        DailyStockPriceRequest.symbol = "SSI";
        DailyStockPriceRequest.fromDate = "16/08/2022";
        DailyStockPriceRequest.toDate = "18/08/2022";
        System.out.println("DailyStockPriceRequest =" + new ObjectMapper().writeValueAsString(client.GetDailyStockPrice(DailyStockPriceRequest)));


    }
}
