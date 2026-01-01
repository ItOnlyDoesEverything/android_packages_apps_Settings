package com.android.settings.musicstats;

import android.content.Context;
import com.android.settings.core.BasePreferenceController;

public class TopLevelMusicStatsPreferenceController extends BasePreferenceController {

    public TopLevelMusicStatsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
