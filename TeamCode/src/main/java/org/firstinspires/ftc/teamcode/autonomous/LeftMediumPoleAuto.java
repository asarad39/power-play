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
@Autonomous(name="Left Medium Pole Auto")
public class LeftMediumPoleAuto extends OpMode
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

                new ArmMove(rh, 1),
                new ClawMove(rh, 0.30),
                new FlipMove(rh, 1),
                new RotateMove(rh, 0),
        };
        setUpServos.createStack(forSetUpServos);

        ParallelStack driveAndLift1 = new ParallelStack(rh);
        State[] forDriveAndLift1 = {

                new ArmMove(rh, 0.668),
                new AutoMoveLift(rh, "low"),
                new AutoGridRR(rh, "forward", 4 + 24),

        };
        driveAndLift1.createStack(forDriveAndLift1);

        ParallelStack driveAndLift2 = new ParallelStack(rh);
        State[] forDriveAndLift2 = {

                new ArmMove(rh, 1.0),
                new AutoMoveLift(rh, "home"),
//                new AutoGridRR(rh, "forward", 24),

        };
        driveAndLift2.createStack(forDriveAndLift2);

        State[] forAutoStack = {

                new AutoTensorFlow(rh, true),
                setUpServos,
                driveAndLift1,
                new AutoGridRR(rh, "right", 12),
                new ClawMove(rh, 0.0),
                new AutoGridRR(rh, "left", 12),
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