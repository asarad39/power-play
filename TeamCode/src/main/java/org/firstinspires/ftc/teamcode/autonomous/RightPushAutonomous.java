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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.ParallelStack;
import org.firstinspires.ftc.teamcode.stateStructure.SeriesStack;
import org.firstinspires.ftc.teamcode.stateStructure.State;


@Autonomous(name="Right Push Autonomous 2022")
public class RightPushAutonomous extends OpMode
{


    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private RobotHardware rh = new RobotHardware();
    //    private RobotHardware rh = new RobotHardware(new Pose2d(-66, -36, Math.toRadians(0)));
    //    private ParallelStack autoStack = new ParallelStack(rh);
    private SeriesStack autoStack = new SeriesStack(rh);

    @Override
    public void init() {

        rh.initialize(this);

        SeriesStack driveSequence = new SeriesStack(rh);
        State[] forDriveSequence = {
                new AutoDriveTime(rh, 5.5, "right", 0.2),
                new AutoDriveTime(rh, 5.5, "left", 0.2),
                new AutoDriveTime(rh, 3.8, "forward", 0.2),
                new AutoTFParkNoRR(rh)
        };
        driveSequence.createStack(forDriveSequence);
//
//        ParallelStack driveAndUp = new ParallelStack(rh);
//        State[] forDriveAndUp = {
//                driveSequence,
//        };
//        driveAndUp.createStack(forDriveAndUp);

        State[] forAutoStack = {
                new AutoTensorFlow(rh, true),
                new AutoNewClaw(rh, "closed", "down", false),
                driveSequence,
        };

        autoStack.createStack(forAutoStack);
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

        rh.telemetry.update();
    }
}
