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

package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

@Autonomous(name="Auto layout", group="Auto")
public class AutoPlan extends OpMode {

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware(new Pose2d(-66, -36, Math.toRadians(0)));
    private SeriesStack autoStack = new SeriesStack(rh);

    private String teamColor = "blue"; // "blue" or "red"
    private String teamSide = "right"; // "right" or "left"
    private boolean customSleeve = false; // true if custom sleeve is used
    private int numCones = 1; // number of cones to score    // 0 -> 0 cones    // 1 -> 1 cone    // 2 -> many cones
                              // has no effect

    @Override
    public void init() {

        rh.initialize(this);

        State[] initialStates = {
//                new AutoTensorFlow(rh, customSleeve),
                new AutoClawArm(rh, "open", "down"),
                new AutoClawArm(rh, "closed", "down"),
                new AutoClawArm(rh, "closed", "up"),
        };

        autoStack.createStack(initialStates);

        State[] states = {};
        // states initial setup based on start position
        if (teamColor.equals("blue") && teamSide.equals("right")) {
            states = new State[]{
                    /*parallel*/
                    new AutoSpline(rh, true, -36, 0, Math.toRadians(45), false),
                    new AutoMoveLift(rh, "high"),
                    /*series*/
                    new AutoSpline(rh, true, -33, 0, Math.toRadians(45), false),
                    new AutoClawArm(rh, "open", "up"),
                    /*parallel*/
                    new AutoSpline(rh, true, -36, 0, Math.toRadians(45), false),
                    new AutoClawArm(rh, "open", "down"),
                    new AutoMoveLift(rh, "home"),
            };

            autoStack.createStack(states);

        } else if (teamColor.equals("red") && teamSide.equals("right")) {

        } else if (teamColor.equals("blue") && teamSide.equals("left")) {

        } else if (teamColor.equals("red") && teamSide.equals("left")) {

        }

        autoStack.stack.add(new AutoTFPark(rh, teamColor, teamSide, true));

        autoStack.init();

        // Tell the driver that initialization is complete.
        rh.telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        rh.telemetry.addData("autoStack", autoStack.getIsDone());
        rh.telemetry.addData("sleeve", RobotHardware.getSleeve());

        if (!autoStack.getIsDone()) {
            autoStack.update();
        }
    }
}
