package org.firstinspires.ftc.teamcode.hardware;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

// Represents the lift of the robot in code
public class ColorSensor {

    // The system contains for DC motors
    private NormalizedColorSensor colorSensor = null;

    private float gain = 0;
    float[] hsvValues = null;

    private NormalizedRGBA colors = null;

    public void initialize(OpMode op) {

        gain = 8.85f;

        // Once per loop, we will update this hsvValues array. The first element (0) will contain the
        // hue, the second element (1) will contain the saturation, and the third element (2) will
        // contain the value.
        hsvValues = new float[3];

        // Get a reference to our sensor object. It's recommended to use NormalizedColorSensor over
        // ColorSensor, because NormalizedColorSensor consistently gives values between 0 and 1, while
        // the values you get from ColorSensor are dependent on the specific sensor you're using.
        colorSensor = op.hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        colorSensor.setGain(gain);
    }

    public double getRed() {

        colors = colorSensor.getNormalizedColors();
        return (double) colors.red;
    }

    public double getGreen() {

        colors = colorSensor.getNormalizedColors();
        return (double) colors.green;
    }

    public double getBlue() {

        colors = colorSensor.getNormalizedColors();
        return (double) colors.blue;
    }

//
//    // Get the normalized colors from the sensor
//    Color.colorToHSV(colors.toColor(), hsvValues);
//
//    /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
//     * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
//     * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
//     * for an explanation of HSV color. */
//
//    // Update the hsvValues array by passing it to Color.colorToHSV()
//
//
//            telemetry.addLine()
//                    .addData("Red", "%.3f", colors.red)
//                    .addData("Green", "%.3f", colors.green)
//                    .addData("Blue", "%.3f", colors.blue);
//            telemetry.addLine()
//                    .addData("Hue", "%.3f", hsvValues[0])
//                    .addData("Saturation", "%.3f", hsvValues[1])
//                    .addData("Value", "%.3f", hsvValues[2]);
//            telemetry.addData("Alpha", "%.3f", colors.alpha);
//
//    /* If this color sensor also has a distance sensor, display the measured distance.
//     * Note that the reported distance is only useful at very close range, and is impacted by
//     * ambient light and surface reflectivity. */
//            if (colorSensor instanceof DistanceSensor) {
//        telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
//    }
//
//            telemetry.update();


}
