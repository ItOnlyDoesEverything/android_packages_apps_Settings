package com.android.settings.musicstats;

import android.content.Context;
import android.os.IMusicStatsManager;
import android.os.ServiceManager;
import android.os.RemoteException;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import android.text.format.DateUtils;

public class MusicStatsController extends BasePreferenceController implements LifecycleObserver, OnStart, OnStop {

    private static final String TAG = "MusicStatsController";
    
    private static final String KEY_DURATION = "music_stats_today_duration";
    private static final String KEY_TRACK = "music_stats_most_played_track";
    private static final String KEY_ARTIST = "music_stats_most_played_artist";

    private IMusicStatsManager mService;

    public MusicStatsController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void onStart() {
        // Connect to service
        try {
            mService = IMusicStatsManager.Stub.asInterface(ServiceManager.getService("music_stats"));
        } catch (Exception e) {
            Log.e(TAG, "Failed to connect to MusicStatsService", e);
        }
    }

    @Override
    public void onStop() {
        // Cleanup
        mService = null;
    }

    @Override
    public void updateState(Preference preference) {
        if (mService == null) {
             // Try to reconnect if service was not available on start
             try {
                mService = IMusicStatsManager.Stub.asInterface(ServiceManager.getService("music_stats"));
            } catch (Exception e) {
                Log.e(TAG, "Failed to connect to MusicStatsService", e);
            }
        }
        
        if (mService == null) return;

        String key = preference.getKey();
        try {
            if (KEY_DURATION.equals(key)) {
                 long duration = mService.getTodayDuration();
                 preference.setSummary(formatDuration(duration));
            } else if (KEY_TRACK.equals(key)) {
                 String track = mService.getTopTrack();
                 preference.setSummary(track != null ? track : "None");
            } else if (KEY_ARTIST.equals(key)) {
                 String artist = mService.getTopArtist();
                 preference.setSummary(artist != null ? artist : "None");
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to communicate with MusicStatsService", e);
        }
    }

    private String formatDuration(long millis) {
        return DateUtils.formatElapsedTime(millis / 1000);
    }
}
