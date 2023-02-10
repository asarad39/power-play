package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.stateStructure.ParallelStack;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmControl {

    RobotHardware rh = null;

    Telemetry telemetry = null;
    SeriesStack armStack = null;

    ServoControl arm = null;
    ServoControl flip = null;
    ServoControl rotate = null;
    ServoControl claw = null;
    ServoControl tiny = null;

    double[] armPositions = {
            1,
            0.668,
            0.48,
            0.48,
            0.282,
            0,
    };
    private final String[] positions = {
            "front down",
            "front up",
            "front vertical",
            "back vertical",
            "back up",
            "back down",
    };

    private int currentIndex = 0;


    public void initialize(OpMode op, RobotHardware rh) {
        // Get telemetry object from opMode for debugging
        this.telemetry = op.telemetry;
        this.rh = rh;

        arm = new ServoControl("arm");
        flip = new ServoControl("flip");
        rotate = new ServoControl("rotate");
        claw = new ServoControl("claw");
        tiny = new ServoControl("tiny");

        arm.initialize(op);
        flip.initialize(op);
        rotate.initialize(op);
        claw.initialize(op);
        tiny.initialize(op);

        armStack = new SeriesStack(rh);
        currentIndex = 1;
        setPosition(0);
    }

    public boolean isMoving() {
        return (0 < armStack.size());
    }



    public void setPosition(int targetIndex) {


        if (!isMoving()) {
            // check if the arm will pass vertical
            if ((currentIndex <= 2) == (targetIndex >= 3)) {

                armStack.stack.add(new ArmMove(rh, armPositions[2]));
                armStack.stack.add(new FlipMove(rh, 0.5));


                ParallelStack armParallel = new ParallelStack(rh);

                double target = (targetIndex / 3);

                armParallel.stack.add(new RotateMove(rh, target));
                armParallel.stack.add(new FlipMove(rh, 1 - target));
                armParallel.stack.add(new ArmMove(rh, armPositions[targetIndex]));

                armStack.stack.add(armParallel);

            } else {

                armStack.stack.add(new ArmMove(rh, armPositions[targetIndex]));

            }

            armStack.init();

            currentIndex = targetIndex;
        }

    }

    public int getGoalIndex(String goal) {
        for (int i = 0; i < positions.length; i++) {
            if (goal.equals(positions[i])) {
                return i;
            }
        }
        return -1;
    }

    public void armUpdate() {
        if (armStack.size() > 0) {
            armStack.update();
        }

        telemetry.addData("Current Arm Index", currentIndex);
        telemetry.addData("Current Arm Position", positions[currentIndex]);
        telemetry.addData("Goal Arm Position", armPositions[currentIndex]);
        telemetry.addData("Arm position", arm.getCurrentPosition());
        telemetry.addData("Claw position", claw.getCurrentPosition());
        telemetry.addData("Flip position", flip.getCurrentPosition());
        telemetry.addData("Rotate position", rotate.getCurrentPosition());
        telemetry.addData("Tiny position", tiny.getCurrentPosition());

        telemetry.addData("Stack", armStack.size());
    }

    private boolean clawClosed = false;
    private boolean canClaw = true;

    public void moveClaw(boolean changeClaw) {
        if (changeClaw && !claw.isMoving()) {
            clawClosed = !clawClosed;
        }
    }

    public void clawUpdate() {
        if (clawClosed || isMoving()) {
            claw.setTargetPosition(0.23);
        } else {
            claw.setTargetPosition(0);
        }
        claw.move();
    }
}
