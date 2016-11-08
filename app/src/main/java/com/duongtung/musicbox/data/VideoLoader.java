package com.duongtung.musicbox.data;

/**
 * Created by duong on 11/9/2016.
 */

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.duongtung.musicbox.base.BaseAsyncTaskLoader;
import com.duongtung.musicbox.utils.MusicItem;
import com.duongtung.musicbox.utils.VideoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Loader for list of tracks.
 */
public class VideoLoader extends BaseAsyncTaskLoader<Collection<VideoItem>> {

    private final Uri albumArtUri = Uri.parse("content://media/external/video/albumart");

    public VideoLoader(Context context) {
        super(context);
    }

    @Override
    public Collection<VideoItem> loadInBackground() {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContext().getContentResolver().query(uri, null,
                null, null, null);
        if (cursor == null) {
            return Collections.emptyList();
        }
        List<VideoItem> items = new ArrayList<>();
        String thumbPath = "";
        try {
            if (cursor.moveToFirst()) {
                int id = cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID);
                int title = cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE);
                int duration = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION);
                int data = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
                do {
                    MediaStore.Video.Thumbnails.getThumbnail(getContext().getContentResolver(),
                            id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                    String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};
                    Cursor thumbCursor = getContext().getContentResolver().query(
                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                            thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
                                    + id, null, null);
                    if (thumbCursor.moveToFirst()) {
                        thumbPath = thumbCursor.getString(thumbCursor
                                .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        thumbCursor.close();
                    }
                    VideoItem item = new VideoItem()
                            .title(cursor.getString(title))
                            .duration(cursor.getLong(duration))
                            .fileUri(Uri.parse(cursor.getString(data)))
                            .albumArtUri(Uri.parse(thumbPath));
                    ;
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return items;
    }
}