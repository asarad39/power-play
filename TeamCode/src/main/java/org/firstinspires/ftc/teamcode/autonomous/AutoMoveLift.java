package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoMoveLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID(2900);

    private double startTime;
    private String goalString;

    private boolean isDone = false;

    public AutoMoveLift(RobotHardware rh, String goalString) {
        this.rh = rh;
        this.goalString = goalString;
    }

    public void init() {
        startTime = rh.time.seconds();
        double maxLiftSpeed = 0.8;

        double liftSpeed = getLiftPowerPID(goalString, maxLiftSpeed);
    }

    public boolean getIsDone() {
        return isDone;
//        return false;
    }


    public void debug() {
        rh.telemetry.addData("State Console", "AUTOLIFT");
        rh.telemetry.addData("Goal Position", goalString);
        rh.telemetry.addData("PID", liftPID.getTargetPosition());
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.8;

        double liftSpeed = getLiftPowerPID(goalString, maxLiftSpeed);
        rh.setLiftTarget(liftPID.getTargetPosition());

        double liftMove = getLiftPowerLgstcCrv(liftSpeed, liftPID.getTargetPosition());

        rh.lift(liftMove);

        isDone = (15 > Math.abs(rh.getLiftEncoderRight() - liftPID.getTargetPosition()));
        this.debug();
    }

    public void setGoalString(String g) {
        goalString = g;
    }


    public double getLiftPowerLgstcCrv(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoderRight(), targetPosition, .01);
    }

    public double getLiftPowerPID(String goalString, double maxPower) {

        double home = 0;
        double low = 240; // 1460.0 / (19.2 / 3.7);
        double middle = 3310.0 / (19.2 / 3.7);
        double high = 1020; // 4975.0 / (19.2 / 3.7);

        if (goalString.equals("home")) {
            liftPID.setTargetPosition(home);
            liftPID.setStartTime(rh.time.milliseconds());

        } else if (goalString.equals("low")) {
            liftPID.setTargetPosition(low);
            liftPID.setStartTime(rh.time.milliseconds());

        } else if (goalString.equals("middle")) {
            liftPID.setTargetPosition(middle);
            liftPID.setStartTime(rh.time.milliseconds());

        } else if (goalString.equals("high")) {
            liftPID.setTargetPosition(high);
            liftPID.setStartTime(rh.time.milliseconds());

        } else {
            throw new IllegalArgumentException("nonexistent lift position name");
        }

        return getLiftPowerLgstcCrv(maxPower, liftPID.getTargetPosition());
    }

}