package ru.princeparadoxes.smsdelivery.base;

import android.content.Context;

public class ComponentFinder {
    private ComponentFinder() {
    }

    @SuppressWarnings("unchecked")
    public static <C> C findActivityComponent(Context context) {
        return ((HasComponent<C>) context).getComponent();
    }
}
