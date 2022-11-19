package org.firstinspires.ftc.teamcode;

public class PID {

    private double targetPosition;
    private double startTime;

    public PID() {
        targetPosition = 0;
        startTime = 0;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }
}
