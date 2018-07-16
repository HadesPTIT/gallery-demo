package com.framgia.gallerydemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final String TAG = GalleryAdapter.class.getSimpleName();
    private static final String FILE_PREFIX = "file://";
    private List<String> mLists;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mImageWidth, mImageHeight;

    public GalleryAdapter(Context context, List<String> lists) {
        mLists = lists;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mImageWidth = getWidth();
        mImageHeight = getWidth();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        String path = mLists.get(position);
        Picasso.get().load(FILE_PREFIX + path).noFade()
                .noPlaceholder()
                .resize(mImageWidth, mImageHeight)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    private int getWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels / 2;
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;

        GalleryViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_item);
        }
    }
}
