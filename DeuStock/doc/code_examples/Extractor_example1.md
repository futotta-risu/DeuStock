### Extractor

The extractor class encapsulates the sentiment analyzer process for the developer to have a easy and accessible way to use those functions. 

```java
class ExtractorUseExample {
    public void exampleExtractorUser() {
        Extractor extractor = new Extractor();
        try {
            extractor.setGateway(Twitter);
            extractor.setSearchQuery("\"law\"");
            System.out.println("Average sentiment: " + extractor.getSentimentTendency());
        } catch (NoGatewayTypeException e) {
            e.printStackTrace();
        }
    }
}
```