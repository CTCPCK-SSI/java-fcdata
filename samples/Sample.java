import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import com.ssi.fcdata.DataContract.*;
import com.ssi.fcdata.FCDataClient;
import com.ssi.fcdata.FCDataStreaming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Scanner;

public class Sample {
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
            System.out.println("3. Exit");
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
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCDataAPI() throws Exception {

        InputStream jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
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
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("SecuritiesRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecurities(gson.fromJson(data, SecuritiesRequest.class))));
                    break;
                case "2":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("SecuritiesDetailRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecuritiesDetail(gson.fromJson(data, SecuritiesDetailRequest.class))));
                    break;
                case "3":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IndexComponentsRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexComponents(gson.fromJson(data, IndexComponentsRequest.class))));
                    break;
                case "4":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IndexListRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexList(gson.fromJson(data, IndexListRequest.class))));
                    break;
                case "5":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DailyOhlcRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyOhlc(gson.fromJson(data, DailyOhlcRequest.class))));
                    break;
                case "6":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("IntradayOhlcRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIntradayOhlc(gson.fromJson(data, IntradayOhlcRequest.class))));
                    break;
                case "7":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DailyIndexRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyIndex(gson.fromJson(data, DailyIndexRequest.class))));
                    break;
                case "8":
                    jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
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
        InputStream jsonStream = Sample.class.getResourceAsStream("/fcdata.json");
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

    
}