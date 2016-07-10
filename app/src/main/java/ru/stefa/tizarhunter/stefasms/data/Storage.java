package ru.stefa.tizarhunter.stefasms.data;

import android.content.SharedPreferences;

/**
 * Created by Danil on 06.07.2016.
 */
public class Storage {
    public static final String KEY_SHARED_PREFERENCES = "key.shared.preferences";
    private static final String KEY_MESSAGE_DELAY = "ket.message.delay";
    private static final String KEY_MESSAGE_PORTION = "ket.message.portion";
    private static final String KEY_MESSAGE_PORTION_DELAY = "ket.message.portion.delay";

    public static IntPreference mMessageDelay;
    public static IntPreference mMessagePortion;
    public static IntPreference mMessagePortionDelay;

    public Storage(SharedPreferences sharedPreferences) {
        mMessageDelay = new IntPreference(sharedPreferences, KEY_MESSAGE_DELAY, 500);
        mMessagePortion = new IntPreference(sharedPreferences, KEY_MESSAGE_PORTION, 40);
        mMessagePortionDelay = new IntPreference(sharedPreferences, KEY_MESSAGE_PORTION_DELAY, 6000);
    }



}
