package com.example.truongngoc.scratchphonecard.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * The basic camera preview
 * The app does not require a advanced camera API, so we decides to use this deprecated camera API
 * This will be hold by a {@link android.widget.FrameLayout}
 * This is bases on reference from android developer site : https://developer.android.com/guide/topics/media/camera.html#intents
 * Created by Truong Ngoc Son on 7/16/2016.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = CameraPreview.class
            .getSimpleName(); // Tag used for logging debugger

    private SurfaceHolder surfaceHolder;
    private Camera camera; // camera object

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.surfaceHolder = this.getHolder();
        // add callback listener
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS
        );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            this.camera.setPreviewDisplay(holder);
            this.camera.setDisplayOrientation(90);
            this.camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        // if (this.camera != null) {
        // this.camera.release();
        //  }
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (this.surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            this.camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            this.camera.setPreviewDisplay(this.surfaceHolder);
            this.camera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // take care of releasing the camera preview in activity
    }
}
