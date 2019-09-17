package com.ellucian.app;

import com.ellucian.app.models.Building;
import com.ellucian.app.models.Config;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import okhttp3.*;

public class Sample {

    final private Gson gson = new Gson();
    private Config config;
    private String requestKey;
    private List<Building> buildings;


    public static void main(String[] args) throws Exception {
        final Sample sample = new Sample();
        System.out.println("Starting");

        sample.loadConfig(args[0]);
        sample.ethosAuth();
        sample.retrieveBuildings();
        sample.saveBuildingsToCsv();

        System.out.println("Finished");
    }

    /**
     * Load the configuration file that will hold the key.
     */
    private void loadConfig(final String configFile) throws IOException {
        System.out.println("Loading: " + configFile);
        try (Stream<String> lines = Files.lines(Paths.get(configFile))) {
            final String data = lines.collect(Collectors.joining("\n"));
            config = gson.fromJson(data, Config.class);
        }
    }

    /**
     * Get the ethos api key.
     */
    private void ethosAuth() throws IOException {
        System.out.println("Getting the ethos api key.");
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final MediaType type = MediaType.parse("application/json; charset=utf-8");
        final RequestBody body = RequestBody.create(type, "");
        final Request request = new Request.Builder()
                .url("https://integrate.elluciancloud.com/auth")
                .addHeader("authorization", "Bearer " + config.ethosApiKey)
                .addHeader("cache-control", "no-cache")
                .post(body)
                .build();
        final Response response = client.newCall(request).execute();
        requestKey = response.body().string();
    }

    /**
     * Get all the buildings.
     */
    private void retrieveBuildings() throws IOException {
        System.out.println("Getting all the buildings.");
        String endpoint = "https://integrate.elluciancloud.com/api/buildings";
        System.out.println(endpoint);
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
                .url(endpoint)
                .url("https://integrate.elluciancloud.com/api/buildings")
                .header("accept", "application/json")
                .addHeader("accept-charset", "UTF-8")
                .addHeader("authorization", "Bearer " + requestKey)
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/vnd.hedtech.integration.v6+json")
                .get()
                .build();

        final Response response = client.newCall(request).execute();
        final String buildingJson = response.body().string();
        final Gson gson = new Gson();
        final Type type = TypeToken.getParameterized(List.class, Building.class).getType();
        buildings = gson.fromJson(buildingJson, type);
    }

    /**
     * Save the buildings to a csv file.
     */
    private void saveBuildingsToCsv() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        // Filter buildings so only buildings that start with 'a' are outputted.
        final List<Building> aBuildings = new ArrayList<>();
        for (final Building building : buildings) {
            if(building.getTitle().toLowerCase().charAt(0) == 'a') {
                aBuildings.add(building);
            }
        }

        System.out.println("Saving the buildings to a csv file.");
        try (Writer writer = new FileWriter(config.csvFileLocation)) {
            StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(aBuildings);
        }
    }
}
