package com.codefish.android.taskmanager.component.webView;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;

/**
 * Created by abedch on 1/9/2017.
 */

public class WebViewFileUploader extends WebChromeClient {

//    private static final String TAG = "WEB_CHROME_IMAGE_FILE";
//    private static final Object INPUT_FILE_REQUEST_CODE = "INPUT_FILE_REQUEST_CODE";
//
//    public boolean onShowFileChooser(
//            WebView webView, ValueCallback<Uri[]> filePathCallback,
//            WebChromeClient.FileChooserParams fileChooserParams) {
//
//        // Double check that we don't have any existing callbacks
//        if(mFilePathCallback != null) {
//            mFilePathCallback.onReceiveValue(null);
//        }
//        mFilePathCallback = filePathCallback;
//
//        // Set up the take picture intent
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//                takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.e(TAG, "Unable to create Image File", ex);
//            }
//
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
//            } else {
//                takePictureIntent = null;
//            }
//        }
//
//        // Set up the intent to get an existing image
//        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        contentSelectionIntent.setType("image/*");
//
//        // Set up the intents for the Intent chooser
//        Intent[] intentArray;
//        if(takePictureIntent != null) {
//            intentArray = new Intent[]{takePictureIntent};
//        } else {
//            intentArray = new Intent[0];
//        }
//
//        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//
//        startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
//
//        return true;
//    }

}
