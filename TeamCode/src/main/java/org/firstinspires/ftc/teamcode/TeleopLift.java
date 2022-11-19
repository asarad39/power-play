package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class TeleopLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID();

    private double lastClaw = 0;
    private double lastArm = 0;

    public TeleopLift(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {
        rh.liftServos(0, 1);
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.2;

        double liftMove = maxLiftSpeed;
        double target = getLiftPowerPID(1);


        //TODO: Add touch sensor back in, Anish removed it on 11/14

//        // stop points
//        if (rh.getTouch() == true) {
//            liftMove = 0;
//        }

        // move arm up and down (servo)
        double liftArmPosition = arm();

        // claw open and shut (servo)
        double liftClawPosition = claw();

        rh.liftTarget(target);
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

    public double getLiftPowerPID(double maxPower) {
        if (rh.gamepad1.a == true) {
            liftPID.setTargetPosition(0);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.b == true) {
            liftPID.setTargetPosition(900);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.y == true) {
            liftPID.setTargetPosition(1900);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.x == true) {
            liftPID.setTargetPosition(2500);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        double targetPosition = liftPID.getTargetPosition();
        double startTime = liftPID.getStartTime();

        return targetPosition;

        //return Mathematics.PIDcontrol(maxPower, 0.0000005, rh.getLiftEncoder1(), targetPosition, rh.time.seconds(), startTime);
    }

    public double claw() {

        double liftClawPosition = rh.getLiftClawEncoder();

        if (rh.gamepad1.left_bumper) {
            if (lastClaw == liftClawPosition) {
                liftClawPosition = (liftClawPosition + 1) % 2;
            }
        } else {
            lastClaw = rh.getLiftClawEncoder();
        }

        rh.telemetry.addData("liftClawPosition", liftClawPosition);
        rh.telemetry.addData("lastClaw", lastClaw);
        rh.telemetry.addData("clawEncoder", rh.getLiftClawEncoder());
        rh.telemetry.addLine();

        return liftClawPosition;
    }

    public double arm() {

        double liftArmPosition = rh.getLiftArmEncoder();

        if (rh.gamepad1.right_bumper) {
            if (lastArm == liftArmPosition) {
                liftArmPosition = (liftArmPosition + .5) % 1;
            }
        } else {
            lastArm = rh.getLiftArmEncoder();
        }

        rh.telemetry.addData("liftArmPosition", liftArmPosition);
        rh.telemetry.addData("lastArm", lastArm);
        rh.telemetry.addData("armEncoder", rh.getLiftArmEncoder());
        rh.telemetry.addLine();

        return liftArmPosition;
    }
}
