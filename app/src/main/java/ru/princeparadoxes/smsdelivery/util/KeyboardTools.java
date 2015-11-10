package ru.princeparadoxes.smsdelivery.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class KeyboardTools {
    public static void hideSoftKeyboard(final Activity activity) {
        if (activity == null) {
            return;
        }

        View currentFocus = activity.getCurrentFocus();

        if (currentFocus == null) {
            currentFocus = new View(activity);
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftKeyboard(final View view) {
        if (view == null) {
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.requestFocus();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                return true;
            }
        });
    }
}
