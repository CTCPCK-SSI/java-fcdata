# fastconnect-client

## Install

Import the SignalR Client to add dependency management for FastConnect Client artifacts to your project:

```xml
<dependency>
    <groupId>vn.com.ssi</groupId>
    <artifactId>signalr-client</artifactId>
    <version>2.0.0</version>
</dependency>
```

Add two dependency on the main FastConnect artifact:

```xml
<dependencies>
    <dependency>
        <groupId>vn.com.ssi</groupId>
        <artifactId>fctrading-client</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>vn.com.ssi</groupId>
        <artifactId>fcdata-client</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```
## Getting started

### FCDataClient
```java
import vn.com.ssi.Data.DataContract.*;
import vn.com.ssi.Data.FCDataClient;
FCDataClient client = new FCDataClient(config.get("consumerId").getAsString(), config.get("consumerSecret").getAsString(), config.get("url").getAsString());
SecuritiesRequest rq1 = new SecuritiesRequest();
rq1.market = "HOSE";
System.out.println("Securities =" + new ObjectMapper().writeValueAsString(client.GetSecurities(rq1)));
```
### FCDataStreaming
```java
import vn.com.ssi.Data.DataContract.*;
import vn.com.ssi.Data.FCDataClient;
import vn.com.ssi.Data.FCDataStreaming;
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
```

### FCTradingClient
```java
import vn.com.ssi.Trading.DataContract.*;
import vn.com.ssi.Trading.FCTradingClient;
FCTradingClient client = new FCTradingClient(config.get("consumerId").getAsString()
                , config.get("consumerSecret").getAsString()
                , config.get("privateKey").getAsString()
                , config.get("code").getAsString()
                , config.get("url").getAsString());
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
```

### FCTradingStreaming
```java
import vn.com.ssi.Trading.DataContract.*;
import vn.com.ssi.Trading.FCTradingClient;
import vn.com.ssi.Trading.FCTradingStreaming;
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
```
## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
