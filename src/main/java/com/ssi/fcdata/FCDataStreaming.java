package com.ssi.fcdata;

import com.google.gson.JsonElement;
import microsoft.aspnet.signalr.client.*;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.Subscription;
import microsoft.aspnet.signalr.client.transport.WebsocketTransport;

import java.util.Map;
import java.util.Scanner;


public class FCDataStreaming {
    private FCDataClient _client;
    private String _url;
    HubConnection _hubConnection;
    HubProxy _hubProxy;
    Subscription _subscription;
    String _channel;
    private  Runnable mOnConnected;
    private  MessageReceivedHandler mOnReceived;

    public FCDataStreaming(FCDataClient client, String url) throws Exception {
        _client = client;
        if(url.endsWith("/"))
            _url = url.substring(0,url.length() -1) + "/v2.0";
        else
            _url = url + "/v2.0";
        String access_token = _client.GetAccessToken();
        String queryString = "access_token=" + access_token;
        _hubConnection = new HubConnection(_url, queryString, true, new Logger(){
            @Override
            public void log(java.lang.String arg0, microsoft.aspnet.signalr.client.LogLevel arg1){
                System.out.println();
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = super.getHeaders();
                headers.put("Authorization", "Bearer " + access_token);
                return headers;
            }
        };
        Credentials credentials = new Credentials() {

            @Override
            public void prepareRequest(Request request) {
                request.addHeader("Authorization", "Bearer " + access_token);
            }
        };
        _hubConnection.setCredentials(credentials);
        _hubProxy = _hubConnection.createHubProxy(API.HUB_NAME);
        _subscription = _hubProxy.subscribe(API.EVENT_NAME);
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
        _hubConnection.connected(new Runnable() {
            @Override
            public void run() {
                if(_channel!= null){
                    _hubProxy.invoke(API.SWITCH_CHANNEL_METHOD, _channel);
                }
                if(mOnConnected != null)
                    mOnConnected.run();
            }
        });
    }

    public void setChannel(String channel) {
        switchChannel(channel);
    }
    public void switchChannel(String channel) {
        assert channel !=null;
        _channel = channel;
        if(_hubConnection != null && _hubConnection.getState() == ConnectionState.Connected){
            if (_channel != null) {
                _hubProxy.invoke(API.SWITCH_CHANNEL_METHOD, _channel);
            }
        }
    }

    public void onError(ErrorCallback callback){
        _hubConnection.error(callback);
    }
    public void onConnected(Runnable callback){
        mOnConnected = callback;

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

    public void start() {
        // Start the connection
        _hubConnection.start(new WebsocketTransport(new NullLogger() ));
    }

    public static void main(String[] args) throws Exception {

        FCDataClient client = new FCDataClient("", ""
                , "https://fc-data.ssi.com.vn"
        );
        FCDataStreaming streaming = new FCDataStreaming(client, "https://fc-data.ssi.com.vn");
        streaming.setChannel("X-QUOTE:ALL,X-TRADE:ALL,F:ALL,R:ALL,MI:ALL");
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
