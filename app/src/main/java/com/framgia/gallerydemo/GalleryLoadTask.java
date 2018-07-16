package com.framgia.gallerydemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;

public class GalleryLoadTask extends AsyncTask<String, Void, ArrayList<String>> {

    private static final String TAG = GalleryLoadTask.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private Context mCtx;
    private String[] mExtensions;
    private ArrayList<String> mListPaths;
    private OnTaskFinishedListener mListener;
    private ProgressDialog mDialog;

    public GalleryLoadTask(Context ctx) {
        mCtx = ctx;
        mListener = (OnTaskFinishedListener) mCtx;
        mListPaths = new ArrayList<>();
        mExtensions = mCtx.getResources().getStringArray(R.array.array_extensions);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        setupLoading();
    }

    /**
     * Load image from ExternalStorageDirectory
     *
     * @param strings
     */
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        loadAllImages(strings[0]);
        return mListPaths;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mListener.onTaskFinished(strings);
    }

    private void setupLoading() {
        mDialog = new ProgressDialog(mCtx);
        mDialog.setCancelable(false);
        mDialog.setMessage(mCtx.getString(R.string.msg_loading));
        mDialog.show();
    }

    /**
     * @param rootFolder
     */
    private void loadAllImages(String rootFolder) {
        File file = new File(rootFolder);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        loadAllImages(f.getAbsolutePath());
                    } else {
                        for (String extension : mExtensions) {
                            if (f.getAbsolutePath().endsWith(extension)) {
                                mListPaths.add(f.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }
}
