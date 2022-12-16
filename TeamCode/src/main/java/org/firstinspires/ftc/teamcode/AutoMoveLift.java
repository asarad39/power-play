package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// State for driving using game controller
public class AutoMoveLift implements State {

    RobotHardware rh = null;
    Telemetry telemetry;
    PID liftPID = new PID(2900);

    private double startTime;
    private double targetPos;
    private String goalString;

    public AutoMoveLift(RobotHardware rh, double targetPos, String goalString) {
        this.rh = rh;
        this.targetPos = targetPos;
        this.goalString = goalString;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {
        return false; //TODO: check encoder positions
    }

    public void update() {

        // set lift movement speed
        double maxLiftSpeed = 0.8;

        double liftSpeed = getLiftPowerPID(maxLiftSpeed);
        rh.liftTarget(liftPID.getTargetPosition());

        double liftMove = getLiftPowerLgstcCrv(liftSpeed, liftPID.getTargetPosition());

        rh.lift(liftMove);
    }


    public double getLiftPowerLgstcCrv(double maxPower, double targetPosition) {
        return Mathematics.getLogisticCurve(maxPower, rh.getLiftEncoder1(), targetPosition, .01);
    }

    public double getLiftPowerPID(double maxPower) {
        double home = 0;
        double low = 500;
        double middle = 1700;
        double high = 2900;

        if (goalString.equals("home")) {
            liftPID.setTargetPosition(home);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (goalString.equals("low")) {
            liftPID.setTargetPosition(low);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (goalString.equals("middle")) {
            liftPID.setTargetPosition(middle);
            liftPID.setStartTime(rh.time.milliseconds());
        }
        if (goalString.equals("high")) {
            liftPID.setTargetPosition(high);
            liftPID.setStartTime(rh.time.milliseconds());
        }

        return getLiftPowerLgstcCrv(maxPower, liftPID.getTargetPosition());
    }

}