package vn.com.ssi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import vn.com.ssi.Data.DataContract.*;
import vn.com.ssi.Data.FCDataClient;
import vn.com.ssi.Data.FCDataStreaming;
import vn.com.ssi.Trading.DataContract.*;
import vn.com.ssi.Trading.FCTradingClient;
import vn.com.ssi.Trading.FCTradingStreaming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static String read(InputStream s) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = s.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
    }
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1. FCData API");
            System.out.println("2. FCData Streaming");
            System.out.println("3. FCTrading API");
            System.out.println("4. FCTrading Streaming");
            System.out.println("5. Exit");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            switch (line) {
                case "1":
                    FCDataAPI();
                    break;
                case "2":
                    FCDataStreaming();
                    break;
                case "3":
                    FCTradingAPI();
                    break;
                case "4":
                    FCTradingStreaming();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCDataAPI() throws Exception {

        InputStream jsonStream = Main.class.getResourceAsStream("/fcdata.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCDataClient client = new FCDataClient(config.get("consumerId").getAsString(), config.get("consumerSecret").getAsString(), config.get("url").getAsString());
        client.init();
        String data;
        while (true) {
            System.out.println("1. SecuritiesRequest");
            System.out.println("2. SecuritiesDetailRequest");
            System.out.println("3. IndexComponentsRequest");
            System.out.println("4. IndexListRequest");
            System.out.println("5. DailyOhlcRequest");
            System.out.println("6. IntradayOhlcRequest");
            System.out.println("7. DailyIndexRequest");
            System.out.println("8. DailyStockPriceRequest");
            System.out.println("9. Back");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            switch (line) {
                case "1":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("SecuritiesRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecurities(gson.fromJson(data, SecuritiesRequest.class))));
                    break;
                case "2":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("SecuritiesDetailRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecuritiesDetail(gson.fromJson(data, SecuritiesDetailRequest.class))));
                    break;
                case "3":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IndexComponentsRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexComponents(gson.fromJson(data, IndexComponentsRequest.class))));
                    break;
                case "4":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IndexListRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexList(gson.fromJson(data, IndexListRequest.class))));
                    break;
                case "5":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DailyOhlcRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyOhlc(gson.fromJson(data, DailyOhlcRequest.class))));
                    break;
                case "6":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IntradayOhlcRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIntradayOhlc(gson.fromJson(data, IntradayOhlcRequest.class))));
                    break;
                case "7":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DailyIndexRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyIndex(gson.fromJson(data, DailyIndexRequest.class))));
                    break;
                case "8":
                    jsonStream = Main.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DailyStockPriceRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyStockPrice(gson.fromJson(data, DailyStockPriceRequest.class))));
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCDataStreaming() throws Exception {
        InputStream jsonStream = Main.class.getResourceAsStream("/fcdata.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCDataClient client = new FCDataClient(config.get("consumerId").getAsString(), config.get("consumerSecret").getAsString(), config.get("url").getAsString());
        client.init();
        FCDataStreaming streaming = new FCDataStreaming(client, config.get("streaming_url").getAsString());
        streaming.setChannel(config.get("channels").getAsString());
        streaming.onReceived(new MessageReceivedHandler() {
            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("onMessageReceived " + json.getAsString());
            }
        });
        streaming.start();
        Scanner inputReader = new Scanner(System.in);
        String line = inputReader.nextLine();
        while (!"exit".equals(line)) {

            line = inputReader.next();
        }
    }

    public static void FCTradingAPI() throws Exception {
        InputStream jsonStream = Main.class.getResourceAsStream("/fctrading.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCTradingClient client = new FCTradingClient(config.get("consumerId").getAsString()
                , config.get("consumerSecret").getAsString()
                , config.get("privateKey").getAsString()
                , config.get("code").getAsString()
                , config.get("url").getAsString());
        client.init();
        String data;
        while (true) {
            System.out.println("1.  NewOrder");
            System.out.println("2.  ModifyOrder");
            System.out.println("3.  CancelOrder");
            System.out.println("4.  GetDerivativeBalance");
            System.out.println("5.  GetCashBalance");
            System.out.println("6.  GetPPMMRAccount");
            System.out.println("7.  GetStockPosition");
            System.out.println("8.  GetDerivativePosition");
            System.out.println("9.  GetMaxBuyQty");
            System.out.println("10. GetMaxSellQty");
            System.out.println("11. GetOrderHistory");
            System.out.println("12. Back");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            switch (line) {
                case "1":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("NewOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.NewOrder(gson.fromJson(data, NewOrderRequest.class))));
                    break;
                case "2":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("ModifyOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.ModifyOrder(gson.fromJson(data, ModifyOrderRequest.class))));
                    break;
                case "3":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("CancelOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CancelOrder(gson.fromJson(data, CancelOrderRequest.class))));
                    break;
                case "4":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("CashAccountBalanceRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCashBalance(gson.fromJson(data, AccountBalanceRequest.class))));
                    break;
                case "5":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DerCashAccountBalanceRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativeBalance(gson.fromJson(data, AccountBalanceRequest.class))));
                    break;
                case "6":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("PPMMRAccountRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetPPMMRAccount(gson.fromJson(data, PPMMRAccountRequest.class))));
                    break;
                case "7":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("StockAccountPositionRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetStockPosition(gson.fromJson(data, AccountPositionRequest.class))));
                    break;
                case "8":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DerAccountPositionRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativePosition(gson.fromJson(data, AccountPositionRequest.class))));
                    break;
                case "9":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("MaxBuyQtyRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxBuyQty(gson.fromJson(data, MaxQtyRequest.class))));
                    break;
                case "10":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("MaxSellQtyRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxSellQty(gson.fromJson(data, MaxQtyRequest.class))));
                    break;
                case "11":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("OrderHistoryRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrderHistory(gson.fromJson(data, OrderHistoryRequest.class))));
                    break;
                case "12":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCTradingStreaming() throws Exception {
        InputStream jsonStream = Main.class.getResourceAsStream("/fctrading.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCTradingClient client = new FCTradingClient(config.get("consumerId").getAsString()
                , config.get("consumerSecret").getAsString()
                , config.get("privateKey").getAsString()
                , config.get("code").getAsString()
                , config.get("url").getAsString());
        client.init();
        FCTradingStreaming streaming = new FCTradingStreaming(client, config.get("streaming_url").getAsString());
        streaming.onReceived(new MessageReceivedHandler() {
            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("onMessageReceived " + json.getAsString());
            }
        });
        streaming.start();
        // Read lines and send them as messages.
        Scanner inputReader = new Scanner(System.in);
        String line = inputReader.nextLine();
        while (!"exit".equals(line)) {

            line = inputReader.next();
        }
    }
}