package LanguagePreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Anita on 24/10/16.
 */
public class ReadPreference {
    // for storing value.
    private SharedPreferences sharedPreferences;

    /**
     * parametrised constructor in which initializing shared preference.
     *
     * @param context passing application context.
     */
    public ReadPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
    }

    /**
     * getting saved language from the preferences
     * @return saved language
     */
    public String getSaveLanguage() {
        return sharedPreferences.getString("lang", "noLanguage");
    }
}
