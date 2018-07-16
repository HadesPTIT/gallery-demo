package com.framgia.gallerydemo;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTaskFinishedListener {

    private static final int REQUEST_CODE_STORAGE = 1;
    private static final int SPAN_COUNT = 2;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
            return;
        }
        new GalleryLoadTask(this).execute(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new GalleryLoadTask(this).execute(Environment.getExternalStorageDirectory().getAbsolutePath());
                } else {
                    requestPermission();
                }
                break;
        }
    }

    @Override
    public void onTaskFinished(ArrayList<String> listPaths) {
        RecyclerView recyclerGallery = findViewById(R.id.recycler_view_gallery);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, listPaths);

        recyclerGallery.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerGallery.setItemAnimator(new DefaultItemAnimator());
        recyclerGallery.setAdapter(galleryAdapter);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            new GalleryLoadTask(this).execute(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.title_permission_needed)
                    .setMessage(R.string.msg_permission_rationale)
                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_STORAGE);
                        }
                    }).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
        }
    }

}
