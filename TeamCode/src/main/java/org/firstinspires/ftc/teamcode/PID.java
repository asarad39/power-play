package org.firstinspires.ftc.teamcode;

public class PID {

    private double targetPosition;
    private double startTime;
    private double maxHeight;

    public PID(double max) {
        targetPosition = 0;
        startTime = 0;
        maxHeight = max;
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

    public void adjustTargetPosition(double adjustment) {
        targetPosition += adjustment;
    }

    public void checkForInvalid(double minPos) {
        if (targetPosition < minPos) {
            targetPosition = 0;
        }
        if (targetPosition > maxHeight) {
            targetPosition = maxHeight;
        }
    }
}
