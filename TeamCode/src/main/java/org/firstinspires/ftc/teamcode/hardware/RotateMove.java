//package org.firstinspires.ftc.teamcode.hardware;
//
//import org.firstinspires.ftc.teamcode.stateStructure.State;
//
//public class RotateMove implements State {
//
//    RobotHardware rh = null;
//    private double target = 1.0;
//    private boolean setStart = false;
//
//
//    /**
//     * Servo positions:
//     * rotate:
//     *      0 = for the front
//     *      1 = for the back
//     **/
//
//    public RotateMove(RobotHardware rh, double target) {
//        this.rh = rh;
//
//        this.target = target;
//    }
//
//    public RotateMove(RobotHardware rh, double target, boolean setStart) {
//        this.rh = rh;
//        this.target = target;
//        this.setStart = setStart;
//    }
//
//    @Override
//    public void update() {
//        rh.armNew.rotate.move();
//    }
//
//    @Override
//    public void init() {
//        rh.armNew.rotate.setTargetPosition(target);
//        if (setStart) {
//            rh.armNew.rotate.setCurrentPosition(target + 0.01);
//        }
//    }
//
//    @Override
//    public boolean getIsDone() {
//        return !(rh.armNew.rotate.isMoving());
//    }
//}
