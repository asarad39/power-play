package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class TeleopLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;

    private double lastClaw = 0;
    private double lastArm = 0;

    public TeleopLift(RobotHardware rh) {

        this.rh = rh;
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.75;

        double liftMove = getLiftPowerDebug(maxLiftSpeed);

        //TODO: Add touch sensor back in, Anish removed it on 11/14

//        // stop points
//        if (rh.getTouch() == true) {
//            liftMove = 0;
//        }

        // move arm up and down (servo)
        double liftArmPosition = arm();

        // claw open and shut (servo)
        double liftClawPosition = claw();

        rh.lift(liftMove);
        rh.liftServos(liftArmPosition, liftClawPosition);

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
            liftMove = -0.5 * maxLiftSpeed;
        } else {
            liftMove = 0;
        }
        return liftMove;
    }

    public double getLiftPowerLgstcCrv(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoder1(), targetPosition, .005);
    }

    public double getLiftPowerPID(double targetPosition, double startTime) {
        return Mathematics.PIDcontrol(rh.getLiftEncoder1(), targetPosition, rh.time.seconds(), startTime);
    }

    public double claw() {

        double liftClawPosition = rh.getLiftClawEncoder();

        if (rh.gamepad1.left_bumper && lastClaw == liftClawPosition) {
            liftClawPosition = (liftClawPosition + 1) % 2;
        } else if (!rh.gamepad1.left_bumper) {
            lastClaw = rh.getLiftClawEncoder();
        }

        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition = rh.getLiftArmEncoder();

        if (rh.gamepad1.left_bumper && lastClaw == liftArmPosition) {
            liftArmPosition = (liftArmPosition + 1) % 2;
        } else if (!rh.gamepad1.left_bumper) {
            lastClaw = rh.getLiftClawEncoder();
        }

        return liftArmPosition;
    }
}
