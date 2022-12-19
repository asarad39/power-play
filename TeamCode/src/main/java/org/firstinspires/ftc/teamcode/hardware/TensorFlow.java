package org.firstinspires.ftc.teamcode.hardware;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.robot.Robot;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2022-2023 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine which image is being presented to the robot.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

public class TensorFlow {

    private static boolean custom = false; // TODO: change here!

    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    private static final String TFOD_MODEL_FILE  =
            "/sdcard/FIRST/tflitemodels/Felix_PowerPlay.tflite";

    private static String[] LABELS = null;

    private static final String VUFORIA_KEY =
            "AU4vjhT/////AAABmaAOaCEHnkkmsyVTf1phAM9YBREuY5WuM3Hg0fnl42nNCoSl+x7mRqdgpTT8Pwa8" +
                    "PyDX1qoTMnA7CNJXEm+KMIBW21wArWKoFWAaUCcQhHzNYwq5lTwDU+DHLhoZxFnx0upTxEwS" +
                    "AUoYfTMJ7xyWXRs0tuKHTGYjcVgb8rXWzCUu/P3No660ADve1RKvWlE2NYnzemzritTBYTazz" +
                    "23XObdQelJgR84b5lv8aSMdOX6M0B/uqJTAgtBbnIHVp/lBNnAOETfKbbtE54gd694BijkFNs" +
                    "lKUlWzexeTOLgH+51zpPhDabvZksMDqqmCNNjvAVlFuEGdHeG4lUK2wOb3EP/bHTehAgbTgJj" +
                    "8kD/mQ8ay";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public void initialize(OpMode op) {

        if (custom) {

            LABELS = new String[]{"HHS Logo side", "Red Robot Side", "Green Bug Side"};

        } else {

            LABELS = new String[]{"1 Bolt", "2 Bulb", "3 Panel"};
        }

        initVuforia(op);
        initTfod(op);

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }
    }

    public static boolean getCustom() {
        return custom;
    }


    public List<Recognition> getRecognitions() throws Exception {

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            return updatedRecognitions;

        } else {

            throw new Exception("no list yet");
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia(OpMode op) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = op.hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod(OpMode op) {
        int tfodMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        if (custom) {

            tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);

        } else {

            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        }
    }
}
