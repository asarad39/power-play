package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoNewClaw implements State {

    RobotHardware rh = null;

    private double lastClaw = 0;
    private double lastArm = 0;
    private double startTime;
    private String clawString = null; // "open" or "closed"
    private String armString = null; // "up" or "down"

    public AutoNewClaw(RobotHardware rh, String clawString, String armString) {
        this.rh = rh;
        this.clawString = clawString;
        this.armString = armString;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {
        return (rh.time.seconds() > 3 + startTime);
    }

    public void update() {

//        rh.liftServos(arm(), claw());

    }

    public double claw() {

        double liftClawPosition = rh.getClawPos();

        if (clawString.equals("open")) {
            liftClawPosition = 0;
        } else if (clawString.equals("closed")) {
            liftClawPosition = 1;
        } else {
            throw new IllegalArgumentException("nonexistent claw position name");
        }

        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition = rh.getClawPos();

        if (armString.equals("up")) {
            liftArmPosition = 1;
        } else if (armString.equals("down")) {
            liftArmPosition = 0;
        } else {
            throw new IllegalArgumentException("nonexistent arm position name");
        }

        return liftArmPosition;
    }

}