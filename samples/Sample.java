import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import com.ssi.fcdata.DataContract.*;
import com.ssi.fcdata.FCDataClient;
import com.ssi.fcdata.FCDataStreaming;
import com.google.gson.JsonElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ssi.fcdata.DataContract.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

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
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("C:\\Users\\hoaht\\Desktop\\fcdata.json"));
        // File currentDir = new File("");
        // String absolutePath = currentDir.getAbsolutePath();
       // Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fcdata.json"));
        JSONObject jsonObject = (JSONObject)obj;
        String consumerId = (String)jsonObject.get("consumerId");
        String consumerSecret = (String)jsonObject.get("consumerSecret");
        String url = (String)jsonObject.get("url");
        FCDataClient client = new FCDataClient(consumerId, consumerSecret, url);
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
            Gson g = new Gson();
            switch (line) {
                case "1":
                    JSONObject securitiesRequest = (JSONObject)jsonObject.get("SecuritiesRequest");
                    SecuritiesRequest dataSecuritiesRequest = g.fromJson(securitiesRequest.toJSONString(),SecuritiesRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecurities(dataSecuritiesRequest)));
                    break;
                case "2":
                    JSONObject securitiesDetailRequest = (JSONObject)jsonObject.get("SecuritiesDetailRequest");
                    SecuritiesDetailRequest dataSecuritiesDetailRequest = g.fromJson(securitiesDetailRequest.toJSONString(),SecuritiesDetailRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetSecuritiesDetail(dataSecuritiesDetailRequest)));
                    break;
                case "3":
                    JSONObject indexComponentsRequest = (JSONObject)jsonObject.get("IndexComponentsRequest");
                    IndexComponentsRequest dataIndexComponentsRequest = g.fromJson(indexComponentsRequest.toJSONString(),IndexComponentsRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexComponents(dataIndexComponentsRequest)));
                    break;
                case "4":
                    JSONObject indexListRequest = (JSONObject)jsonObject.get("IndexListRequest");
                    IndexListRequest dataIndexListRequest = g.fromJson(indexListRequest.toJSONString(),IndexListRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIndexList(dataIndexListRequest)));
                    break;
                case "5":
                    JSONObject dailyOhlcRequest = (JSONObject)jsonObject.get("DailyOhlcRequest");
                    DailyOhlcRequest dataDailyOhlcRequest = g.fromJson(dailyOhlcRequest.toJSONString(),DailyOhlcRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyOhlc(dataDailyOhlcRequest)));
                    break;
                case "6":
                    JSONObject intradayOhlcRequest = (JSONObject)jsonObject.get("IntradayOhlcRequest");
                    IntradayOhlcRequest dataIntradayOhlcRequest = g.fromJson(intradayOhlcRequest.toJSONString(),IntradayOhlcRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetIntradayOhlc(dataIntradayOhlcRequest)));
                    break;
                case "7":
                    JSONObject dailyIndexRequest = (JSONObject)jsonObject.get("DailyIndexRequest");
                    DailyIndexRequest dataDailyIndexRequest = g.fromJson(dailyIndexRequest.toJSONString(),DailyIndexRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyIndex(dataDailyIndexRequest)));
                    break;
                case "8":
                    JSONObject dailyStockPriceRequest = (JSONObject)jsonObject.get("DailyStockPriceRequest");
                    DailyStockPriceRequest dataDailyStockPriceRequest = g.fromJson(dailyStockPriceRequest.toJSONString(),DailyStockPriceRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDailyStockPrice(dataDailyStockPriceRequest)));
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCDataStreaming() throws Exception {
        JSONParser parser = new JSONParser();
        //Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fcdata.json"));
        Object obj = parser.parse(new FileReader("C:\\Users\\hoaht\\Desktop\\fcdata.json"));
        JSONObject jsonObject = (JSONObject)obj;
        String consumerId = (String)jsonObject.get("consumerId");
        String consumerSecret = (String)jsonObject.get("consumerSecret");
        String url = (String)jsonObject.get("url");
        String urlStreamString = (String)jsonObject.get("streaming_url");
        String channel = (String)jsonObject.get("channels");
        FCDataClient client = new FCDataClient(consumerId, consumerSecret, url);
        client.init();
        FCDataStreaming streaming = new FCDataStreaming(client, urlStreamString);
        streaming.setChannel(channel);
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