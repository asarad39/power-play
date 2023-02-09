package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoLineRR implements State {

    RobotHardware rh = null;
    Trajectory traj = null;

    private double x;
    private double y;
    private boolean backwards;
    private boolean track = false;

    private Pose2d startPose = null;
    private Pose2d endPose = null;

    public AutoLineRR(RobotHardware rh,
                        boolean track,
                        double x,
                        double y,
                        boolean backwards) {

        this.rh = rh;
        this.x = x;
        this.y = y;

        this.backwards = backwards;
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

        traj = rh.sampleMec.trajectoryBuilder(startPose, backwards)
                // Also works for forward, strafe, etc.
                .lineTo(new Vector2d(x, y))
                .build();

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