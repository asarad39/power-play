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

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.ParallelStack;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AutoPlan {

    // Declare OpMode members
    private RobotHardware rh = null;
    private String teamColor = null; // "blue" or "red"
    private String teamSide = null; // "right" or "left"
    private boolean customSleeve = false; // true if custom sleeve is used
    private boolean track = false;
    private int numCones = 0; // number of cones to score    // 0 -> 0 cones    // 1 -> 1 cone    // 2 -> many cones
                              // has no effect

    private SeriesStack autoStack = null;

    public AutoPlan(RobotHardware rh,
                    String teamColor,
                    String teamSide,
                    boolean customSleeve,
                    int numCones,
                    boolean track) {

        this.rh = rh;
        this.teamColor = teamColor;
        this.teamSide = teamSide;
        this.customSleeve = customSleeve;
        this.track = track;
        this.numCones = numCones;
        this.autoStack = new SeriesStack(rh);
    }
    public SeriesStack getStack() {

        ArrayList<State> states = new ArrayList<State> (

                Arrays.asList(
                        new AutoTensorFlow(rh, customSleeve),
                        new AutoClawArm(rh, "open", "down"),
                        new AutoClawArm(rh, "closed", "down"),
                        new AutoClawArm(rh, "closed", "up")
                )
        );

        // states initial setup based on start position
        if (teamColor.equals("blue") && teamSide.equals("right")) {

            ParallelStack driveLift = new ParallelStack(rh);
            driveLift.createStack(new State[] {
                    new AutoSplineRR(rh, track, -36, 0, Math.toRadians(45), false),
                    new AutoMoveLift(rh, "high")
            });

            ParallelStack clawLift = new ParallelStack(rh);
            clawLift.createStack(new State[] {
                    new AutoClawArm(rh, "open", "down"),
                    new AutoMoveLift(rh, "home")
            });

            Collections.addAll(states,

                    driveLift,

                    new AutoClawArm(rh, "closed", "down"),
                    new AutoClawArm(rh, "open", "down")

            );

        } else if (teamColor.equals("red") && teamSide.equals("right")) {

        } else if (teamColor.equals("blue") && teamSide.equals("left")) {

        } else if (teamColor.equals("red") && teamSide.equals("left")) {

        }

        autoStack.stack.add(
                new AutoTFParkRR(rh, teamColor, teamSide, track)
        );

////        For testing:
//        autoStack.stack.add(
//                new AutoDriveTime(rh, 3, "forward", 0.3)
//        );

        autoStack.createStack(states);
        return autoStack;
    }
}
