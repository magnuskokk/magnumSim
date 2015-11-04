package main;

public interface Config {

    int fps = 60;

    double slowMotionRatio = 1;
    int numPlanets = 50;

    int frameWidth = 1000;
    int frameHeight = 700;

    // Camera settings
    double cameraRotationAngle = 0.007;
    double cameraRollAngle = 0.0002;
}
