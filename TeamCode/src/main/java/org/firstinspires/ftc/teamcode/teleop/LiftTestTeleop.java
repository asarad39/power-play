/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

// Teleop program that uses TeleopMove state to drive using robot controller

@TeleOp(name="Lift Test Teleop")
public class LiftTestTeleop extends OpMode
{

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware();
//    private ParallelStack teleStack = new ParallelStack(rh);

    @Override
    public void init() {

        rh.initialize(this);
    }

    @Override
    public void loop() {

//        if (gamepad1.a) {
//            rh.armNew.setPosition(0);
//        }
//        if (gamepad1.b) {
//            rh.armNew.setPosition(1);
//        }
        if (gamepad1.x) {
//            rh.armNew.flip.setTargetPosition(1);
        } else if (gamepad1.y) {
//            rh.armNew.flip.setTargetPosition(0);
        } else {
//            rh.armNew.flip.setTargetPosition(0.5);
        }

//        rh.armNew.arm.setTargetPosition(0.48);
//        rh.armNew.flip.move();
//        rh.armNew.arm.move();


//        rh.armNew.armUpdate();

//        // offset
//        int offsetSize = 2;
//
//        if (gamepad1.dpad_down) {
//            rh.liftNew.adjustOffset(-offsetSize);
//        } else if (gamepad1.dpad_up) {
//            rh.liftNew.adjustOffset(offsetSize);
//        } else if (gamepad1.a) {
////            rh.liftNew.adjustPosition(LiftControl.Positions.DOWN);
//        }
//
//        rh.liftNew.adjustPosition(LiftControl.Positions.UP);
//        rh.liftNew.setPosition();
    }


    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void stop() {
    }
    @Override
    public void init_loop() {
    }
    @Override
    public void start() {
        runtime.reset();
    }
}
