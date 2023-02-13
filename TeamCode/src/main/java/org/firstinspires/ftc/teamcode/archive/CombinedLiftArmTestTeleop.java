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

package org.firstinspires.ftc.teamcode.archive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.LiftControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

// Teleop program that uses TeleopMove state to drive using robot controller

@Disabled
@TeleOp(name="Combined Test Teleop")
public class CombinedLiftArmTestTeleop extends OpMode
{

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware();

    boolean mirrored = false; // front positions
    boolean canMirror = true;
    boolean canLift = true;
    boolean canJump = true;

    int[] front = {0, 1, 1, 1};
    int[] back = {4, 5, 5, 5};
    int index = 0;

    @Override
    public void init() {

        rh.initialize(this);
    }

    @Override
    public void loop() {

        // Before testing this:
        // - Push code
        // - Add power curve to lift
        // - Find correct positions using LiftTestTeleop
        // - Adjust min and max positions for lift!
        // - Speed up claw transitions?

        // mirror button (modifier)
        if (rh.gamepad1.right_bumper) {
            if (canMirror) {
                mirrored = !mirrored;
                canMirror = false;
            }
        } else {
            canMirror = true;
        }

        int offsetSize = 2;

        // adjust lift height
        if (gamepad1.dpad_down) {
            rh.liftNew.adjustOffset(-offsetSize);
        } else if (gamepad1.dpad_up) {
            rh.liftNew.adjustOffset(offsetSize);
        }

        if (rh.gamepad1.b && index >= 0) {
            if (canLift) {

                index--;
                canLift = false;

                if(mirrored) {

                    rh.armNew.setPosition(back[index]);

                } else {

                    rh.armNew.setPosition(front[index]);
                }

            }
        }
        if (rh.gamepad1.y && index <= 3) {
            if (canLift) {

                index++;
                canLift = false;

                if(mirrored) {

                    rh.armNew.setPosition(back[index]);

                } else {

                    rh.armNew.setPosition(front[index]);
                }

            }
        } else {
            canLift = true;
        }
        if (rh.gamepad1.y || rh.gamepad1.b) {

            if (canLift) {
                if (rh.gamepad1.y) {
                    rh.liftNew.adjustPosition(LiftControl.Positions.UP);
                }
                if (rh.gamepad1.b) {
                    rh.liftNew.adjustPosition(LiftControl.Positions.DOWN);
                }
                canLift = false;
            }

        } else {
            canLift = true;
        }

        // Move to collect
        if (gamepad1.a) {

            if (mirrored) {
                rh.armNew.setPosition(5);
            } else {
                rh.armNew.setPosition(0);
            }

            rh.liftNew.setPositionsIndex(0);
        }

        // TODO: We need to make the claw vertical!
        if (gamepad1.x) {

            if (mirrored) {
                rh.armNew.setPosition(3);
            } else {
                rh.armNew.setPosition(2);
            }
        }

        // jump
        // TODO: add final click adjustment
        if (rh.gamepad1.dpad_right) {
            if (canJump) {


                rh.liftNew.adjustOffset(100);
                canJump = false;

                if (mirrored) {
                    rh.armNew.setPosition(3);
                } else {
                    rh.armNew.setPosition(2);
                }
            }
        } else {
            canJump = true;
        }

//        rh.armNew.moveClaw(gamepad1.right_bumper);
        rh.armNew.clawUpdate();
        rh.liftNew.setPosition();
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
