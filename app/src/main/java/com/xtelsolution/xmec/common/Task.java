package com.xtelsolution.xmec.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.xtelsolution.xmec.dialog.TaskUploadDialog;
import com.xtelsolution.xmec.listener.UploadFileListener;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by vivhp on 07/08/2017
 */

public class Task {

    private static final String TAG = "Task";
    private static TaskUploadDialog uploadDialog;

    private static void postImageToServer(final File file, final Activity context, final UploadFileListener uploadFileListener) {
        uploadDialog = new TaskUploadDialog(context);
        uploadDialog.show();
        Log.e("tb_up_upload", "dang up: " + file.getPath());
        Ion.with(context)
                .load(Constant.SERVER_UPLOAD)
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(final long downloaded, final long total) {
                        Log.e(TAG, "onProgress: " + downloaded + " / " + total);
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadDialog.setProgressBarTotal((int) total);
                                uploadDialog.setTextCount(String.valueOf(downloaded));
                            }
                        });
                    }
                })
                .setMultipartFile("fileUpload", file)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Log.e("tb_up_error", e.toString());
                            uploadDialog.dismis();
                            uploadFileListener.onError("");
                        } else {
                            uploadDialog.dismis();
                            uploadFileListener.onSuccess(result);
                        }
                    }
                });
    }

    public static class ConvertImage extends AsyncTask<Bitmap, Void, File> {
        private ProgressDialog dialogProgressBar;
        private Activity context;
        private boolean isBigImage;
        private UploadFileListener uploadFileListener;

        public ConvertImage(Activity context, boolean isBigImage, UploadFileListener uploadFileListener) {
            this.context = context;
            this.isBigImage = isBigImage;
            this.uploadFileListener = uploadFileListener;
        }

        @Override
        public File doInBackground(Bitmap... params) {
            try {
                Log.e("tb_uri", "null k " + params[0].getWidth());

                try {
                    Bitmap newBitmap;

                    if (isBigImage)
                        newBitmap = getBitmapBigSize(params[0]);
                    else
                        newBitmap = getBitmapSmallSize(params[0]);

                    if (newBitmap != null) {
                        return saveImageFile(newBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tb_image_error", e.toString());
            }
            return null;
        }

        @Override
        public void onPostExecute(File file) {
            super.onPostExecute(file);

            if (file != null) {
                postImageToServer(file, context, uploadFileListener);
            } else {
//                dialogProgressBar.hideProgressBar();
                uploadFileListener.onError("error");
            }
        }

        private Bitmap getBitmapSmallSize(Bitmap bitmap) {
            try {
                double width = bitmap.getWidth(), height = bitmap.getHeight();
                Log.e("tb_bitmap_old", width + "        " + height);

                if (width > 200 || height > 240) {
                    int new_width, new_height;
                    while (width > 200 || height > 240) {
                        width = width * 0.8;
                        height = height * 0.8;
                        Log.e("tb_bitmap_run", width + "       " + height);
                    }
                    new_width = (int) width;
                    new_height = (int) height;

                    Log.e("tb_bitmap_new", new_width + "         " + new_height);
                    return Bitmap.createScaledBitmap(bitmap, new_width, new_height, false);
                }

                return bitmap;
            } catch (Exception e) {
                Log.e("tb_error_image", e.toString());
                e.printStackTrace();
            }
            return null;
        }

        private Bitmap getBitmapBigSize(Bitmap bitmap) {
            try {
                double width = bitmap.getWidth(), height = bitmap.getHeight();
                Log.e("tb_bitmap_old", width + "        " + height);

                if (width > 1280 || height > 1280) {
                    int new_width, new_height;
                    while (width > 1280 || height > 1280) {
                        width = width * 0.8;
                        height = height * 0.8;
                        Log.e("tb_bitmap_run", width + "       " + height);
                    }
                    new_width = (int) width;
                    new_height = (int) height;

                    Log.e("tb_bitmap_new", new_width + "         " + new_height);
                    return Bitmap.createScaledBitmap(bitmap, new_width, new_height, false);
                }

                return bitmap;
            } catch (Exception e) {
                Log.e("tb_error_image", e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private File saveImageFile(Bitmap bitmap) {
            try {
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vParking";
                File dir = new File(file_path);

                if (!dir.exists())
                    dir.mkdirs();

                File file = new File(dir, System.currentTimeMillis() + ".png");
                FileOutputStream fOut = new FileOutputStream(file);

                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                }

                fOut.flush();
                fOut.close();

                return file;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}