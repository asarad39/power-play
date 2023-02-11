package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.ArmMove;
import org.firstinspires.ftc.teamcode.hardware.ClawMove;
import org.firstinspires.ftc.teamcode.hardware.FlipMove;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RotateMove;
import org.firstinspires.ftc.teamcode.stateStructure.ParallelStack;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

//@Disabled
@Autonomous(name="Right Medium Pole FLIP Auto")
public class RightMediumPoleFlipAuto extends OpMode
{

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware();
    private SeriesStack autoStack = new SeriesStack(rh);

    @Override
    public void init() {

        rh.initialize(this);

        ParallelStack setUpServos = new ParallelStack(rh);
        State[] forSetUpServos = {

                new ArmMove(rh, 1, true),
                new ClawMove(rh, 0.30, true),
                new FlipMove(rh, 1, true),
                new RotateMove(rh, 0, true),
        };
        setUpServos.createStack(forSetUpServos);

        //        // TODO: test if we can move servos on init
        setUpServos.init();
        setUpServos.update();

        ParallelStack driveAndLift1 = new ParallelStack(rh);
        State[] forDriveAndLift1 = {

                new ArmMove(rh, 0.668),
                new AutoMoveLift(rh, "middle"),
                new AutoGridRR(rh, "forward", 7.5 + 24),

        };
        driveAndLift1.createStack(forDriveAndLift1);

        SeriesStack flipSides = new SeriesStack(rh);
        State[] forFlipSides = {
                new ClawMove(rh, 0.3),
                new ArmMove(rh, 0.48),
                new FlipMove(rh, 0.5),
                new RotateMove(rh, 1),
                new FlipMove(rh, 0),
                new ArmMove(rh, 0),
                new ClawMove(rh, 0),
        };
        flipSides.createStack(forFlipSides);

        ParallelStack driveAndLift2 = new ParallelStack(rh);
        State[] forDriveAndLift2 = {

                flipSides,
                new AutoMoveLift(rh, "home", true),
//                new AutoGridRR(rh, "forward", 24),

        };
        driveAndLift2.createStack(forDriveAndLift2);

        State[] forAutoStack = {

                new AutoTensorFlow(rh, true),
//                setUpServos,
                driveAndLift1,
                new AutoGridRR(rh, "left", 12 + 1),
                new ClawMove(rh, 0.0),
                new AutoGridRR(rh, "right", 12 + 1),
                driveAndLift2,
                new AutoTFParkRRSimple(rh),
        };

        autoStack.createStack(forAutoStack);
        autoStack.init();

        // Tell the driver that initialization is complete.
        rh.telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        rh.telemetry.addData("autoStack", autoStack.getIsDone());
        rh.telemetry.addData("sleeve", RobotHardware.getSleeve());

        if (!autoStack.getIsDone()) {
            autoStack.update();
        }
    }
}