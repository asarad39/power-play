package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class AutoDriveTime implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    double seconds;


    private double startTime;
    private String direction;
    private double time;

    public AutoDriveTime(RobotHardware rh, int seconds, String direction) {
        this.rh = rh;
        this.direction = direction;
        this.time = seconds;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {
        return (rh.time.seconds() > startTime + time);
    }

    public void update() {

        // set lift movement speed
        double speed = 0.5;
        double moveX = 0;
        double moveY = 0;
        double moveRotate = 0;
        if (direction.equals("forward")) {
            moveY = 1;
        } else if (direction.equals("backward")) {
            moveY = -1;
        } else if (direction.equals("right")) {
            moveX = 1;
        } else if (direction.equals("front")) {
            moveX = -1;
        } else {
            throw new IllegalArgumentException("nonexistent move direction");
        }

        double powerFR = + moveX + moveY + moveRotate;
        double powerFL = + moveX - moveY + moveRotate;
        double powerBR = - moveX + moveY + moveRotate;
        double powerBL = - moveX - moveY + moveRotate;

        rh.drive(powerFR, powerFL, powerBR, powerBL);

    }
}