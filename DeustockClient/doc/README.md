## How to add client petitions

```
String url = "http://localhost:8080"

Client client = ClientBuilder.newClient();
WebTarget target = client.target(URL);

Response response = target.path("myapp")
    .path("myresource")
    .request(MediaType.TEXT_PLAIN_TYPE)
    .get();
    
String status = response.getStatus();
String sResponse = response.readEntity(String.class);

System.out.println("Response: " + status + " - " + sResponse);
```