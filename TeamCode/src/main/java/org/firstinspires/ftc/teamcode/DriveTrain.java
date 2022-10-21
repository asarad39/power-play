package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrain {

    private DcMotor motorFR = null;
    private DcMotor motorBR = null;
    private DcMotor motorFL = null;
    private DcMotor motorBL = null;

    public void initialize(OpMode op) {
        motorFR = op.hardwareMap.get(DcMotor.class, "motorFR");
        motorFL = op.hardwareMap.get(DcMotor.class, "motorFL");
        motorBR = op.hardwareMap.get(DcMotor.class, "motorBR");
        motorBL = op.hardwareMap.get(DcMotor.class, "motorBL");
    }

    public void setPower(double powerFR, double powerFL, double powerBR, double powerBL) {
        motorFR.setPower(powerFR);
        motorFL.setPower(powerFL);
        motorBR.setPower(powerBR);
        motorBL.setPower(powerBL);
    }



}
