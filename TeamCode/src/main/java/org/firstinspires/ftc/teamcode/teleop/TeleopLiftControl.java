package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopLiftControl implements State {

    RobotHardware rh = null;

    private boolean canJump = true;
    private boolean canUP = true;
    private boolean canDOWN = true;

    public TeleopLiftControl(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

    }

    public void update() {

        // go up 1
        if (rh.gamepad1.y) {
            if (canUP) {
                rh.liftNew.adjustPosition(LiftControl.Positions.UP);
                canUP = false;
            }
        } else {
            canUP = true;
        }

        // go down 1
        if (rh.gamepad1.b) {
            if (canDOWN) {
                rh.liftNew.adjustPosition(LiftControl.Positions.UP);
                canDOWN = false;
            }
        } else {
            canDOWN = true;
        }

        // collect
        if (rh.gamepad1.a) {
            rh.liftNew.setPositionsIndex(0);
        }

        // high
        if (rh.gamepad1.left_stick_button) {
            rh.liftNew.setPositionsIndex(3);
        }

        // jumping
        if (rh.gamepad1.dpad_right) {
            if (canJump) {
                rh.liftNew.adjustOffset(100);
                canJump = false;
            }
        } else {
            canJump = true;
        }

        int offsetSize = 10;
        // adjust lift height
        if (rh.gamepad1.dpad_down) {
            rh.liftNew.adjustOffset(-offsetSize);
        } else if (rh.gamepad1.dpad_up) {
            rh.liftNew.adjustOffset(offsetSize);
        }

        rh.liftNew.setPosition();
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
