package com.ellucian.app;

public class Sample {

    public static void main(String[] args) {
        final Sample sample = new Sample();
        System.out.println("Started");

        sample.loadConfig(args[0]);
        sample.ethosAuth();
        sample.retrieveBuildings();
        sample.saveBuildingsToCsv();

        System.out.println("Finish");
    }

    /**
     * Load the configuration file that will hold the key.
     */
    private void loadConfig(final String configFile) {
        System.out.println("Loading config file: " + configFile);
    }

    /**
     * Get the ethos api key.
     */
    private void ethosAuth() {
        System.out.println("Getting the ethos api key.");
    }

    /**
     * Get all the buildings.
     */
    private void retrieveBuildings() {
        System.out.println("Getting all the buildings.");
    }

    /**
     * Save the buildings to a csv file.
     */
    private void saveBuildingsToCsv() {
        System.out.println("Saving the buildings to a csv file.");
    }
}
