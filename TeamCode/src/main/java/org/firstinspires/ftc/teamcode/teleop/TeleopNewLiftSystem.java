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
    PID liftPID = new PID(5700); // for new robot

    private double lastClaw = 0;

    private boolean mirrored = false;
    private boolean canMirror = false;

    private boolean armMirror1 = false;
    private boolean flipMirror = false;
    private boolean endMirror = false;
    private boolean rotateMirror = false;

    private boolean liftTargetSetBefore = false;
    private boolean canJump = false;

    private String level = null;
    private String lastLevel = null;
    private boolean vertical = false;
    private boolean stayVertical = false;

    private int goHome;

    private double armPos = 0;
    private double flipPos = 0;
    private double rotatePos = 0;
    private double clawPos = 0;
    
    private double currentSeconds = 0;
    private double mirrorSeconds = 0;

    public TeleopNewLiftSystem(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {

        /**
         * Servo positions:
         * arm:
         *      0 = down back
         *      0.282 = lifted (back)
         *      0.48 = vertical (transition)
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
         *      0.2 = closed
         *      0 = open
         **/

//        front down default
//        armPos = 1;
//        flipPos = 0;
//        rotatePos = 0;
//        clawPos = 0;

//        custom
        armPos = 0.48;
        flipPos = 0.5;
        rotatePos = 1;
        clawPos = 0.2;

//        rh.setServoPositions(armPos, flipPos, rotatePos, clawPos);
        goHome = 2;
        level = "home";
        lastLevel = "home";
        vertical = true;
        
        mirrored = false;
        canMirror = true;
        
        armMirror1 = false;
        flipMirror = false;
        rotateMirror = false;
        endMirror = true;

        canJump = true;
    }

    @Override
    public boolean getIsDone() {

        return false;
    }

    public void update() {

        currentSeconds = rh.time.seconds();
        goHome = rh.lift.getGoHome();

        double armPos = getArm();
        double flipPos = getFlip();
        double rotatePos = getRotate();
        double clawPos = getClaw();

        // set lift movement speed
        double maxLiftSpeed = 1.0;

        double liftSpeed = getLiftPowerPID(maxLiftSpeed);

        double liftMove = getLiftPowerLogistic(liftSpeed, liftPID.getTargetPosition());

        level = getLevel();
        
        getMirrored();

        adjustLiftHeight();
//        adjustServo();

        jumpLiftHeight();

        rh.setLiftTarget(liftPID.getTargetPosition());
        rh.lift(liftMove);

        rh.setServoPositions(armPos, flipPos, rotatePos, clawPos);

        rh.telemetry.addData("Level", level);
        rh.telemetry.addData("Last level", lastLevel);

        rh.telemetry.addData("Mirrored", mirrored);
        rh.telemetry.addData("Can Mirrored", canMirror);
        rh.telemetry.addData("Arm 1 mirrored", armMirror1);
        rh.telemetry.addData("Flip mirrored", flipMirror);
        rh.telemetry.addData("Rotate mirrored", rotateMirror);
        rh.telemetry.addData("Arm 2 mirrored", armMirror1);
        rh.telemetry.addData("End mirrored", endMirror);

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

            vertical = false;
            liftTargetSetBefore = false;

        } else if (rh.gamepad1.b) { // toggle lift up

            if(lastLevel.equals("low")) {

                newLevel = "home";

            } else if(lastLevel.equals("middle")) {

                newLevel = "low";

            } else if(lastLevel.equals("high")) {

                newLevel = "middle";

            }

            vertical = false;
            liftTargetSetBefore = false;

        } else if (rh.gamepad1.a) { // home the lift

            newLevel = "home";

            vertical = false;
            liftTargetSetBefore = false;

        } else if (rh.gamepad1.x) { // home the lift

            vertical = true;

        } else if (rh.gamepad1.dpad_left) {

            newLevel = "topStack";

            vertical = false;
            liftTargetSetBefore = false;

        } else {

            lastLevel = level;
        }


        return newLevel;
    }

    public void getMirrored() {

        if (rh.gamepad1.right_bumper && canMirror && vertical) {

                endMirror = false;
//                mirrorSeconds = currentSeconds;

                armMirror1 = true;
                canMirror = false;
        }
    }

    public void adjustLiftHeight() {

        double adjustmentSize = 20;

        if (rh.gamepad1.dpad_up) {

            liftPID.adjustTargetPosition(adjustmentSize);

        } else if (rh.gamepad1.dpad_down) {

            liftPID.adjustTargetPosition(-adjustmentSize);
        }
    }

    public void jumpLiftHeight() {

        double adjustmentSize = 900;

        double clawPosition = rh.getClawPos();

        if (rh.gamepad1.dpad_right) {

            if (canJump) {

                liftPID.adjustTargetPosition(adjustmentSize);
                canJump = false;

            }

        } else {
            canJump = true;
        }
    }

