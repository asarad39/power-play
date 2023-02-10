/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name="Drive Test")
public class DriveTestTeleop extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotorEx motorFR = null;
    private DcMotorEx motorBR = null;
    private DcMotorEx motorFL = null;
    private DcMotorEx motorBL = null;

    private double powerFR = 0.0;
    private double powerBR = 0.0;
    private double powerFL = 0.0;
    private double powerBL = 0.0;

    private double velocityFR = 0.0;
    private double velocityBR = 0.0;
    private double velocityFL = 0.0;
    private double velocityBL = 0.0;

    private int encoderOnBR = 0;
    private int encoderOnFR = 0;
    private int encoderOnBL = 0;
    private int encoderOnFL = 0;

    @Override
    public void runOpMode() {

        motorFR = this.hardwareMap.get(DcMotorEx.class, "motorFR");
        motorFL = this.hardwareMap.get(DcMotorEx.class, "motorFL");
        motorBR = this.hardwareMap.get(DcMotorEx.class, "motorBR");
        motorBL = this.hardwareMap.get(DcMotorEx.class, "motorBL");

        motorFL.setDirection(DcMotorEx.Direction.REVERSE);
        motorBL.setDirection(DcMotorEx.Direction.REVERSE);
        motorBR.setDirection(DcMotorEx.Direction.FORWARD);
        motorFR.setDirection(DcMotorEx.Direction.FORWARD);

        motorFR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        motorFR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        motorFR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if(gamepad1.x) {

                powerFL = 1.0;

            } else {

                powerFL = 0.0;

            }

            if(gamepad1.y) {

                powerFR = 1.0;

            } else {

                powerFR = 0.0;

            }

            if(gamepad1.b) {

                powerBR = 1.0;

            } else {

                powerBR = 0.0;

            }

            if(gamepad1.a) {

                powerBL = 1.0;

            } else {

                powerBL = 0.0;

            }

            telemetry.update();

            // Send calculated power to wheels
            motorFL.setPower(powerFL);
            motorFR.setPower(powerFR);
            motorBL.setPower(powerBL);
            motorBR.setPower(powerBR);

            encoderOnBR = motorBR.getCurrentPosition();
            encoderOnFR = motorFR.getCurrentPosition();
            encoderOnBL = motorBL.getCurrentPosition();
            encoderOnFL = motorFL.getCurrentPosition();

            velocityBR = motorBR.getVelocity();
            velocityFR = motorFR.getVelocity();
            velocityBL = motorBL.getVelocity();
            velocityFL = motorFL.getVelocity();

            telemetry.addData("Encoder BL", encoderOnBL);
            telemetry.addData("Encoder FL", encoderOnFL);
            telemetry.addData("Encoder BR", encoderOnBR);
            telemetry.addData("Encoder FR", encoderOnFR);

            telemetry.addData("Velocity BL", velocityBL);
            telemetry.addData("Velocity FL", velocityFL);
            telemetry.addData("Velocity BR", velocityBR);
            telemetry.addData("Velocity FR", velocityFR);


            telemetry.update();
        }
    }}
