package LanguagePreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Anita on 24/10/16.
 */
public class SavePreference {
    // for storing value.
    private SharedPreferences sharedPreferences;

    /**
     * parametrised constructor in which initializing shared preference.
     *
     * @param context passing application context.
     */
    public SavePreference(Context context) {
        sharedPreferences = context.getSharedPreferences("language",Context.MODE_PRIVATE);
    }

    /**
     * saving language
     * @param language passing language as string
     */
    public void saveLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", language);
        editor.apply();

    }
}
