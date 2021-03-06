package com.example.musicapp.wave;

public interface OrganCoefficients {
    //32 начальных фаз в градусах из таблицы
    double[] initialPhaseCoefficients = {
            -50.96, -12.00, 39.50, 66.04, -76.13, -26.40, 2.58, -137.87,
            5.72, 148.95, 48.69, 34.13, -122.53, -178.98, 124.63, -5.68,
            33.62, -107.65, -60.17, -20.35, 9.84, 60.86, -179.05, -30.19,
            -12.82, 38.51, -116.43, 109.97, -36.97, -74.79, -2.64, -64,82};

    //32 значение амплитуды для гармоник
    int[] amplitudeRatios = {
            100, 230, 1, 585, 1, 1, 2, 156,
            1, 1, 869, 1, 1, 1, 1, 651,
            2, 1, 1, 1, 1, 1, 1, 1,
            765, 1, 2, 3, 7, 1, 1, 1};
}
