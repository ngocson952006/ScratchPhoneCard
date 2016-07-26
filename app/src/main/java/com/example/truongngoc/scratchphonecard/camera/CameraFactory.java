package com.example.truongngoc.scratchphonecard.camera;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by Truong Ngoc Son on 7/16/2016.
 */
public class CameraFactory {
    private static final String TAG = CameraFactory.class.getSimpleName(); // tag

    /**
     * private constructor to prevent from being instantiated
     */
    private static Camera cameraObject;

    private CameraFactory() {

    }

    /**
     *
     * @return
     */
    public static Camera getInstance() {
        cameraObject = null;
        try {
            // try to create camera
            cameraObject = Camera.open();
        } catch (Exception e) {
            Log.e(TAG, "Camera is not available for this device. Error message : " + e.getMessage());
        }

        // return the camera object
        return cameraObject;
    }
}
