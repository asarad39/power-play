package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.roadrunnerPackages.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoConePark implements State {

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

    public AutoConePark(RobotHardware rh, String colorString, String sideString, boolean track) {

        this.colorString = colorString;
        this.sideString = sideString;

        // any angle must be in radians. Angles go counterclockwise!
        this.theta = theta;
        this.backwards = backwards;
        this.track = track;
    }

    public void init() {

        if(track) {
            startPose = rh.getCurrentPose();
        } else {
            startPose = new Pose2d();
        }

        // We may have a delay in our program since we create a new trajectory for each movement
        rh.sampleMec.setPoseEstimate(startPose);

        if (colorString.equals("red")) {

            if (sideString.equals("right")) {

//                test1 = (red >= 1.9 * blue);
//                test2 = (red >= 2.0 * green);

            } else if (sideString.equals("left")) {

//                test1 = (blue >= 2.0 * red);
//                test2 = (blue >= 1.3 * green);

            } else {

                throw new IllegalArgumentException("nonexistent color name");
            }

        } else if (colorString.equals("blue")) {

            if (sideString.equals("right")) {

//                test1 = (red >= 1.9 * blue);
//                test2 = (red >= 2.0 * green);

            } else if (sideString.equals("left")) {

//                test1 = (blue >= 2.0 * red);
//                test2 = (blue >= 1.3 * green);

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