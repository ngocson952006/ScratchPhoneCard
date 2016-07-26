package com.example.truongngoc.scratchphonecard;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.truongngoc.scratchphonecard.camera.CameraFactory;
import com.example.truongngoc.scratchphonecard.camera.CameraPreview;
import com.example.truongngoc.scratchphonecard.domain.AppConstants;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = CameraActivity.class.getSimpleName(); // tag used for debugging

    private Camera camera; // camera object
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_camera);
        // get support action bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

        {
            ActionBar actionBar = this.getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }


        // Because camera permission is dangerous permission at Android 6.0, so we need to get
        // permission at runtime
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                // GET CAMERA PERMISSION
//                if (ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this, Manifest.permission.CAMERA)) {
//                    // show dialog to user
//                    new CameraPermissionConfirmationDialog().show(getFragmentManager(), "camera permission");
//                } else {
//                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, AppConstants.REQUEST_CAMERA_PERMISSION);
//                }
//            } else {
//                this.setViewContent();
//            }
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                // GER STORAGE PERMISSION
//                if (ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    new StoragePermissionConfirmationDialog().show(getFragmentManager(), "storage permission");
//                } else {
//                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission_group.STORAGE},
//                            AppConstants.REQUEST_STORAGE_PERMISSION);
//                }
//            } else {
//                this.setViewContent();
//            }
//        } else { // if lower Android version , we directly inflate the layout due to we've already listed camera
//            // permission in manifest.xml file before
//
//            // so we start to set up view content
//            this.setViewContent();
//
//        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // analyze the requestCode
//        switch (requestCode) {
//            case AppConstants.REQUEST_CAMERA_PERMISSION:
//            case AppConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // set view content here
//                    this.setViewContent();
//
//                } else {
//                    // recommend user to user the alternative feature
//
//                    this.finish();
//                }
//                break;
//            default:
//                break;
//        }
//    }

//    private void setViewContent() {
//        this.setContentView(R.layout.fragment_camera2_basic);
//
//        this.camera = CameraFactory.getInstance(); // get instance of device's camera object
//        // make sure we have a valid camera
//        if (this.camera != null) {
//            //
//            FrameLayout cameraPreview = (FrameLayout) this.findViewById(R.id.camera_preview);
//            // resize the view of camera for fit the device's screen size
//            // instance the camera preview object
//            this.cameraPreview = new CameraPreview(this, this.camera);
//            // add view into the layout
//            cameraPreview.addView(this.cameraPreview);
//
//            final ImageButton captureButton = (ImageButton) this.findViewById(R.id.image_button_picture);
//            // make sure we have valid image button
//            if (captureButton != null) {
//                captureButton.setOnClickListener(this);
//            }
//        }
//
//    }
//
//    /**
//     * Handle when user interact with view components
//     *
//     * @param v
//     */
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//        }
//    }
//
//    @Override
//    public void onPictureTaken(byte[] data, Camera camera) {
//
//    }
//
//    @Override
//    public void onShutter() {
//
//    }
//
//
//    /* DIALOG BUILDER */
//
//    /**
//     * Shows an error message dialog.
//     */
//    public static class ErrorDialog extends android.app.DialogFragment {
//
//        private static final String ARG_MESSAGE = "message";
//
//        public static ErrorDialog newInstance(String message) {
//            ErrorDialog dialog = new ErrorDialog();
//            Bundle args = new Bundle();
//            args.putString(ARG_MESSAGE, message);
//            dialog.setArguments(args);
//            return dialog;
//        }
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Activity activity = getActivity();
//            return new AlertDialog.Builder(activity)
//                    .setMessage(getArguments().getString(ARG_MESSAGE))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            activity.finish(); // finish the activity if the permission is denied from use
//                        }
//                    })
//                    .create();
//        }
//
//    }
//
//    /**
//     * Shows OK/Cancel confirmation dialog about camera permission.
//     */
//    public static class CameraPermissionConfirmationDialog extends android.app.DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Activity activity
//                    = getActivity();
//            return new AlertDialog.Builder(getActivity())
//                    .setMessage(R.string.request_camera_permission)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(activity,
//                                    new String[]{Manifest.permission.CAMERA},
//                                    AppConstants.REQUEST_CAMERA_PERMISSION);
//
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (activity != null) {
//                                        activity.finish(); // finish the activity if the permission is denied from use
//                                    }
//                                }
//                            })
//                    .create();
//        }
//    }
//
//    /**
//     * Shows OK/Cancel confirmation dialog about camera permission.
//     */
//    public static class StoragePermissionConfirmationDialog extends android.app.DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Activity activity
//                    = getActivity();
//            return new AlertDialog.Builder(getActivity())
//                    .setMessage(R.string.request_camera_permission)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission_group.STORAGE},
//                                    AppConstants.REQUEST_STORAGE_PERMISSION);
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    if (activity != null) {
//                                        activity.finish();
//                                    }
//                                }
//                            })
//                    .create();
//        }
//    }

}