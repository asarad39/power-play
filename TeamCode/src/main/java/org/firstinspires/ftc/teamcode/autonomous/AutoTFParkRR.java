package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class AutoTFParkRR implements State {

    RobotHardware rh = null;
    Trajectory traj = null;

    private double x;
    private double y;
    private double theta;
    private boolean backwards;

    private boolean isDone = false;
    private Pose2d startPose = null;
    private Pose2d endPose = null;
    private boolean track = false;

    private String colorString = null;
    private String sideString = null;

    private int sleeve = 0;

    public AutoTFParkRR(RobotHardware rh, String colorString, String sideString, boolean track) {

        this.colorString = colorString;
        this.sideString = sideString;

        // any angle must be in radians. Angles go counterclockwise!
        this.theta = theta;
        this.backwards = backwards;
        this.track = track;
    }

    public void init() {

        sleeve = RobotHardware.getSleeve();

        if(track) {
            startPose = rh.getCurrentPose();
        } else {
            startPose = new Pose2d();
        }

        rh.sampleMec.setPoseEstimate(startPose);

        // Positions are based on absolute coordinates
        if (colorString.equals("red")) {

            if (sideString.equals("right")) {

                if (sleeve == 3) {

                    x = 24.0;
                    y = 60.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 2) {

                    x = 24.0;
                    y = 36.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 1) {

                    x = 24.0;
                    y = 12.0;
                    theta = Math.toRadians(1.0);

                } else {
                    throw new IllegalArgumentException("sleeve is not valid");
                }
            } else if (sideString.equals("left")) {

                if (sleeve == 3) {

                    x = 24.0;
                    y = -12.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 2) {

                    x = 24.0;
                    y = -36.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 1) {

                    x = 24.0;
                    y = -60.0;
                    theta = Math.toRadians(1.0);

                } else {
                    throw new IllegalArgumentException("sleeve is not valid");
                }
            } else {
                throw new IllegalArgumentException("nonexistent color name");
            }

        } else if (colorString.equals("blue")) {

            if (sideString.equals("right")) {

                if (sleeve == 3) {

                    x = -24.0;
                    y = -60.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 2) {

                    x = -24.0;
                    y = -36.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 1) {

                    x = -24.0;
                    y = -12.0;
                    theta = Math.toRadians(1.0);

                } else {
                    throw new IllegalArgumentException("sleeve is not valid");
                }
            } else if (sideString.equals("left")) {

                if (sleeve == 3) {

                    x = -24.0;
                    y = 12.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 2) {

                    x = -24.0;
                    y = 36.0;
                    theta = Math.toRadians(1.0);

                } else if (sleeve == 1) {

                    x = -24.0;
                    y = 60.0;
                    theta = Math.toRadians(1.0);

                } else {
                    throw new IllegalArgumentException("sleeve is not valid");
                }
            } else {
                throw new IllegalArgumentException("nonexistent color name");
            }

        } else {
            throw new IllegalArgumentException("nonexistent color name");
        }

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