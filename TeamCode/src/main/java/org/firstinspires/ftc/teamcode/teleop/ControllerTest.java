package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp(name="Controller Test")
public class ControllerTest extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addData("Gamepad 1 Left Stick X", gamepad1.left_stick_x);
        telemetry.addData("Gamepad 1 Left Stick Y", gamepad1.left_stick_y);
        telemetry.addData("Gamepad 1 Right Stick X", gamepad1.right_stick_x);
        telemetry.addData("Gamepad 1 Right Stick Y", gamepad1.right_stick_y);

        telemetry.addData("Gamepad 2 Left Stick X", gamepad2.left_stick_x);
        telemetry.addData("Gamepad 2 Left Stick Y", gamepad2.left_stick_y);
        telemetry.addData("Gamepad 2 Right Stick X", gamepad2.right_stick_x);
        telemetry.addData("Gamepad 2 Right Stick Y", gamepad2.right_stick_y);

        telemetry.update();
    }
}
