package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class TeleopArmControl implements State {

    RobotHardware rh = null;

    private boolean clawClosed = false;
    private boolean armDown = true;

    private boolean canClaw = true;
    private boolean canArm = true;

    public TeleopArmControl(RobotHardware rh) {

        this.rh = rh;
    }

    public void init() {

    }

    public void update() {

        // left bumper claw
        if (rh.gamepad1.left_bumper) {
            if (canClaw) {
                clawClosed = !clawClosed;
                canClaw = false;
            }
        } else {
            canClaw = true;
        }

        // right bumper arm
        if (rh.gamepad1.right_bumper) {
            if (canArm) {
                armDown = !armDown;
                canArm = false;
            }
        } else {
            canArm = true;
        }

        rh.armNew.clawUpdate(clawClosed);
        rh.armNew.armUpdate(armDown);
    }

    @Override
    public boolean getIsDone() {
        return false;
    }
}
