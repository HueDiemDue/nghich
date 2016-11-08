package com.duongtung.musicbox.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duongtung.musicbox.MainActivity;
import com.duongtung.musicbox.R;
import com.duongtung.musicbox.adapter.MusicAdapter;
import com.duongtung.musicbox.adapter.VideoAdapter;
import com.duongtung.musicbox.data.MusicLoader;
import com.duongtung.musicbox.data.VideoLoader;
import com.duongtung.musicbox.filter.MusicFilter;
import com.duongtung.musicbox.filter.VideoFilter;
import com.duongtung.musicbox.interfacemusic.ItemClickSupport;
import com.duongtung.musicbox.service.MusicService;
import com.duongtung.musicbox.utils.EmptyViewObserver;
import com.duongtung.musicbox.utils.MusicItem;
import com.duongtung.musicbox.utils.VideoItem;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by duong.thanh.tung on 11/9/2016.
 */

public class FragmentListVideo extends Fragment {

    private static final int VIDEO_LOADER_ID = 2;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.empty_view)
    View emptyView;

    private VideoAdapter adapter;
    private EmptyViewObserver emptyViewObserver;
    private View view;
    private static FragmentListVideo instance;

    public final static FragmentListVideo getInstance() {
        if (instance == null)
            instance = new FragmentListVideo();
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new VideoAdapter(this.getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        emptyViewObserver = new EmptyViewObserver(emptyView);
        emptyViewObserver.bind(recyclerView);
        VideoFilter filter = new VideoFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        adapter.withFilter(filter);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position, long id) {
                        VideoItem item = adapter.getItem(position);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            loadVideo();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_music, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (adapter.getItemCount() == 0)
            getActivity().getSupportLoaderManager().restartLoader(VIDEO_LOADER_ID, null, new android.support.v4.app.LoaderManager.LoaderCallbacks<Collection<VideoItem>>() {
                @Override
                public android.support.v4.content.Loader<Collection<VideoItem>> onCreateLoader(int id, Bundle args) {
                    if (id == VIDEO_LOADER_ID)
                        return new VideoLoader(getActivity());
                    return null;
                }

                @Override
                public void onLoaderReset(android.support.v4.content.Loader<Collection<VideoItem>> loader) {
                    int size = adapter.getItemCount();
                    adapter.clear();
                    adapter.notifyItemRangeRemoved(0, size);
                }

                @Override
                public void onLoadFinished(android.support.v4.content.Loader<Collection<VideoItem>> loader, Collection<VideoItem> data) {
                    adapter.addAll(data);
                    adapter.notifyItemRangeInserted(0, data.size());
                }
            });
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        emptyViewObserver.unbind();
    }

    private void loadVideo() {
        getActivity().getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, new android.support.v4.app.LoaderManager.LoaderCallbacks<Collection<VideoItem>>() {
            @Override
            public android.support.v4.content.Loader<Collection<VideoItem>> onCreateLoader(int id, Bundle args) {
                if (id == VIDEO_LOADER_ID)
                    return new VideoLoader(getActivity());
                return null;
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Collection<VideoItem>> loader) {
                int size = adapter.getItemCount();
                adapter.clear();
                adapter.notifyItemRangeRemoved(0, size);
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Collection<VideoItem>> loader, Collection<VideoItem> data) {
                adapter.addAll(data);
                adapter.notifyItemRangeInserted(0, data.size());
            }
        });

    }
}
