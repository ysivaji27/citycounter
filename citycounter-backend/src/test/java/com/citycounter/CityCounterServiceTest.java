package com.citycounter;

import com.citycounter.controller.CityCounterController;
import com.citycounter.exceptionhandler.ServiceUnavailableException;
import com.citycounter.exceptionhandler.WeatherApiException;
import com.citycounter.httpclient.OkHttpClientService;
import com.citycounter.service.impl.CityCounterServiceImpl;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class CityCounterServiceTest {

    @Autowired
    private CityCounterServiceImpl cityCounterService;

    @Autowired
    private  OkHttpClientService okHttpClientService;

    @Autowired
    private CityCounterController cityCounterController;

    @Mock // Creates a mock instance of Call.Factory (which OkHttpClient implements)
    private OkHttpClient httpClient; // Mock OkHttpClient to control its behavior

    @Mock // Creates a mock instance of Call.Factory (which OkHttpClient implements)
    private Request  request; // Mock OkHttpClient to control its behavior

    @Mock // Mocks the Call object returned by OkHttpClient.newCall()
    private Call mockCall;

    // Sample JSON responses for different scenarios
    private final String MOCK_SUCCESS_JSON ="{\"cod\":\"200\",\"calctime\":0.3107,\"cnt\":15,\"list\":[{\"id\":2208791,\"name\":\"Yafran\",\"coord\":{\"lon\":12.52859,\"lat\":32.06329},\"main\":{\"temp\":9.68,\"temp_min\":9.681,\"temp_max\":9.681,\"pressure\":961.02,\"sea_level\":1036.82,\"grnd_level\":961.02,\"humidity\":85},\"dt\":1485784982,\"wind\":{\"speed\":3.96,\"deg\":356.5},\"rain\":{\"3h\":0.255},\"clouds\":{\"all\":88},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}]},{\"id\":2208425,\"name\":\"Zuwarah\",\"coord\":{\"lon\":12.08199,\"lat\":32.931198},\"main\":{\"temp\":15.36,\"temp_min\":15.356,\"temp_max\":15.356,\"pressure\":1036.81,\"sea_level\":1037.79,\"grnd_level\":1036.81,\"humidity\":89},\"dt\":1485784982,\"wind\":{\"speed\":5.46,\"deg\":30.0002},\"clouds\":{\"all\":56},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}]},{\"id\":2212771,\"name\":\"Sabratah\",\"coord\":{\"lon\":12.48845,\"lat\":32.79335},\"main\":{\"temp\":15.31,\"temp_min\":15.306,\"temp_max\":15.306,\"pressure\":1037.05,\"sea_level\":1037.55,\"grnd_level\":1037.05,\"humidity\":100},\"dt\":1485784982,\"wind\":{\"speed\":6.71,\"deg\":28.5002},\"clouds\":{\"all\":92},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}]},{\"id\":2217362,\"name\":\"Gharyan\",\"coord\":{\"lon\":13.02028,\"lat\":32.172218},\"main\":{\"temp\":11.23,\"temp_min\":11.231,\"temp_max\":11.231,\"pressure\":1004.23,\"sea_level\":1037.06,\"grnd_level\":1004.23,\"humidity\":90},\"dt\":1485784982,\"wind\":{\"speed\":3.86,\"deg\":16.5002},\"rain\":{\"3h\":1.29},\"clouds\":{\"all\":92},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}]},{\"id\":2216885,\"name\":\"Zawiya\",\"coord\":{\"lon\":12.72778,\"lat\":32.75222},\"main\":{\"temp\":17,\"pressure\":1024,\"humidity\":55,\"temp_min\":17,\"temp_max\":17},\"dt\":1485784982,\"wind\":{\"speed\":3.6,\"deg\":10},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2210247,\"name\":\"Tripoli\",\"coord\":{\"lon\":13.18746,\"lat\":32.875191},\"main\":{\"temp\":16,\"pressure\":1025,\"humidity\":59,\"temp_min\":16,\"temp_max\":16},\"dt\":1485781822,\"wind\":{\"speed\":3.6,\"deg\":360},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2210221,\"name\":\"Tarhuna\",\"coord\":{\"lon\":13.6332,\"lat\":32.43502},\"main\":{\"temp\":17,\"pressure\":1024,\"humidity\":55,\"temp_min\":17,\"temp_max\":17},\"dt\":1485784982,\"wind\":{\"speed\":3.6,\"deg\":10},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2215163,\"name\":\"Masallatah\",\"coord\":{\"lon\":14,\"lat\":32.616669},\"main\":{\"temp\":12.86,\"temp_min\":12.856,\"temp_max\":12.856,\"pressure\":1000.17,\"sea_level\":1036.49,\"grnd_level\":1000.17,\"humidity\":73},\"dt\":1485784982,\"wind\":{\"speed\":4.81,\"deg\":16.5002},\"rain\":{\"3h\":0.39},\"clouds\":{\"all\":88},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}]},{\"id\":2219905,\"name\":\"Al Khums\",\"coord\":{\"lon\":14.26191,\"lat\":32.648609},\"main\":{\"temp\":15.18,\"temp_min\":15.181,\"temp_max\":15.181,\"pressure\":1023.35,\"sea_level\":1036.01,\"grnd_level\":1023.35,\"humidity\":73},\"dt\":1485784982,\"wind\":{\"speed\":5.26,\"deg\":26.0002},\"clouds\":{\"all\":88},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}]},{\"id\":2208485,\"name\":\"Zlitan\",\"coord\":{\"lon\":14.56874,\"lat\":32.467381},\"main\":{\"temp\":15.18,\"temp_min\":15.181,\"temp_max\":15.181,\"pressure\":1023.35,\"sea_level\":1036.01,\"grnd_level\":1023.35,\"humidity\":73},\"dt\":1485784982,\"wind\":{\"speed\":5.26,\"deg\":26.0002},\"clouds\":{\"all\":88},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}]},{\"id\":2563191,\"name\":\"Birkirkara\",\"coord\":{\"lon\":14.46111,\"lat\":35.897221},\"main\":{\"temp\":14,\"pressure\":1023,\"humidity\":62,\"temp_min\":14,\"temp_max\":14},\"dt\":1485784991,\"wind\":{\"speed\":4.1,\"deg\":10,\"var_beg\":330,\"var_end\":30},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2523650,\"name\":\"Ragusa\",\"coord\":{\"lon\":14.71719,\"lat\":36.928242},\"main\":{\"temp\":14.54,\"pressure\":1022,\"humidity\":50,\"temp_min\":13,\"temp_max\":16},\"dt\":1485781816,\"wind\":{\"speed\":3.1,\"deg\":20,\"var_beg\":350,\"var_end\":60},\"clouds\":{\"all\":20},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}]},{\"id\":2523693,\"name\":\"Pozzallo\",\"coord\":{\"lon\":14.84989,\"lat\":36.730541},\"main\":{\"temp\":14,\"pressure\":1022,\"humidity\":50,\"temp_min\":14,\"temp_max\":14},\"dt\":1485781816,\"wind\":{\"speed\":3.1,\"deg\":20,\"var_beg\":350,\"var_end\":60},\"clouds\":{\"all\":20},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}]},{\"id\":2524119,\"name\":\"Modica\",\"coord\":{\"lon\":14.77399,\"lat\":36.84594},\"main\":{\"temp\":15.74,\"pressure\":1022,\"humidity\":47,\"temp_min\":14,\"temp_max\":17},\"dt\":1485785168,\"wind\":{\"speed\":2.1,\"deg\":0},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2523581,\"name\":\"Rosolini\",\"coord\":{\"lon\":14.94779,\"lat\":36.824242},\"main\":{\"temp\":15.62,\"pressure\":1022,\"humidity\":47,\"temp_min\":14,\"temp_max\":17},\"dt\":1485784979,\"wind\":{\"speed\":2.1,\"deg\":0},\"clouds\":{\"all\":40},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]}]}";
    private final String MOCK_ERROR_JSON = "{\"cod\":\"400\",\"message\":\"city not found\"}";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(okHttpClientService, "httpClient", httpClient);
        ReflectionTestUtils.setField(okHttpClientService, "openweathermapUrl", "http://test.api/weather");
        ReflectionTestUtils.setField(okHttpClientService, "requestTimeout", 10);
        ReflectionTestUtils.setField(okHttpClientService, "maxIdleConnections", 10);
        ReflectionTestUtils.setField(okHttpClientService, "keepAliveDuration", 5);
        ReflectionTestUtils.setField(okHttpClientService, "request", request);
        when(httpClient.newCall(any(Request.class))).thenReturn(mockCall);
    }

    @Test
    @DisplayName("for XX Should return 0 from WeatherResponse on successful 200 OK")
    void testCityCountByLetter_XX_success() throws IOException {
        // Configure mockCall to return a successful 200 OK response
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("http://test.api/weather/y").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MOCK_SUCCESS_JSON, okhttp3.MediaType.parse("application/json")))
                .build();

        when(mockCall.execute()).thenReturn(mockResponse);

        int  response = cityCounterService.getCityCountByLetter("XX");
        assertEquals(0, response);
    }

    @Test
    @DisplayName("for Y Should return 1 from WeatherResponse on successful 200 OK")
    void testCityCountByLetter_Y_success() throws IOException {
        // Configure mockCall to return a successful 200 OK response
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("http://test.api/weather/y").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MOCK_SUCCESS_JSON, okhttp3.MediaType.parse("application/json")))
                .build();

        when(mockCall.execute()).thenReturn(mockResponse);

        int  response = cityCounterService.getCityCountByLetter("Y");
        assertEquals(1, response);
    }


    @Test
    @DisplayName("for Z Should return 3 from  WeatherResponse on successful 200 OK")
    void testCityCountByLetter_Z_success() throws IOException {
        // Configure mockCall to return a successful 200 OK response
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("http://test.api/weather/y").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MOCK_SUCCESS_JSON, okhttp3.MediaType.parse("application/json")))
                .build();

        when(mockCall.execute()).thenReturn(mockResponse);

        int  response = cityCounterService.getCityCountByLetter("z");
        assertEquals(3, response);
    }



    @Test
    @DisplayName("Should throw BadRequestException for 400 Bad Request")
    void testCityCountByLetter_400BadRequest() throws IOException {
        // Arrange
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("http://test.api/weather/InvalidCity").build())
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .message("Bad Request")
                .body(ResponseBody.create(MOCK_ERROR_JSON, okhttp3.MediaType.parse("application/json")))
                .build();

        when(mockCall.execute()).thenReturn(mockResponse);


        // Act & Assert
        WeatherApiException thrown = assertThrows(WeatherApiException.class, () -> {
            cityCounterService.getCityCountByLetter("InvalidCity");
        });
        assertEquals("Client error from external weather API: " + MOCK_ERROR_JSON, thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    @DisplayName("Should throw ServiceUnavailableException for 500 Internal Server Error")
    void testCityCountByLetter_500InternalServerError() throws IOException {
        // Arrange
        Response mockResponse = new Response.Builder()
                .request(new Request.Builder().url("http://test.api/weather/CityWithError").build())
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message("Internal Server Error")
                .body(ResponseBody.create("{\"error\":\"Internal Server Error\"}", okhttp3.MediaType.parse("application/json")))
                .build();

        when(mockCall.execute()).thenReturn(mockResponse);

        // Act & Assert
        ServiceUnavailableException thrown = assertThrows(ServiceUnavailableException.class, () -> {
            cityCounterService.getCityCountByLetter("CityWithError");
        });
        assertEquals("Unexpected response from external weather API: ", thrown.getMessage());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, thrown.getHttpStatus());
    }

    @Test
    @DisplayName("Should throw ServiceUnavailableException for SocketTimeoutException")
    void testCityCountByLetter_timeoutException() throws IOException {
        // Arrange
        when(mockCall.execute()).thenThrow(new ServiceUnavailableException("Read timed out"));


        // Act & Assert
        ServiceUnavailableException thrown = assertThrows(ServiceUnavailableException.class, () -> {
            cityCounterService.getCityCountByLetter("SlowCity");
        });
        assertEquals("Read timed out", thrown.getMessage());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, thrown.getHttpStatus());
    }

    @Test
    @DisplayName("Should throw WeatherApiException for general IOException")
    void testCityCountByLetter_generalIOException() throws IOException {
        // Arrange
        when(mockCall.execute()).thenThrow(new IOException("Network unreachable"));

        // Act & Assert
        IOException thrown = assertThrows(IOException.class, () -> {
            cityCounterService.getCityCountByLetter("DisconnectedCity");
        });
        assertEquals("Network unreachable", thrown.getMessage());
    }

}