//    public void adjustServo() {
//
//        double adjustmentSize = 0.001;
//
//        if (rh.gamepad1.x) {
//
//            flipPos += adjustmentSize;
//
//        } else if (rh.gamepad1.a) {
//
//            flipPos -= adjustmentSize;
//        }
//    }

    public double getLiftPowerLogistic(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoderLeft(), targetPosition, .01);
    }

    public double getLiftPowerPID(double maxPower) {

        double pos = 0;

        if (level.equals("home")) {

            pos = 0;

        } else if (level.equals("low")) {

            pos = 1460;

        } else if (level.equals("middle")) {

            pos = 3310;

        } else if (level.equals("high")) {

            pos = 4975;

        } else if (level.equals("topStack")) {

            pos = 995;
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

        /*
        0 = down back
        1 = down front
         */

        double liftArmPosition = 0.0;

        if (armMirror1 && !mirrored) {

            for (double i = 0.48; i >= 0.0; i -= 0.00000002) {

                liftArmPosition = i;
            }

            flipMirror = true;

        } else if (armMirror1 && mirrored) {

            for (double i = 0.48; i <= 1; i += 0.00000002) {

                liftArmPosition = i;
            }

            flipMirror = true;

        } else if (vertical) {

            liftArmPosition = 0.48;

        } else if ((level.equals("home") || level.equals("topStack")) && mirrored) {

            liftArmPosition = 0.0;

        } else if ((level.equals("home") || level.equals("topStack")) && !mirrored) {

            liftArmPosition = 1.0;

        } else if (mirrored) {

            liftArmPosition = 0.282;

        } else {

            liftArmPosition = 0.668;

        }

        return liftArmPosition;
    }

    public double getFlip() {

        /* rotate:
         *      0 = for the front
         *      1 = for the back
         */

        double flipPosition = 0.0;

        if (flipMirror && !mirrored) {

            for (double i = 0.5; i <= 1; i += 0.0000002) {

                flipPosition = i;
            }

            rotateMirror = true;

        } else if (flipMirror && mirrored) {

            for (double i = 0.5; i >= 0.0; i -= 0.0000002) {

                flipPosition = i;
            }

            rotateMirror = true;

        } else if (vertical) {

            flipPosition = 0.5;

        } else if (mirrored) {

            flipPosition = 1.0;

        } else if (!mirrored) {

            flipPosition = 0.0;

        }
        
        return flipPosition;
    }

    public double getRotate() {

        /* rotate:
         *      0 = for the front
         *      1 = for the back
         */

        double rotatePosition = 0.0;

        if (rotateMirror && !mirrored) {

            rotatePosition = 1.0;

            mirrored = true;

            endMirror = true;
            armMirror1 = false;
            flipMirror = false;
            rotateMirror = false;
            vertical = false;

            if(level.equals("home")) {
                liftTargetSetBefore = false;
                level = "low";
            }

            canMirror = true;

        } else if (rotateMirror && mirrored) {

            rotatePosition = 0.0;

            mirrored = false;

            endMirror = true;
            armMirror1 = false;
            flipMirror = false;
            rotateMirror = false;
            vertical = false;

            if(level.equals("home")) {
                    liftTargetSetBefore = false;
                    level = "low";
            }

            canMirror = true;

        } else if (mirrored) {

            rotatePosition = 1.0;

        } else if (!mirrored) {

            rotatePosition = 0.0;

        }

        return rotatePosition;
    }
    
    public double getClaw() {

        double clawPosition = rh.getClawPos();

        if (vertical) {

            clawPosition = 0.2;

        } else if (rh.gamepad1.left_bumper) {

            if (lastClaw == clawPosition) {
                if (clawPosition == 0.0) {

                    clawPosition = 0.2;

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

