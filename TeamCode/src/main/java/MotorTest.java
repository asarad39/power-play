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


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;




@TeleOp(name="Motor Test", group="Iterative Opmode")
public class MotorTest extends OpMode
{

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor motorFR = null;
    private DcMotor motorFL = null;
    private DcMotor motorBR = null;
    private DcMotor motorBL = null;

    private double moveX;
    private double moveY;
    private double moveRotate;

    private double powerFR;
    private double powerFL;
    private double powerBR;
    private double powerBL;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        // Tell the driver that initialization is complete.
    }

    @Override
    public void loop() {

        movement(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);

    }

    public void move() {
        motorFR.setPower(powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(powerBR);
        motorBL.setPower(powerBL);
    }

    public void movement(double x, double y, double rotate) {
        moveX = x;
        moveY = y;
        moveRotate = rotate;

        powerFR = - moveX + moveY + moveRotate;
        powerFL =   moveX - moveY + moveRotate;
        powerBR =   moveX - moveY + moveRotate;
        powerBL = - moveX - moveY + moveRotate;

        double divisor = findPowerDivisor(powerFR,powerFL,powerBR,powerBL);

        powerFR = powerFR / divisor;
        powerFL = powerFL / divisor;
        powerBR = powerBR / divisor;
        powerBL = powerBL / divisor;

        move();
    }

    public double findPowerDivisor(double a, double b, double c, double d) {
        double maxPower = Math.abs(a);
        if(Math.abs(b)>maxPower) maxPower = Math.abs(b);
        if(Math.abs(c)>maxPower) maxPower = Math.abs(c);
        if(Math.abs(d)>maxPower) maxPower = Math.abs(d);
        return maxPower;
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
