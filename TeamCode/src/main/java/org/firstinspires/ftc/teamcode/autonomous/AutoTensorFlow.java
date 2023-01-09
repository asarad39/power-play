package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.stateStructure.State;

// State for driving using game controller
public class AutoTensorFlow implements State {

    RobotHardware rh = null;

    private double startTime;
    private boolean custom;

    private int oneCount = 0;
    private int twoCount = 0;
    private int threeCount = 0;

    public AutoTensorFlow(RobotHardware rh, boolean custom) {

        this.rh = rh;
        this.custom = custom;
    }

    public void init() {
        startTime = rh.time.seconds();
        rh.setCustom(this.custom);
    }

    public boolean getIsDone() {

        if (rh.time.seconds() > startTime + 3) {

            if (oneCount >= twoCount && oneCount >= threeCount) {
                RobotHardware.setSleeve(1);
            } else if (twoCount >= threeCount) {
                RobotHardware.setSleeve(2);
            } else {
                RobotHardware.setSleeve(3);
            }

            return true;

        } else {
            return false;
        }
    }

    public void update() {

//        rh.telemetry.addData("custom", rh.setCustom());

        for (int i = 0; i < 1000; i++) {

            try {
                for (Recognition recognition : rh.getRecognitions()) {

                    if (custom) {

                        if (recognition.getLabel().equals("HHS Logo side")) {

                            oneCount++;

                        } else if (recognition.getLabel().equals("Red Robot Side")) {

                            twoCount++;

                        } else if (recognition.getLabel().equals("Green Bug Side")) {

                            threeCount++;
                        }

                    } else {

                        if (recognition.getLabel().equals("1 Bolt")) {

                            oneCount++;

                        } else if (recognition.getLabel().equals("2 Bulb")) {

                            twoCount++;

                        } else if (recognition.getLabel().equals("3 Panel")) {

                            threeCount++;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}