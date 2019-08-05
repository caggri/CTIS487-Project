package com.cagribayram.denizhancetin.project;

public final class TemperatureArchive {

    private TemperatureArchive() {

    }

    public static class Temperature {
        public static final String TABLE_NAME = "temperature";
        public static final String ID = "record_id";
        public static final String TIMESTAMP = "time";
        public static final String TEMP = "temp";

    }
}
