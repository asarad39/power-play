package org.firstinspires.ftc.teamcode.archive;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoDriveTime implements State {

    RobotHardware rh = null;

    private double startTime;
    private String direction;
    private double seconds;
    private double power;

    public AutoDriveTime(RobotHardware rh, double seconds, String direction, double power) {
        this.rh = rh;
        this.direction = direction;
        this.seconds = seconds;
        this.power = power;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {
        if (rh.time.seconds() > seconds + startTime) {

            rh.drive(0, 0, 0, 0);
            return true;
        }

        return false;
    }

    public void update() {

        double moveX = 0;
        double moveY = 0;
        double moveRotate = 0;

        if (direction.equals("forward")) {
            moveY = power;
        } else if (direction.equals("backward")) {
            moveY = -power;
        } else if (direction.equals("right")) {
            moveX = power;
        } else if (direction.equals("left")) {
            moveX = -power;
        } else if (direction.equals("clockwise")) {
            moveRotate = power;
        } else if (direction.equals("counter")) {
            moveRotate = -power;
        } else {
            throw new IllegalArgumentException("nonexistent move direction");
        }

        double powerFR = + moveX - moveY + moveRotate;
        double powerFL = + moveX + moveY + moveRotate;
        double powerBR = - moveX - moveY + moveRotate;
        double powerBL = - moveX + moveY + moveRotate;

        rh.drive(powerFR, powerFL, powerBR, powerBL);

        rh.telemetry.addData("Direction", direction);
    }
}