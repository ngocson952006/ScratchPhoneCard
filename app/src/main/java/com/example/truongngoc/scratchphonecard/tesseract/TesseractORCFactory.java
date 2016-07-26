package com.example.truongngoc.scratchphonecard.tesseract;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Truong Ngoc Son on 6/26/2016.
 */
public class TesseractORCFactory {
    private static final String TAG = TesseractORCFactory.class.getSimpleName();

    private TesseractORCFactory() {
        // prevent from being instantiated
    }

    /**
     * Convert {@link Bitmap} to {@link String}
     * Note: make sure that we had traineddata file (in this case eng.traninedata) already in device's storage
     *
     * @param bitmap
     * @return
     */
    public static String convert(Context context, Bitmap bitmap) {
        // first check that if the tessdata directory existed or not
        File tessdataDirectory = new File(context.getExternalFilesDir(Environment.MEDIA_MOUNTED) + "/tessdata");
        if (!tessdataDirectory.exists()) {
            Log.e(TAG, "tessdata does not exist , start to create !!");
            try {
                createTessDataRepository(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TessBaseAPI tessBaseAPI = new TessBaseAPI(); // instantiate a tess base API object
        // we save eng.traindata file in assets android
        // resource directory,
        tessBaseAPI.init(context.getExternalFilesDir(Environment.MEDIA_MOUNTED).toString(), "eng");
        tessBaseAPI.setImage(bitmap);
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890"); // we simply need to convert to number
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");
        tessBaseAPI.setPageSegMode(TessBaseAPI.OEM_TESSERACT_CUBE_COMBINED); // set mode for the process
        String recognizedText = tessBaseAPI.getUTF8Text();
        // end the tess api
        tessBaseAPI.end();
        return recognizedText;
    }

    //    /**
//     * Adjust the bitmap before it is converted to text
//     *
//     * @param path : the path of the photo file to be converted
//     * @return
//     */
//    private static Bitmap adjuctBitmap(String path) throws IOException {
//        ExifInterface exifInterface = new ExifInterface("");
//
//        return null;
//    }

    /**
     * At the begging time of app, we need to create and
     * @param context
     * @return
     * @throws IOException
     */
    public static boolean createTessDataRepository(Context context) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.MEDIA_MOUNTED) + "/tessdata");
        if (!file.mkdir()) {
            Log.e(TAG, "Can not make tessdata directory");
            return false;
        }
        // start to copy eng.traindedata file into this subdirectory
        File englishTrainedDataFile = new File(context.getExternalFilesDir(Environment.MEDIA_MOUNTED) + "/tessdata/eng.traineddata");
        if (!englishTrainedDataFile.createNewFile()) {
            Log.e(TAG, "Can not make tessdata directory");
            return false;
        }
        AssetManager assetManager = context.getAssets(); // get asset manager
        InputStream inputStream = assetManager.open("tessdata/eng.traineddata");
        OutputStream outputStream = new FileOutputStream(englishTrainedDataFile);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, length);
        }
        //close all streams
        inputStream.close();
        outputStream.close();
        return true;
    }

}
