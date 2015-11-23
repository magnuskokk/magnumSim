package eu.kokk;

public interface Config {

    int fps = 30;

    double speedPercent = 100;
    int numPlanets = 50;

    int frameWidth = 1280;
    int frameHeight = 720;

    // Camera settings
    double cameraRotationAngle = 0.01;
    double cameraRollAngle = 0.0002;

    // Physics
    //double G = 6.674E-11; // This is the real value
    double G = 6.67E-5; // G*10000000
}
