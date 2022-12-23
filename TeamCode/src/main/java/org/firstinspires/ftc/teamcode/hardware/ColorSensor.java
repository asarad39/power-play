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

public class ColorSensor {

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

    public double getAlpha() {

        colors = colorSensor.getNormalizedColors();
        return (double) colors.alpha;
    }

    public float[] updateHSV() {

        // Hue = hsvValues[0]
        // Saturation = hsvValues[1]
        // Value = hsvValues[2]

        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues;
    }

    public double getDistance() {

        return ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM);
    }
}
