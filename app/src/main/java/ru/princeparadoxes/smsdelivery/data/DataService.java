package ru.princeparadoxes.smsdelivery.data;

import android.app.Application;
import android.content.Context;

import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

import javax.inject.Inject;

@ApplicationScope
public class DataService {
    public static final String NO_INTERNET_CONNECTION = "No internet connection";

    private final Context context;
    private final FileService fileService;
    private final TokenStorage tokenStorage;

    @Inject
    public DataService(Application application, TokenStorage tokenStorage,
                       FileService fileService) {
        this.context = application;
        this.fileService = fileService;
        this.tokenStorage = tokenStorage;
    }

}
