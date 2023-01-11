package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopNewLiftSystem implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID(2900);

    private double lastClaw = 0;

    private boolean mirrored = false;
    private boolean lastMirrored = false;

    private String level = null;
    private String lastLevel = null;

    private boolean goHome;

    public TeleopNewLiftSystem(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {

        rh.setServoPositions(0, 1, 0, 0);
        goHome = true;
        level = "home";
        lastLevel = "home";
    }

    @Override
    public boolean getIsDone() {

        return false;
    }

    public void update() {

        goHome = rh.lift.getGoHome();

        double armPos = getArm();
        double flipPos = getFlip();
        double rotatePos = getRotate();
        double clawPos = getClaw();

        // set lift movement speed
        double maxLiftSpeed = 0.8;

        double liftSpeed = getLiftPowerPID(maxLiftSpeed);
        adjustLiftHeight();
        rh.setLiftTarget(liftPID.getTargetPosition());

        double liftMove = getLiftPowerLogistic(liftSpeed, liftPID.getTargetPosition());

        rh.lift(liftMove);

        level = getLevel();
        mirrored = getMirrored();
        rh.setServoPositions(armPos, flipPos, rotatePos, clawPos);

        rh.telemetry.addData("Level", level);
        rh.telemetry.addData("Last level", lastLevel);

        rh.telemetry.addData("Mirrored", mirrored);
        rh.telemetry.addData("Last mirrored", lastMirrored);

//        rh.telemetry.addData("Red", rh.getRed());
//        rh.telemetry.addData("Blue", rh.getBlue());
//        rh.telemetry.addData("Green", rh.getRed());
//        rh.telemetry.addData("Distance", rh.getDistance());

        rh.telemetry.addLine();

        rh.telemetry.addData("goHome", goHome);
        rh.telemetry.addData("liftMove", liftMove);
        rh.telemetry.addData("Lift Target Position", liftPID.getTargetPosition());

        rh.telemetry.addLine();

        rh.telemetry.addData("Lift Clicks Right", rh.getLiftEncoderLeft());
        rh.telemetry.addData("Lift Clicks Left", rh.getLiftEncoderRight());

        rh.telemetry.addLine();

        rh.telemetry.addData("Arm Servo Pos", rh.getArmPos());
        rh.telemetry.addData("Flip Servo Pos", rh.getFlipPos());
        rh.telemetry.addData("Rotate Servo Pos", rh.getRotatePos());
        rh.telemetry.addData("Claw Servo Pos", rh.getClawPos());
    }

    private String getLevel() {

        String newLevel = this.level;

        if (rh.gamepad1.dpad_up) { // toggle lift up

            if(lastLevel.equals("home")) {

                newLevel = "low";

            } else if(lastLevel.equals("low")) {

                newLevel = "middle";

            } else if(lastLevel.equals("middle")) {

                newLevel = "high";

            } else if(lastLevel.equals("high")) {

                newLevel = "home";

            }

        } else if (rh.gamepad1.dpad_down){

            lastLevel = level;
        }

        return newLevel;
    }

    public boolean getMirrored() {

        boolean newMirrored = this.mirrored;

        if (rh.gamepad1.right_bumper) {

            if (lastMirrored == newMirrored) {

                newMirrored = !newMirrored;
            }

        } else {

            lastMirrored = mirrored;
        }

        return newMirrored;
    }

    public void adjustLiftHeight() {

        double adjustmentSize = 20;

        if (rh.gamepad1.y) {

            liftPID.adjustTargetPosition(adjustmentSize);

        } else if (rh.gamepad1.b) {

            liftPID.adjustTargetPosition(-adjustmentSize);
        }
    }

    public double getLiftPowerLogistic(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoderLeft(), targetPosition, .01);
    }

    public double getLiftPowerPID(double maxPower) {

        double pos = 0;

        if (level.equals("home")) {

            pos = 0;

        } else if (level.equals("low")) {

            pos = 500;

        } else if (level.equals("middle")) {

            pos = 1700;

        } else if (level.equals("high")) {

            pos = 2900;
        }

        liftPID.setTargetPosition(pos);
        liftPID.setStartTime(rh.time.milliseconds());

        if (goHome == false) { // so the lift can move down while homing
            liftPID.checkForInvalid();
        }

        return getLiftPowerLogistic(maxPower, liftPID.getTargetPosition());
    }

    public double getArm() { //TODO: find actual positions

        double liftArmPosition = 0.0;

        if (level.equals("home") && mirrored) {

            liftArmPosition = 1.0;

        } else if (level.equals("home")) {

            liftArmPosition = 0.0;

        } else if (mirrored) {

            liftArmPosition = 0.75;

        } else {

            liftArmPosition = 0.25;

        }

        return liftArmPosition;
    }

    public double getFlip() {

        double liftArmPosition = 0.0;

        if (mirrored) {

            liftArmPosition = 1.0;

        } else {

            liftArmPosition = 0.0;
        }

        return liftArmPosition;
    }

    public double getRotate() {

        double liftArmPosition = 0.0;

        if (mirrored) {

            liftArmPosition = 1.0;

        } else {

            liftArmPosition = 0.0;
        }

        return liftArmPosition;
    }

    public double getClaw() {

        double clawPosition = rh.getClawPos();

        if (rh.gamepad1.left_bumper) {
            if (lastClaw == clawPosition) {
                clawPosition = (clawPosition + 1) % 2;
            }
        } else {
            lastClaw = rh.getClawPos();
        }

        rh.telemetry.addData("clawPos", clawPosition);
        rh.telemetry.addData("lastClaw", lastClaw);
        rh.telemetry.addLine();

        return clawPosition;
    }
}

