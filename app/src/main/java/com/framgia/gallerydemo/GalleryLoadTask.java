package com.framgia.gallerydemo;

import android.os.AsyncTask;

public class GalleryLoadTask extends AsyncTask<String, Void, Void> {

    private OnTaskFinishedListener mListener;
    private FileUtil mFileUtil;

    public GalleryLoadTask(OnTaskFinishedListener listener, String[] extensions) {
        mListener = listener;
        mFileUtil = new FileUtil(extensions);
    }

    @Override
    protected Void doInBackground(String... strings) {
        mFileUtil.loadAllImages(strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.onTaskFinished(mFileUtil.getListPaths());
    }
}
