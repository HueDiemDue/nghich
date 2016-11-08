package com.duongtung.musicbox.fragment;

import android.content.ContentValues;
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

import com.duongtung.musicbox.ApplicationMusic;
import com.duongtung.musicbox.MainActivity;
import com.duongtung.musicbox.R;
import com.duongtung.musicbox.adapter.MusicAdapter;
import com.duongtung.musicbox.data.MusicLoader;
import com.duongtung.musicbox.filter.MusicFilter;
import com.duongtung.musicbox.interfacemusic.ItemClickSupport;
import com.duongtung.musicbox.service.MusicService;
import com.duongtung.musicbox.utils.DatabaseStatic;
import com.duongtung.musicbox.utils.EmptyViewObserver;
import com.duongtung.musicbox.utils.MusicItem;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by duong.thanh.tung on 11/9/2016.
 */

public class FragmentListMusic extends Fragment {

    private static final int MUSIC_LOADER_ID = 1;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectView(R.id.empty_view)
    View emptyView;

    private MusicAdapter adapter;
    private EmptyViewObserver emptyViewObserver;
    private View view;
    private static FragmentListMusic instance;

    public final static FragmentListMusic getInstance() {
        if (instance == null)
            instance = new FragmentListMusic();
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new MusicAdapter(this.getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        emptyViewObserver = new EmptyViewObserver(emptyView);
        emptyViewObserver.bind(recyclerView);
        MusicFilter filter = new MusicFilter(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        adapter.withFilter(filter);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position, long id) {
                        MusicItem item = adapter.getItem(position);
                        if (!((MainActivity) getActivity()).isServiceRunning(MusicService.class)) {
                            MusicService.setTracks(getActivity(), adapter.getSnapshot().toArray(new MusicItem[adapter.getNonFilteredCount()]));
                        }
                        MusicService.playTrack(getActivity(), item);
                    }
                });
        ItemClickSupport.addTo(recyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(RecyclerView parent, View view, int position, long id) {
                        MusicItem item = adapter.getItem(position);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseStatic.name,item.title());
                        contentValues.put(DatabaseStatic.data,String.valueOf(item.fileUri()));
                        contentValues.put(DatabaseStatic.albumArtUri,String.valueOf(item.albumArtUri()));
                        contentValues.put(DatabaseStatic.type,0);
//                        ApplicationMusic.getDatabaseHelper().
                        return false;
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
            loadMusic();
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
        MusicService.setState(getActivity(), false);
        if (adapter.getItemCount() == 0)
            getActivity().getSupportLoaderManager().restartLoader(MUSIC_LOADER_ID, null, new android.support.v4.app.LoaderManager.LoaderCallbacks<Collection<MusicItem>>() {
                @Override
                public android.support.v4.content.Loader<Collection<MusicItem>> onCreateLoader(int id, Bundle args) {
                    if (id == MUSIC_LOADER_ID)
                        return new MusicLoader(getActivity());
                    return null;
                }

                @Override
                public void onLoaderReset(android.support.v4.content.Loader<Collection<MusicItem>> loader) {
                    int size = adapter.getItemCount();
                    adapter.clear();
                    adapter.notifyItemRangeRemoved(0, size);
                }

                @Override
                public void onLoadFinished(android.support.v4.content.Loader<Collection<MusicItem>> loader, Collection<MusicItem> data) {
                    adapter.addAll(data);
                    adapter.notifyItemRangeInserted(0, data.size());
                    MusicService.setTracks(getActivity(), data.toArray(new MusicItem[data.size()]));
                }
            });
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicService.setState(getActivity(), true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        emptyViewObserver.unbind();
    }

    private void loadMusic() {
        getActivity().getSupportLoaderManager().initLoader(MUSIC_LOADER_ID, null, new android.support.v4.app.LoaderManager.LoaderCallbacks<Collection<MusicItem>>() {
            @Override
            public android.support.v4.content.Loader<Collection<MusicItem>> onCreateLoader(int id, Bundle args) {
                if (id == MUSIC_LOADER_ID)
                    return new MusicLoader(getActivity());
                return null;
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Collection<MusicItem>> loader) {
                int size = adapter.getItemCount();
                adapter.clear();
                adapter.notifyItemRangeRemoved(0, size);
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Collection<MusicItem>> loader, Collection<MusicItem> data) {
                adapter.addAll(data);
                adapter.notifyItemRangeInserted(0, data.size());
                MusicService.setTracks(getActivity(), data.toArray(new MusicItem[data.size()]));
            }
        });

    }

}
