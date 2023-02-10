package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopArmControl implements State {

    RobotHardware rh = null;

    private boolean facingFront = true;
    private boolean isVertical = false;

    private boolean canMirror = true;
    private boolean canScore = true;
    private boolean canCollect = true;
    private boolean canVertical = true;
    private int level = 0;  //  0 collect  1 score  2 vertical

    public TeleopArmControl(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

    }

    public void update() {

        // left bumper mirror
        if (rh.gamepad1.left_bumper) {
            if (canMirror) {
                facingFront = !facingFront;
                canMirror = false;
            }
        } else {
            canMirror = true;
        }

        // x go to vertical
        if (rh.gamepad1.x) {
            if (canVertical) {
                isVertical = !isVertical;
                canVertical = false;
            }
        } else {
            canVertical = true;
        }

        // go to score
        if (rh.liftNew.getPosition() > 0) {
            level = 1;
        } else {
            level = 0;
        }

        if (isVertical) {
            level = 2;
        }

        // convert side and level to index
        int index = level;
        if (!facingFront) {
            index = 5 - level;
        }

        rh.armNew.setPosition(index);

// todo only set position when index != currentindex

        // claw
        rh.armNew.moveClaw(rh.gamepad1.right_bumper);

        rh.armNew.clawUpdate();
        rh.armNew.armUpdate();
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
