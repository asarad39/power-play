package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

public class AutoSeekColor implements State {

    RobotHardware rh = null;
    private double startTime;

    private String colorString = null;

    private double red = 0;
    private double blue = 0;
    private double green = 0;
    private double distance = 0;

    private boolean isDone = false;

    private double seconds = 5;

    public AutoSeekColor(RobotHardware rh, String colorString) {
        this.rh = rh;
        this.colorString = colorString;
    }

    public void init() {
        startTime = rh.time.seconds();
    }

    public boolean getIsDone() {

//        rh.telemetry.addData("cone?", cone);

        return getCone();
//        return false;
    }

    public void update() {

        red = rh.getRed();
        blue = rh.getBlue();
        green = rh.getGreen();
        distance = rh.getDistance();

//        // TODO: uncomment
//        if (rh.time.seconds() > startTime + seconds) {
//            isDone = true;
//        }

        rh.telemetry.addLine();
        rh.telemetry.addData("red", red);
        rh.telemetry.addData("blue", blue);
        rh.telemetry.addData("green", green);
        rh.telemetry.addLine();
    }

    public boolean getCone() {

        boolean test1 = false;
        boolean test2 = false;
        boolean testd = false;

        if (colorString.equals("red")) {

            test1 = (red >= 1.9 * blue);
            test2 = (red >= 2.0 * green);

        } else if (colorString.equals("blue")) {

            test1 = (blue >= 2.0 * red);
            test2 = (blue >= 1.3 * green);

        } else {

            throw new IllegalArgumentException("nonexistent color name");
        }

        testd = (distance > 0.0) &&
                (distance <= 10.0);

        rh.telemetry.addLine();
        rh.telemetry.addData("test1", test1);
        rh.telemetry.addData("test2", test2);
        rh.telemetry.addData("testd", testd);
        rh.telemetry.addLine();

        return (test1 && test2 && testd);
    }
}