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
        final String access_token = _client.GetAccessToken();
        String queryString = "access_token=" + access_token;
        //String queryString = "token=" + access_token;
        _hubConnection = new HubConnection(_url, queryString, true, new Logger(){
            @Override
            public void log(java.lang.String arg0, microsoft.aspnet.signalr.client.LogLevel arg1){
                //System.out.println(arg0);
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
        //System.out.println("onError" + callback.toString());
    }
    public void onConnected(Runnable callback){
        mOnConnected = callback;
        //System.out.println("onConnected" + callback.toString());
    }
    public void onClosed(Runnable callback){
        _hubConnection.closed(callback);
        //System.out.println("onClosed" + callback.toString());
    }
    public void onStateChanged(StateChangedCallback handler){
        _hubConnection.stateChanged(handler);
        //System.out.println("onStateChanged" + handler.toString());
    }
    public void onReconnecting(Runnable handler) {
        _hubConnection.reconnecting(handler);
        //System.out.println("onReconnecting" + handler.toString());
    }

    public void onReconnected(Runnable handler) {
        _hubConnection.reconnected( handler);
        //System.out.println("onReconnected" + handler.toString());
    }
    public void onReceived(MessageReceivedHandler handler) {
        mOnReceived = handler;
        //System.out.println("onReceived" + handler.toString());
    }

    public void start() {
        // Start the connection
        _hubConnection.start(new WebsocketTransport(new NullLogger() ));
        //_hubConnection.start(new WebsocketTransport(_hubConnection.getLogger()));
        //_hubConnection.start(new LongPollingTransport(_hubConnection.getLogger()));
    }

    public static void main(String[] args) throws Exception {

        FCDataClient client = new FCDataClient("dd11c30e98494385a180f6ebae321272", "c8cb7e7441534e3aab8eb3cb1841a6ae"
                , "http://192.168.213.98:1189"
        );
        FCDataStreaming streaming = new FCDataStreaming(client, "http://192.168.213.98:1189/FastConnectHub");
        streaming.setChannel("X:ALL");
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
