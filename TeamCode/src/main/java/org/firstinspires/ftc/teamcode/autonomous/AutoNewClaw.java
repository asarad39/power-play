package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoNewClaw implements State {

    RobotHardware rh = null;

    private double startTime;
    private String clawString = null; // "open" or "closed"
    private String armString = null; // "up" or "down"
    private boolean init = false;

    public AutoNewClaw(RobotHardware rh, String clawString, String armString, boolean init) {
        this.rh = rh;
        this.clawString = clawString;
        this.armString = armString;
        this.init = init;
    }

    public void init() {
        startTime = rh.time.seconds();

        // close the claw with the arm down
        if (init) {
            rh.setServoPositions(1, 1, 0, 0.16);
        }
    }

    public boolean getIsDone() {
        return (rh.time.seconds() > 0.75 + startTime);
    }

    public void update() {

        rh.setServoPositions(arm(), 1, 0, claw());

    }

    public double claw() {

        double liftClawPosition = 0;

        if (clawString.equals("open")) {

            liftClawPosition = 0.0;

        } else if (clawString.equals("closed")) {

            liftClawPosition = 0.2;

        } else {
            throw new IllegalArgumentException("nonexistent claw position name");
        }

        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition = 0;

        if (armString.equals("up")) {
            liftArmPosition = 0.668;
        } else if (armString.equals("down")) {
            liftArmPosition = 1;
        } else {
            throw new IllegalArgumentException("nonexistent arm position name");
        }

        return liftArmPosition;
    }

}