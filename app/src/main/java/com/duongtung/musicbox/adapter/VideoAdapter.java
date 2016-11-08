package com.duongtung.musicbox.adapter;

/**
 * Created by duong.thanh.tung on 11/9/2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duongtung.musicbox.R;
import com.duongtung.musicbox.base.BaseRecyclerViewAdapter;
import com.duongtung.musicbox.utils.MusicItem;
import com.duongtung.musicbox.utils.VideoItem;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Adapter for list of tracks.
 */
public class VideoAdapter extends BaseRecyclerViewAdapter<VideoItem, VideoAdapter.VideoViewHolder> {

    private final CropCircleTransformation cropCircleTransformation;

    public VideoAdapter(@NonNull Context context) {
        super(context);
        cropCircleTransformation = new CropCircleTransformation(context);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_music, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoItem item = getItem(position);
        holder.title.setText(getFilter().highlightFilteredSubstring(item.title()));
        holder.duration.setText(convertDuration(item.duration()));
        Glide.with(getContext())
                .load(item.albumArtUri())
                .asBitmap()
                .transform(cropCircleTransformation)
                .placeholder(R.drawable.aw_ic_default_album)
                .error(R.drawable.aw_ic_default_album)
                .into(holder.albumCover);
    }

    private String convertDuration(long durationInMs) {
        long durationInSeconds = durationInMs / 1000;
        long seconds = durationInSeconds % 60;
        long minutes = (durationInSeconds % 3600) / 60;
        long hours = durationInSeconds / 3600;
        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.artist)
        TextView artist;

        @InjectView(R.id.album)
        TextView album;

        @InjectView(R.id.duration)
        TextView duration;

        @InjectView(R.id.album_cover)
        ImageView albumCover;


        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
