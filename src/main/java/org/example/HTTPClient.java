package org.example;

import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import  java.nio.charset.StandardCharsets;

public class HTTPClient {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/api/orders");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{"
                    + "\"customer\": {\"name\": \"Test Client\", \"phoneNumber\": \"1234567890\", \"email\": \"test@client.com\"},"
                    + "\"orderItems\": [{\"productId\": 1, \"quantity\": 1}, {\"productId\": 2, \"quantity\": 1}],"
                    + "\"deliveryDate\": \"2025-05-16\", "
                    + "\"deliveryZone\": \"near\""
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response.toString());
            }

            connection.disconnect();


            }catch (Exception e){
            e.printStackTrace();
        };
    }

}
