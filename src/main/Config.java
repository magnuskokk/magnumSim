package main;

public interface Config {

    int fps = 30;

    double slowMotionRatio = 1;
    int numPlanets = 200;

    int frameWidth = 1000;
    int frameHeight = 700;

    // Camera settings
    double cameraRotationAngle = 0.01;
    double cameraRollAngle = 0.001;
}
