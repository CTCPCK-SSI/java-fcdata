package vn.com.ssi.Trading;
import com.google.gson.JsonElement;
import  microsoft.aspnet.signalr.client.hubs.*;
import  microsoft.aspnet.signalr.client.*;
import  microsoft.aspnet.signalr.client.http.*;
import microsoft.aspnet.signalr.client.transport.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FCTradingStreaming {
    private FCTradingClient _client;
    private  String _url;
    HubConnection _hubConnection;
    HubProxy _hubProxy;
    Subscription _subscription;
    private  MessageReceivedHandler mOnReceived;
    public FCTradingStreaming(FCTradingClient client, String url) throws Exception {
        _client = client;
        if(url.endsWith("/"))
            _url = url.substring(0,url.length() -1) + "/v2.0";
        else
            _url = url + "/v2.0";
        String access_token = _client.GetAccessToken();
        String queryString = "access_token="+access_token;
        _hubConnection = new HubConnection(_url, queryString, true, new NullLogger());
        Credentials credentials = new Credentials() {
            @Override
            public void prepareRequest(Request request) {
                request.addHeader("Authorization", "Bearer " + access_token);
            }
        };
        _hubConnection.setCredentials(credentials);
        _hubProxy =_hubConnection.createHubProxy(API.HUB_NAME);
        _subscription =  _hubProxy.subscribe(API.HUB_EVENT);
        _subscription.addReceivedHandler(new Action<JsonElement[]>() {
            @Override
            public void run(JsonElement[] obj) throws Exception {
                if(mOnReceived != null)
                    for (JsonElement jsonElement: obj
                    ) {
                        mOnReceived.onMessageReceived(jsonElement);
                    }
            }
        });
    }
    public void onError(ErrorCallback callback){
        _hubConnection.error(callback);
    }
    public void onConnected(Runnable callback){
        _hubConnection.connected(callback);
    }
    public void onClosed(Runnable callback){
        _hubConnection.closed(callback);
    }
    public void onStateChanged(StateChangedCallback handler){
        _hubConnection.stateChanged(handler);
    }
    public void onReconnecting(Runnable handler) {
        _hubConnection.reconnecting(handler);
    }

    public void onReconnected(Runnable handler) {
        _hubConnection.reconnected( handler);
    }
    public void onReceived(MessageReceivedHandler handler) {
       mOnReceived = handler;
    }
    public  void start() throws Exception {
       _hubConnection.start(new WebsocketTransport(new NullLogger()));
    }
    public static void main(String[] args) throws Exception {

                FCTradingClient client = new FCTradingClient("2c7c511d4eb940d6a8b09aaef5711654", "bce8d1b486e94a99b96ba7e9f6169fbd"
                , "PFJTQUtleVZhbHVlPjxNb2R1bHVzPnNhZDhnM0tGZVhrK1BBOUdlUERkdVUwUnY5ZXpsTGpSRjExdjV0UEFMZk8zYnRtbmlucmJWdmxuU3FPTGluRkpPNVhwbGtzMy80dE01WGxVeTM2dndzYlBleld4UVE1dThodEo0bGZGL2F6NkdGZVZsZWFBR0RmRnZJWVUyS2dNRHA1bGZwVkNkdE5HczRCVVNtd0JlWWVXSWdBMEVjbFgrMjFKalR5Z2Q2VT08L01vZHVsdXM+PEV4cG9uZW50PkFRQUI8L0V4cG9uZW50PjxQPjBDREcwU3RyOUpnNFBIQnBiUEtialFWcjlvUXlyVm9nU2dscHZQZUh2bmx5TCtHOFBEK3hKcnBJMndmZ3RLa3o4c3lpM0NSd3lkUDJzYjY2eUZQa2Z3PT08L1A+PFE+Mm9SUmp6a3FrL2RTNDg2REpTNjRpTmpyMEZhVElVK0Y5OCsrZUJCeENnYW5lamoraDRVbk4rWXB2bWZzdFdIUVNDd01wZXByd1E2bWhQSnhMVWlCMnc9PTwvUT48RFA+clhMelhqUjZ0by81SmQxazd1Zk0zSnl0R2ZlWUtFSVk4THFoaFZzZ3BJdWZydW5JTHNuQndBMjFVOXhmMXcrLy9GT3dVaGlJYXBzY1Y0c2xMSGhGenc9PTwvRFA+PERRPjBIaUVSeHZHM3p1SnZRUjhZYkRkSk5HdXlDaVFYYXM4cUZ0dDM2WHY4aHkvRXYvazlPMjNxTURRK25LemhhZzN5V01jL2YxVHArK09OakFHZ2FrM0dRPT08L0RRPjxJbnZlcnNlUT5EN0VSd2Y3RWxoR2t2NjBNRFM5Z2VtNXhpOXJuUktZeGJ5TzROMWVNS0tqMmNoM1hqc3g3Z3dOcGp3K3hJRTB0dkZhN09hb085N0daVE5icUhDR3NUUT09PC9JbnZlcnNlUT48RD5xeSszOUxYeTFnVzFxWXdTblZHRVpoMVVvQ2JLM2VGbFlmMWdhZTNiZnF3ZE1zeTYrOTY4NHNjNitCbzF5VGEybVpzd1ZlbXZVU2c2OWRoL2xBTkVlbkdZaWxuOUlVZ1U2SmU1alBCaFppVzgwVWJsTE1YN2x4NmhWSjN4S1B5eFVWM2hlV1d4aFhIa0RpQXZSaUlSQ2lERWl1SHhHcWhQMG5RTFM1bHgydlU9PC9EPjwvUlNBS2V5VmFsdWU+"
                , "8aTQgtE5A1X5vK"
                , "https://fc-tradeapi.ssi.com.vn"
                , true
        );
        FCTradingStreaming streaming = new FCTradingStreaming(client, "https://fc-tradehub.ssi.com.vn/");
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
