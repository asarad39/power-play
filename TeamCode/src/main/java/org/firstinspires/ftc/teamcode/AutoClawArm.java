package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class AutoClawArm implements State {

    RobotHardware rh = null;
    Telemetry telemetry;

    private double lastClaw = 0;
    private double lastArm = 0;
    private boolean isDone = false;
    private String clawString = null; // "open" or "closed"
    private String armString = null; // "up" or "down"

    public AutoClawArm(RobotHardware rh, String clawString, String armString) {
        this.rh = rh;
        this.clawString = clawString;
        this.armString = armString;
    }

    public void init() {

    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }

    public void update() {

        rh.liftServos(arm(), claw());
        isDone = true;
    }

    public double claw() {

        double liftClawPosition = rh.getLiftClawEncoder();

        if (clawString.equals("open")) {
            liftClawPosition = 1;
        } else if (clawString.equals("closed")) {
            liftClawPosition = 0;
        }

        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition = rh.getLiftClawEncoder();

        if (armString.equals("up")) {
            liftArmPosition = 0;
        } else if (armString.equals("down")) {
            liftArmPosition = 1;
        }

        return liftArmPosition;
    }

}