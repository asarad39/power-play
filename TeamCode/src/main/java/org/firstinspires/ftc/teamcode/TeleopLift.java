package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class TeleopLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    public TeleopLift(RobotHardware rh) {

        this.rh = rh;
//        this.telemetry = rh.getTelemetry();
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.75;

        double liftMove = getLiftPowerDebug(maxLiftSpeed);

        //TODO: Add touch sensor back in, Anish removed it on 11/14
        //TODO: add in drive motors

//        // stop points
//        if (rh.getLiftLowerBound() == true) {
//            liftMove = 0;
//        }

        // move arm up and down (servo)
        double liftArmPosition = arm();

        // claw open and shut (servo)
        double liftClawPosition = claw();

        rh.lift(liftMove, liftArmPosition, liftClawPosition);

        rh.telemetry.addData("Lift Clicks 1", rh.getLiftEncoder1());
        rh.telemetry.addData("Lift Clicks 2", rh.getLiftEncoder2());

        rh.telemetry.addData("Arm Servo Pos", rh.getLiftClawEncoder());
        rh.telemetry.addData("Claw Servo Pos", rh.getLiftArmEncoder());
    }

    public double getLiftPowerDebug(double maxLiftSpeed) {
        double liftMove;

        if (rh.gamepad1.dpad_up == true) {
            liftMove = + maxLiftSpeed;
        } else if (rh.gamepad1.dpad_down == true) {
            liftMove = - maxLiftSpeed;
        } else {
            liftMove = 0;
        }
        return liftMove;
    }

    public double claw() {
        double liftClawPosition;
        double minClawPosition = 0;
        double maxClawPosition = 1;
        if (rh.gamepad1.left_bumper == true) {
            liftClawPosition = maxClawPosition;
        } else {
            liftClawPosition = minClawPosition;
        }
        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition;
        double minArmPosition = 0;
        double maxArmPosition = 1;
        if (rh.gamepad1.right_bumper == true) {
            liftArmPosition = maxArmPosition;
        } else {
            liftArmPosition = minArmPosition;
        }
        return liftArmPosition;
    }
}
