package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class AutoTFParkRRSimple implements State {

    /***
     * Code for reading the cone and parking without roadrunner:
     * new AutoTensorFlow(rh, false),
     * new AutoDriveTime(rh, 3, "forward", 0.2),
     * new AutoTFParkNoRR(rh)
     */

    RobotHardware rh = null;
    Trajectory traj = null;

    private double distance = 24;
    private int sleeve = 0;

    public AutoTFParkRRSimple(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

        sleeve = RobotHardware.getSleeve();
        Pose2d startPose = new Pose2d();

        if (sleeve == 3) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .strafeLeft(distance)
                    .build();

        } else if (sleeve == 2) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .forward(0)
                    .build();

        } else if (sleeve == 1) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .strafeRight(distance)
                    .build();

        } else {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .forward(0)
                    .build();

        }

        rh.sampleMec.followTrajectoryAsync(traj);
    }

    public void update() {

        rh.sampleMec.update();
    }

    public boolean getIsDone() {

        // Updates the robot's current position to that created by the trajectory

        return !rh.sampleMec.isBusy();
    }
}