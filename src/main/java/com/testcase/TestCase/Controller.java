package com.testcase.TestCase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcase.TestCase.model.CityResponse;
import com.testcase.TestCase.model.ConsumptionRequestBody;
import com.testcase.TestCase.model.ResponseMessage;
import com.testcase.TestCase.model.WeatherResponse;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class Controller {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAllCities")
    public CityResponse getAllCities() {
        List<String> cities = new ArrayList<>();
        Connection connection = null;
        String dbURL = "jdbc:mysql://localhost:3306/testcase";
        String user = "root";
        String password = "enis";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC sürücüsü yüklendi!");

            connection = DriverManager.getConnection(dbURL, user, password);
            String query = "SELECT * FROM city";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String cityName = resultSet.getString("name");
                cities.add(cityName);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC sürücüsü bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Bağlantı kapatıldı.");
                } catch (SQLException e) {
                    System.out.println("Bağlantı kapatılırken hata: " + e.getMessage());
                }
            }
        }

        return new CityResponse(cities);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getWeather")
    public WeatherResponse getWeather(@RequestParam String city) {
        WeatherResponse weatherResponse = new WeatherResponse();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.collectapi.com/weather/getWeather?data.lang=tr&data.city=" + city))
                .header("content-type", "application/json")
                .header("authorization", "apikey 0LnOrFr60jheHdBefLt0Qb:285CeCvraaWjqG8IXMV2Kz") // Gerçek token ile değiştirin
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);
            ObjectMapper objectMapper = new ObjectMapper();

            weatherResponse = objectMapper.readValue(responseBody, WeatherResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherResponse;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addConsumption")
    public ResponseMessage addConsumption(@RequestBody ConsumptionRequestBody consumptionBody) {
        ResponseMessage responseMessage = new ResponseMessage();

        Connection connection = null;
        String dbURL = "jdbc:mysql://localhost:3306/testcase";
        String user = "root";
        String password = "enis";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC sürücüsü yüklendi!");
            PreparedStatement preparedStatement = null;

            connection = DriverManager.getConnection(dbURL, user, password);
            String query = "INSERT INTO consumption (cityid, temperature, averageconsumption) VALUES (?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, consumptionBody.getCityId());
            preparedStatement.setString(2, consumptionBody.getTemperature());
            preparedStatement.setString(3, consumptionBody.getAverageConsumption());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                responseMessage.setStatus("Success");
                responseMessage.setMessage("Added Successfully");
            } else {
                responseMessage.setStatus("Failed");
                responseMessage.setMessage("Failed to add Consumption");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC sürücüsü bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Bağlantı kapatıldı.");
                } catch (SQLException e) {
                    System.out.println("Bağlantı kapatılırken hata: " + e.getMessage());
                }
            }
        }

        return responseMessage;
    }

}


