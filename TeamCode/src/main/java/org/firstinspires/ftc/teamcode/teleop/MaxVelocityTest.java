package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.ParallelStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// Teleop program that uses TeleopMove state to drive using robot controller

@TeleOp(name="Max Velocity Test from Doc")
public class MaxVelocityTest extends LinearOpMode {

    DcMotorEx motor;

    double currentVelocity;

    double maxVelocity = 0.0;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "motorFL");
        motor.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();


        while (opModeIsActive()) {

            currentVelocity = motor.getVelocity();



            if (currentVelocity > maxVelocity) {

                maxVelocity = currentVelocity;

            }



            telemetry.addData("current velocity", currentVelocity);

            telemetry.addData("maximum velocity", maxVelocity);

            telemetry.update();

        }

    }

}