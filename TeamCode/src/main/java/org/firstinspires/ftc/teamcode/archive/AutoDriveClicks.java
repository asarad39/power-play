package org.firstinspires.ftc.teamcode.archive;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoDriveClicks implements State {

    RobotHardware rh = null;
    
    private String direction;
    private double seconds;
    private double power;
    private double targetClicks;
    private boolean isDone;
    
    private double currentLeftClicks;
    private double currentRightClicks;
    private double currentBackClicks;

    public AutoDriveClicks(RobotHardware rh, 
                           double seconds, 
                           String direction, 
                           double power, 
                           double clicks) {
        this.rh = rh;
        this.direction = direction;
        this.seconds = seconds;
        this.power = power;
        this.targetClicks = clicks;
    }

    public void init() {
     
        
    }

    public boolean getIsDone() {

        return isDone;
    }

    public void update() {

        // TODO: correct when we are actually using clicks!
//        currentLeftClicks = rh.getLeftEncoder();
//        currentRightClicks = rh.getRightEncoder();
//        currentBackClicks = rh.getBackEncoder();

        double moveX = 0;
        double moveY = 0;
        double moveRotate = 0;

        if (direction.equals("forward")) {
            
            moveY = -power;
            isDone = (15 > Math.abs(currentLeftClicks - targetClicks));
            
        } else if (direction.equals("backward")) {
            
            moveY = power;
            isDone = (15 > Math.abs(currentLeftClicks - targetClicks));
        
        } else if (direction.equals("right")) {
            
            moveX = power;
            isDone = (15 > Math.abs(currentBackClicks - targetClicks));
        
        } else if (direction.equals("left")) {
            
            moveX = -power;
            isDone = (15 > Math.abs(currentBackClicks - targetClicks));
            
        } else if (direction.equals("clockwise")) {
            
            moveRotate = power;
            isDone = (15 > Math.abs(currentLeftClicks - targetClicks));
            
        } else if (direction.equals("counter")) {
            
            moveRotate = -power;
            isDone = (15 > Math.abs(currentLeftClicks - targetClicks));
            
        } else {
            throw new IllegalArgumentException("nonexistent move direction");
        }

        double powerFR = - moveX + moveY + moveRotate;
        double powerFL = + moveX + moveY - moveRotate;
        double powerBR = + moveX + moveY + moveRotate;
        double powerBL = - moveX + moveY - moveRotate;

        rh.drive(powerFR, powerFL, powerBR, powerBL);

        rh.telemetry.addData("Direction", direction);
        rh.telemetry.addData("Left clicks", currentLeftClicks);
        rh.telemetry.addData("Right clicks", currentRightClicks);
        rh.telemetry.addData("Back clicks", currentBackClicks);
    }
}