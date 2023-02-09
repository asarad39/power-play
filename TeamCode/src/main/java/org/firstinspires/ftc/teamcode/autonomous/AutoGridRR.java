package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.roadrunnerPackages.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoGridRR implements State {

    RobotHardware rh = null;
    Trajectory traj = null;

    private double distance;
    private boolean track = false;
    private String direction = null;

    private Pose2d startPose = null;
    private Pose2d endPose = null;

    public AutoGridRR(RobotHardware rh, boolean track, String direction, double distance) {

        this.rh = rh;
        this.direction = direction;
        this.distance = distance;
        this.track = track;
    }

    public void init() {

        // We may have a delay in our program since we create a new trajectory for each movement
        if(track) {
            startPose = rh.getCurrentPose();
        } else {
            startPose = new Pose2d();
        }

        rh.sampleMec.setPoseEstimate(startPose);

        if (direction.equals("forward")) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .forward(distance)
                    .build();

        } else if (direction.equals("back")) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .back(distance)
                    .build();

        } else if (direction.equals("right")) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .strafeRight(distance)
                    .build();

        } else if (direction.equals("left")) {

            traj = rh.sampleMec.trajectoryBuilder(startPose)
                    .strafeLeft(distance)
                    .build();

        } else {
            throw new IllegalArgumentException("nonexistent direction name");
        }

        rh.sampleMec.followTrajectoryAsync(traj);
    }

    public void update() {

        rh.sampleMec.update();
    }

    public boolean getIsDone() {

        // Updates the robot's current position to that created by the trajectory

        if (rh.sampleMec.isBusy()) {
            return false;
        } else {
            endPose = traj.end();
            rh.setCurrentPose(endPose);
            return true;
        }
    }
}