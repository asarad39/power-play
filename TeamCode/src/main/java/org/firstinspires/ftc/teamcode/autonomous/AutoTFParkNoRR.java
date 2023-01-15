package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class AutoTFParkNoRR implements State {

    /***
     * Code for reading the cone and parking without roadrunner:
     * new AutoTensorFlow(rh, false),
     * new AutoDriveTime(rh, 3, "forward", 0.2),
     * new AutoTFParkNoRR(rh)
     */

    RobotHardware rh = null;

    private double startTime;
    private double moveX = 0;
    private double moveY = 0;
    private double moveRotate = 0;
    private int sleeve = 0;
    private double power;

    private double seconds = 0;

    private boolean isDone = false;

    public AutoTFParkNoRR(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

        sleeve = RobotHardware.getSleeve();
        power = 0.3;

        if (sleeve == 3) {

            moveX = power;
            seconds = 2.75;

        } else if (sleeve == 2) {

            moveX = 0;
            seconds = 0;

        } else if (sleeve == 1) {

            moveX = -power;
            seconds = 2.75;

        } else {
            throw new IllegalArgumentException("sleeve is not valid");
        }

        startTime = rh.time.seconds();
    }

    public void update() {

        double powerFR = + moveX + moveY - moveRotate;
        double powerFL = + moveX - moveY - moveRotate;
        double powerBR = - moveX + moveY - moveRotate;
        double powerBL = - moveX - moveY - moveRotate;

        rh.drive(powerFR, powerFL, powerBR, powerBL);
    }

    public boolean getIsDone() {

        if (rh.time.seconds() > seconds + startTime) {

            rh.drive(0, 0, 0, 0);
            return true;
        }

        return false;
    }
}