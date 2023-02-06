package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class ArmControl {

    RobotHardware rh = null;

    Telemetry telemetry = null;
    SeriesStack armStack = new SeriesStack(rh);

    ServoControl arm = null;
    ServoControl flip = null;
    ServoControl rotate = null;
    ServoControl claw = null;
    ServoControl tiny = null;

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
    }

    public boolean isMoving() {
        return (0 < armStack.size());
    }

    private final String[] positions = {
            "front down",
            "front up",
            "front vertical",
//            "back vertical",
            "back up",
            "back down",
    };
    private int currentIndex = 4;

    State[] frontToBackVerticalStack = {
//            new ClawMove("Up"),
//            new RotateClaw,
//            new ClawMove("Back"),
    };
    SeriesStack frontToBackVertical = new SeriesStack(rh);
//    frontToBackVertical.createStack(frontToBackVerticalStack);

    // increases index when running
    private final State[] frontToBack = {
            new ArmMove(rh, "FrontDown"),
            new ArmMove(rh, "FrontUp"),
            new ArmMove(rh, "Vertical"),
//            frontToBackVertical,
            new ArmMove(rh, "BackUp"),
            new ArmMove(rh, "BackDown"),
    };

    State[] backToFrontVerticalStack = {
//            new ClawMove("Up"),
//            new RotateClaw,
//            new ClawMove("Back"),
    };
    SeriesStack backToFrontVertical = new SeriesStack(rh);
//    backToFrontVertical.createStack(backToFrontVerticalStack);

    // decreases index when running
    private final State[] backToFront = {
            new ArmMove(rh, "FrontDown"),
            new ArmMove(rh, "FrontUp"),
            backToFrontVertical,
            new ArmMove(rh, "Vertical"),
            new ArmMove(rh, "BackUp"),
            new ArmMove(rh, "BackDown"),
    };

    public void setPosition(String target) {

        int targetIndex = getTargetIndex(target);

        if (currentIndex < targetIndex) {
            for (int i = currentIndex + 1; i <= targetIndex; i++) {
                armStack.stack.add(frontToBack[i]);
            }
        } else if (currentIndex > targetIndex) {
            for (int i = currentIndex - 1; i >= targetIndex; i--) {
                armStack.stack.add(backToFront[i]);
            }
        } else {}

    }

    public int getTargetIndex(String target) {
        for (int i = 0; i < positions.length; i++) {
            if (target.equals(positions[i])) {
                return i;
            }
        }
        return -1;
    }

    public void armUpdate() {
        if (armStack.size() > 0) {
            armStack.update();
        }
    }
}
