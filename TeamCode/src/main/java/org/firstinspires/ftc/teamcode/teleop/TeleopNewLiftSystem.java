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
//    PID liftPID = new PID(2900);
    PID liftPID = new PID(5000); // for new robot

    private double lastClaw = 0;

    private boolean mirrored = false;
    private boolean lastMirrored = false;

    private boolean startMirror = false;
    private boolean mirrorCheck = false;
    private boolean endMirror = false;

    private boolean liftTargetSetBefore = false;

    private String level = null;
    private String lastLevel = null;

    private int goHome;

    private double armPos = 0;
    private double flipPos = 0;
    private double rotatePos = 0;
    private double clawPos = 0;


    public TeleopNewLiftSystem(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {

        /**
         * Servo positions:
         * arm:
         *      0 = down back
         *      0.282 = lifted (back)
         *      0.475 = vertical (transition)
         *      0.668 = lifted (front)
         *      1 = down front
         * flip:
         *      0 = for the front
         *      0.5 = vertical
         *      1 = for the back
         * rotate:
         *      0 = for the front
         *      1 = for the back
         * claw:
         *      0.16 = closed
         *      0 = open
         *
         * flip and rotate pos = always same position
         * arm = [0.5, 1] -> flipAndRotate = 0
         * arm = [0, 0.5] -> flipAmdRotate = 1
         **/

        armPos = 1;
        flipPos = 0;
        rotatePos = 0;
        clawPos = 0.16;

        rh.setServoPositions(armPos, flipPos, rotatePos, clawPos);
        goHome = 2;
        level = "home";
        lastLevel = "home";
        mirrored = false;
        lastMirrored = false;
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

        double liftMove = getLiftPowerLogistic(liftSpeed, liftPID.getTargetPosition());

        level = getLevel();
//        mirrored = getMirrored();

        adjustLiftHeight();
        adjustServo();

        rh.setLiftTarget(liftPID.getTargetPosition());
        rh.lift(liftMove);

        rh.setServoPositions(armPos, flipPos, rotatePos, clawPos);

        rh.telemetry.addData("Level", level);
        rh.telemetry.addData("Last level", lastLevel);

        rh.telemetry.addData("Mirrored", mirrored);
        rh.telemetry.addData("Last mirrored", lastMirrored);

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

        if (rh.gamepad1.y) { // toggle lift up

            if(lastLevel.equals("home")) {

                newLevel = "low";

            } else if(lastLevel.equals("low")) {

                newLevel = "middle";

            } else if(lastLevel.equals("middle")) {

                newLevel = "high";

            }

            liftTargetSetBefore = false;

        } else if (rh.gamepad1.b){

            if(lastLevel.equals("low")) {

                newLevel = "home";

            } else if(lastLevel.equals("middle")) {

                newLevel = "low";

            } else if(lastLevel.equals("high")) {

                newLevel = "middle";

            }

            liftTargetSetBefore = false;

        } else {

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

        if (rh.gamepad1.dpad_up) {

            liftPID.adjustTargetPosition(adjustmentSize);

        } else if (rh.gamepad1.dpad_down) {

            liftPID.adjustTargetPosition(-adjustmentSize);
        }
    }

    public void adjustServo() {

        double adjustmentSize = 0.001;

        if (rh.gamepad1.x) {

            armPos += adjustmentSize;

        } else if (rh.gamepad1.a) {

            armPos -= adjustmentSize;
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

        if (!liftTargetSetBefore) {
            liftPID.setTargetPosition(pos);
            liftTargetSetBefore = true;
        }

        liftPID.setStartTime(rh.time.milliseconds());

        if (goHome == 0) { // so the lift can move down while homing
            liftPID.checkForInvalid();
        }

        return getLiftPowerLogistic(maxPower, liftPID.getTargetPosition());
    }

    /**
     *
     * @return
     */

    public double getArm() { //TODO: find actual positions

        double liftArmPosition = 0.0;

        if (level.equals("home") && mirrored) {

            liftArmPosition = 0.0;

        } else if (level.equals("home")) {

            liftArmPosition = 1.0;

        } else if (mirrored) {

            liftArmPosition = 0.282;

        } else {

            liftArmPosition = 0.668;

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
                if (clawPosition == 0.0) {

                    clawPosition = 0.16;

                } else {

                    clawPosition = 0.0;
                }
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

