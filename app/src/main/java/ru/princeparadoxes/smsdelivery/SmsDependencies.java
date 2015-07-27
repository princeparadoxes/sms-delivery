package ru.princeparadoxes.smsdelivery;

import android.app.Application;

import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.base.tools.KeyboardPresenter;
import ru.princeparadoxes.smsdelivery.base.tools.ToastPresenter;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.data.FileService;
import ru.princeparadoxes.smsdelivery.data.TokenStorage;
import ru.princeparadoxes.smsdelivery.ui.ActivityHierarchyServer;
import ru.princeparadoxes.smsdelivery.ui.AppContainer;
import ru.princeparadoxes.smsdelivery.ui.ApplicationSwitcher;
import ru.princeparadoxes.smsdelivery.ui.main.databases.DatabasesActionsDialogBuilder;


/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface SmsDependencies {
    void inject(SmsApplication app);

    Application application();

    AppContainer appContainer();

//    RefWatcher refWatcher();


    ActivityScreenSwitcher activityScreenSwitcher();

    ActivityHierarchyServer activityHierarchyServer();

    DataService dataService();

    TokenStorage tokenStorage();

    KeyboardPresenter keyboardPresenter();

    ToastPresenter toastPresenter();

    FileService fileService();

    ApplicationSwitcher applicationSwitcher();

    DatabasesActionsDialogBuilder databasesActionsDialogBuilder();

}
