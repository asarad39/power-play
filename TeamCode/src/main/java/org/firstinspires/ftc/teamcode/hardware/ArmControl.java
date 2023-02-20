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

    ServoControl arm = null;
    ServoControl claw = null;



    public void initialize(OpMode op, RobotHardware rh) {
        // Get telemetry object from opMode for debugging
        this.telemetry = op.telemetry;
        this.rh = rh;

        arm = new ServoControl("arm");
        claw = new ServoControl("claw");

        arm.initialize(op);
        claw.initialize(op);

    }

    public void armUpdate(boolean armDown) {
        telemetry.addData("armDown", armDown);
        telemetry.addData("arm.isMoving", arm.isMoving());
        if (armDown) {
            arm.setTargetPosition(0.23);
        } else {
            arm.setTargetPosition(0);
        }
        arm.move();
    }

    public void clawUpdate(boolean clawClosed) {
        telemetry.addData("clawClosed", clawClosed);
        telemetry.addData("claw.isMoving", claw.isMoving());
        if (clawClosed) {
            claw.setTargetPosition(0.23);
        } else {
            claw.setTargetPosition(0);
        }
        claw.move();
    }
}
