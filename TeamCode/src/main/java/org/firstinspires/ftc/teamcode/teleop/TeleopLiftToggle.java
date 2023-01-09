package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopLiftToggle implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID(2900);

    private double lastClaw = 0;
    private double lastArm = 0;
    private boolean goHome;

    private String level = null;
    private String lastLevel = null;

    private boolean mirrored = false;
    private boolean lastMirrored = false;

    public TeleopLiftToggle(RobotHardware rh) {
        this.rh = rh;
    }

    public void init() {

        rh.liftServos(0, 1);
        goHome = true;
        level = "home";
        lastLevel = "home";
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

        setPositionPID();

        adjustLiftHeight();
        rh.setLiftTarget(liftPID.getTargetPosition());

        double liftMove = getLiftPowerLgstcCrv(maxLiftSpeed, liftPID.getTargetPosition());

        rh.lift(liftMove);


        level = getLevel();
        mirrored = getMirrored();

        rh.telemetry.addData("Level", level);
        rh.telemetry.addData("Last level", lastLevel);

        rh.telemetry.addData("Mirrored", mirrored);
        rh.telemetry.addData("Last mirrored", lastMirrored);

        rh.liftServos(liftArmPosition, liftClawPosition);

        rh.telemetry.addLine();

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

    private String getLevel() {

        String newLevel = this.level;

        if (rh.gamepad2.a) {

            if(lastLevel.equals("home")) {

                newLevel = "high";

            } else if(lastLevel.equals("high")) {

                newLevel = "middle";

            } else if(lastLevel.equals("middle")) {

                newLevel = "low";

            } else if(lastLevel.equals("low")) {

                newLevel = "home";

            }
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

        if (rh.gamepad1.dpad_up == true) {
            liftPID.adjustTargetPosition( adjustmentSize );
        } else if (rh.gamepad1.dpad_down == true) {
            liftPID.adjustTargetPosition( -adjustmentSize );
        }
    }

    public double getLiftPowerLgstcCrv(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoder1(), targetPosition, .01);
    }

    public void setPositionPID() { // TODO: Tune final positions

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

        double liftArmPosition = 0.0;

        if (level.equals("home")) {

            liftArmPosition = 0.0;

        } else {

            liftArmPosition = 1.0;
        }

        return liftArmPosition;
    }
}
