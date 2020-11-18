package com.example.musicapp.database;

public class WaveDb {
    public static final class WaveTable{
        public static final String NAME = "waves";

        public static final class Columns{
            public static final String TYPE ="type";
            public static final String FREQUENCY ="frequency";
            public static final String HARMONICS_NUMBER="harmonics_number";
            //public static final String CHANNELS_NUMBER="channels_number";
        }
    }
}
