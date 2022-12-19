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

    public AutoMoveLift(RobotHardware rh, String goalString) {
        this.rh = rh;
        this.goalString = goalString;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {
        return (15 > Math.abs(rh.getLiftEncoder1() - liftPID.getTargetPosition()));
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