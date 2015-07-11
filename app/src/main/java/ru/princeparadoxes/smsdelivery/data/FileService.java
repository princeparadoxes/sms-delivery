package ru.princeparadoxes.smsdelivery.data;

import android.app.Application;

import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by Danil on 18.05.2015.
 */
@ApplicationScope
public class FileService {

    private final Application application;

    @Inject
    public FileService(Application application) {
        this.application = application;
    }
}
