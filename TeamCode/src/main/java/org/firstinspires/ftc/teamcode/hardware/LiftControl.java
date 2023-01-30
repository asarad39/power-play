package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class LiftControl {

//    RobotHardware rh;

    private TouchSensor touch = null;

    //    PID liftPID = new PID(5700); // for new robot
    public DcMotor left;
    public DcMotor right;

    boolean homing = true;

    final int[] positions = {0, 100, 200, 300, 400};
    final int minPosition = 0;
    final int maxPosition = 500;
    int posIndex;
    int offset;
    private Telemetry telemetry;



    public void initialize(OpMode op) {
        // Get telemetry object from opMode for debugging
        this.telemetry = op.telemetry;

        posIndex = 0;
        offset = 0;
        // get DcMotors
        // set encoders
        left = op.hardwareMap.get(DcMotor.class, "liftMotorLeft");
        right = op.hardwareMap.get(DcMotor.class, "liftMotorRight");

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setTargetPosition(0);
        right.setTargetPosition(0);
        resetEncoders();

        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.FORWARD);


        touch = op.hardwareMap.get(TouchSensor.class, "touch");
    }

    public boolean getTouch() {
        return touch.isPressed();
    }

    public void resetEncoders() {

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPosition() {
        // TODO: Calculate power before setting motor speed
        left.setPower(0.1);
        right.setPower(0.1);

        // Calculate max/min position
        int newPosition = positions[posIndex] + offset;

        telemetry.addData("naive final goal position", newPosition);

        // contain position inside the range
        if (newPosition < minPosition) {
            newPosition = minPosition;
        }
        if (newPosition > maxPosition) {
            newPosition = maxPosition;
        }


        if (homing) {
            newPosition = maxPosition;
            if (getTouch()) {
                resetEncoders();
                homing = false;
            }
        }
        telemetry.addData("final goal position", newPosition);
        telemetry.addData("touching", getTouch());
        telemetry.addData("Homing", homing);


        left.setTargetPosition(newPosition);
        right.setTargetPosition(newPosition);
    }

    public static enum Positions {
        UP,
        DOWN
    }
    // liftcontrol.adjustPosition(LiftControl.Positions.UP)
    public void adjustPosition(Positions p) {
        int d = 0;
        switch(p) {
            case UP: d = 1;
            case DOWN: d = -1;
        }
        // d is either 1 or -1
        posIndex = (posIndex + d) % positions.length;
        telemetry.addData("adjusting", d);
        offset = 0;
        this.setPosition();
    }

    public void adjustOffset(int o) {
        offset += o;

        this.setPosition();
    }



//    public void setLiftTarget() {
//        liftPID.setTargetPosition(positions[posIndex] + offset);
//        rh.setLiftTarget(liftPID.getTargetPosition());
//    }

}
