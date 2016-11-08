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

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Adapter for list of tracks.
 */
public class MusicAdapter extends BaseRecyclerViewAdapter<MusicItem, MusicAdapter.MusicViewHolder> {

    private final CropCircleTransformation cropCircleTransformation;

    public MusicAdapter(@NonNull Context context) {
        super(context);
        cropCircleTransformation = new CropCircleTransformation(context);
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        MusicItem item = getItem(position);
        holder.title.setText(getFilter().highlightFilteredSubstring(item.title()));
        holder.artist.setText(getFilter().highlightFilteredSubstring(item.artist()));
        holder.album.setText(getFilter().highlightFilteredSubstring(item.album()));
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

    public class MusicViewHolder extends RecyclerView.ViewHolder {

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


        public MusicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
