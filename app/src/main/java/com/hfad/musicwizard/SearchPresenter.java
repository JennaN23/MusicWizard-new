package com.hfad.musicwizard;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

public class SearchPresenter implements Search.ActionListener {

    private static final String TAG = "SearchPresenter";
    public static final int PAGE_SIZE = 20;

    private final Context context;
    private final Search.View mView;
    private String mCurrentQuery;

    private SearchPager mSearchPager;
    private SearchPager.CompleteListener mSearchListener;

    private Player mPlayer;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayer = ((PlayerService.PlayerBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayer = null;
        }
    };

    public SearchPresenter(Context context, Search.View view) {
        this.context = context;
        mView = view;
    }

    @Override
    public void init(String accessToken) {
        logMessage("Api Client created");
        SpotifyAPI spotifyAPI = new SpotifyAPI();

        if (accessToken != null) {
            spotifyAPI.setToken(accessToken);
        } else {
            logError("No valid access token");
        }

        mSearchPager = new SearchPager(spotifyAPI.getService());

        context.bindService(PlayerService.getIntent(context), mServiceConnection, Activity.BIND_AUTO_CREATE);
    }


    @Override
    public void search(@Nullable String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty() && !searchQuery.equals(mCurrentQuery)) {
            logMessage("query text submit " + searchQuery);
            mCurrentQuery = searchQuery;
            mView.reset();
            mSearchListener = new SearchPager.CompleteListener() {
                @Override
                public void onComplete(List<Track> items) {
                    mView.addData(items);
                }

                @Override
                public void onError(Throwable error) {
                    logError(error.getMessage());
                }
            };
            mSearchPager.getFirstPage(searchQuery, PAGE_SIZE, mSearchListener);
        }
    }


    @Override
    public void destroy() {
        context.unbindService(mServiceConnection);
    }

    @Override
    @Nullable
    public String getCurrentQuery() {
        return mCurrentQuery;
    }

    @Override
    public void resume() {
        context.stopService(PlayerService.getIntent(context));
    }

    @Override
    public void pause() {
        context.startService(PlayerService.getIntent(context));
    }

    @Override
    public void loadMoreResults() {
        Log.d(TAG, "Load more...");
        mSearchPager.getNextPage(mSearchListener);
    }

    @Override
    public void selectTrack(Track item) {
        String previewUrl = item.preview_url;

        if (previewUrl == null) {
            logMessage("Track doesn't have a preview");
            return;
        }

        if (mPlayer == null) return;

        String currentTrackUrl = mPlayer.getCurrentTrackURL();

        if (currentTrackUrl == null || !currentTrackUrl.equals(previewUrl)) {
            mPlayer.play(previewUrl);
        } else if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.resume();
        }
    }

    private void logError(String msg) {
        Toast.makeText(context, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg);
    }

    private void logMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    //TODO: review
}
