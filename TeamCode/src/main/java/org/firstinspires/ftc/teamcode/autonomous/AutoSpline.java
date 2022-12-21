package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.roadrunnerPackages.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoSpline implements State {

    RobotHardware rh = null;
    Trajectory traj = null;

    private double x;
    private double y;
    private double theta;
    private boolean backwards;

    private boolean isDone = false;
    private Pose2d startPose = null;
    private Pose2d endPose = null;

    public AutoSpline(RobotHardware rh,
                      boolean track,
                      double x,
                      double y,
                      double theta,
                      boolean backwards) {

        this.rh = rh;
        this.x = x;
        this.y = y;

        // any angle must be in radians. Angles go counterclockwise!
        this.theta = theta;
        this.backwards = backwards;

        if(track) {
            startPose = rh.getCurrentPose();
        } else {
            startPose = new Pose2d();
        }
    }

    public void init() {

        // We may have a delay in our program since we create a new trajectory for each movement

        rh.sampleMec.setPoseEstimate(startPose);

        traj = rh.sampleMec.trajectoryBuilder(startPose, backwards)
                // Also works for forward, strafe, etc.
                .splineTo(new Vector2d(x, y), theta)
                .build();
    }

    public void update() {

        // The OpMode will not move on until the robot has finished the trajectory
        rh.sampleMec.followTrajectory(traj);
        endPose = traj.end();
        isDone = true;
    }

    public boolean getIsDone() {

        // Updates the robot's current position to that created by the trajectory
        rh.setCurrentPose(endPose);
        return isDone;
    }
}