package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID(2900);

    private double lastClaw = 0;
    private double lastArm = 0;
    private boolean goHome;

    public TeleopLift(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {
        rh.liftServos(0, 1);
        goHome = true;
    }

    @Override
    public boolean getIsDone() {
        return false;
    }

    public void update() {

        // move arm up and down (servo)
        double liftArmPosition = arm();

        // claw open and shut (servo)
        double liftClawPosition = claw();

        // set lift movement speed
        double maxLiftSpeed = 0.8;

        double liftSpeed = getLiftPowerPID(maxLiftSpeed);
        adjustLiftHeight();
        rh.liftTarget(liftPID.getTargetPosition());

        double liftMove = getLiftPowerLgstcCrv(liftSpeed, liftPID.getTargetPosition());

        if (goHome == true) {

            rh.lift(0.3);
            liftPID.setTargetPosition(-10000);

            if (rh.getTouch() == true) {

                rh.resetLiftEncoders(); // TODO: the problem!
                goHome = false;
            }

        } else {

            rh.lift(liftMove);
        }


        rh.liftServos(liftArmPosition, liftClawPosition);

        rh.telemetry.addData("Red", rh.getRed());
        rh.telemetry.addData("Blue", rh.getBlue());
        rh.telemetry.addData("Green", rh.getRed());
        rh.telemetry.addData("Distance", rh.getDistance());

        rh.telemetry.addLine();

        rh.telemetry.addData("goHome", goHome);
        rh.telemetry.addData("liftMove", liftMove);
        rh.telemetry.addData("Lift Target Position", liftPID.getTargetPosition());

        rh.telemetry.addLine();

        rh.telemetry.addData("Lift Clicks 1", rh.getLiftEncoder1());
        rh.telemetry.addData("Lift Clicks 2", rh.getLiftEncoder2());

        rh.telemetry.addLine();

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

    public void adjustLiftHeight() {
        double adjustmentSize = 20;

        if (rh.gamepad1.dpad_up == true) {
            liftPID.adjustTargetPosition( adjustmentSize );
        } else if (rh.gamepad1.dpad_down == true) {
            liftPID.adjustTargetPosition( -adjustmentSize );
        }
    }

    public double getLiftPowerLgstcCrv(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoder1(), targetPosition, .01);
    }

    public double getLiftPowerPID(double maxPower) {
        double home = 0;
//        double ground = 180;
        double low = 500;
        double middle = 1700;
        double high = 2900;

        if (rh.gamepad1.a == true) {
            liftPID.setTargetPosition(home);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.b == true) {
            liftPID.setTargetPosition(low);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.y == true) {
            liftPID.setTargetPosition(middle);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (rh.gamepad1.x == true) {
            liftPID.setTargetPosition(high);
            liftPID.setStartTime(rh.time.milliseconds());
        }

        if (goHome == false) { // so the lift can move down while homing
            liftPID.checkForInvalid();
        }

        return getLiftPowerLgstcCrv(maxPower, liftPID.getTargetPosition());
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
                liftArmPosition = (liftArmPosition + 1) % 2;
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
