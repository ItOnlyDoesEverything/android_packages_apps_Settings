package com.android.settings.musicstats;

import android.app.settings.SettingsEnums;
import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

public class MusicStatsFragment extends DashboardFragment {

    private static final String TAG = "MusicStatsFragment";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.DIGITAL_WELLBEING;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.music_stats_settings;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(Context context) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new MusicStatsController(context, "music_stats_today_duration"));
        controllers.add(new MusicStatsController(context, "music_stats_most_played_track"));
        controllers.add(new MusicStatsController(context, "music_stats_most_played_artist"));
        return controllers;
    }
}
