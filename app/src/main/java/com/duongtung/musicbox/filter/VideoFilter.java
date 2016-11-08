package com.duongtung.musicbox.filter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.duongtung.musicbox.base.BaseFilter;
import com.duongtung.musicbox.utils.MusicItem;
import com.duongtung.musicbox.utils.VideoItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Filter for list of tracks.
 */
public class VideoFilter extends BaseFilter<VideoItem> {

    public VideoFilter(int highlightColor) throws AssertionError {
        super(highlightColor);
    }

    @NonNull
    @Override
    protected FilterResults performFilteringImpl(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (TextUtils.isEmpty(constraint) || TextUtils.isEmpty(constraint.toString().trim())) {
            results.count = -1;
            return results;
        }
        String str = constraint.toString().trim();
        List<VideoItem> result = new ArrayList<>();
        int size = getNonFilteredCount();
        for (int i = 0; i < size; i++) {
            VideoItem item = getNonFilteredItem(i);
            if (check(str, item.title())){
                result.add(item);
            }
        }
        results.count = result.size();
        results.values = result;
        return results;
    }

    private boolean check(@NonNull String what, @Nullable String where) {
        if (TextUtils.isEmpty(where))
            return false;
        where = where.toLowerCase();
        what = what.toLowerCase();
        return where.contains(what);
    }
}
