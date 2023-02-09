package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class LiftControl {

    RobotHardware rh = null;

    private TouchSensor touch = null;

    //    PID liftPID = new PID(5700); // for new robot
    public DcMotor left;
    public DcMotor right;

    boolean homing = true;

    private double maxPower = 0.8;

//    final int[] positions = {0, 100, 200, 300, 400};
    
    // low to high
    final int[] positions = {
            -18,
            243, // (int) (1460.0 / (19.2 / 3.7)),
            552, // (int) (3310.0 / (19.2 / 3.7)),
            829, // (int) (4975.0 / (19.2 / 3.7)),
            };
// TODO: scale motor powers before we change clicks, the motors are not currently reaching targets


    final int minPosition = -18;

//    final int maxPosition = 500;
    final int maxPosition = (int) (4975.0 / (19.2 / 3.7));

    int posIndex;
    int offset;
    private Telemetry telemetry;

    boolean canLift = true;

    public void initialize(OpMode op, RobotHardware rh) {
        // Get telemetry object from opMode for debugging
        this.telemetry = op.telemetry;
        this.rh = rh;

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

    public boolean isMoving() {
        return !(left.isBusy() || right.isBusy());
        // TODO check if position is within range
        // isBusy
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
        // TODO: Calculate power before setting motor speed - pwer corve

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

            left.setPower(0.04);
            right.setPower(0.04);

            newPosition = maxPosition;
            if (getTouch()) {
                resetEncoders();
                homing = false;
            }

        }

//        double power = Mathematics.getLogisticCurve(maxPower, left.getCurrentPosition(), newPosition, .015);
        double power = 0.2;

        left.setPower(power);
        right.setPower(power);

        telemetry.addData("posIndex", posIndex);
        telemetry.addData("final goal position", newPosition);
        telemetry.addData("clicks", left.getCurrentPosition());

        telemetry.addData("touching", getTouch());
        telemetry.addData("Homing", homing);

        left.setTargetPosition(newPosition);
        right.setTargetPosition(newPosition);
    }

    public int getPosition() {
        return posIndex;
    }

    public static enum Positions {
        UP,
        DOWN
    }
    // liftcontrol.adjustPosition(LiftControl.Positions.UP)

    public void adjustPosition(Positions p) {

        int d = 0;

        switch(p) {

            case UP:
                d = 1;
                break;

            case DOWN:
                d = -1;
                break;
        }

        posIndex = (posIndex + d + positions.length) % positions.length;
        telemetry.addData("adjusting", d);
        offset = 0;
        this.setPosition();
    }

    public void adjustOffset(int o) {
        offset += o;

        this.setPosition();
    }

    public void setPositionsIndex(int index) {

        posIndex = index;
        offset = 0;
        this.setPosition();
    }


//    public void setLiftTarget() {
//        liftPID.setTargetPosition(positions[posIndex] + offset);
//        rh.setLiftTarget(liftPID.getTargetPosition());
//    }

}
