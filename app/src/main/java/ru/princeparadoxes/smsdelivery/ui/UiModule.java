package ru.princeparadoxes.smsdelivery.ui;

import android.app.Activity;

import ru.princeparadoxes.smsdelivery.base.mvp.Registry;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.base.tools.KeyboardPresenter;
import ru.princeparadoxes.smsdelivery.ui.annotation.ActivityScreenSwitcherServer;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

    @Provides
    @ApplicationScope
    AppContainer provideAppContainer() {
        return AppContainer.DEFAULT;
    }

    @Provides
    @ApplicationScope
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @ApplicationScope
    @ActivityScreenSwitcherServer
    ActivityHierarchyServer provideActivityScreenSwitcherServer(final ActivityScreenSwitcher screenSwitcher,
                                                                final KeyboardPresenter keyboardPresenter) {
        return new ActivityHierarchyServer.Empty() {
            @Override
            public void onActivityStarted(Activity activity) {
                screenSwitcher.attach(activity);
                keyboardPresenter.attach(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                screenSwitcher.detach();
                keyboardPresenter.detach();
            }
        };
    }

    @Provides
    @ApplicationScope
    ActivityHierarchyServer provideActivityHierarchyServer(
            @ActivityScreenSwitcherServer ActivityHierarchyServer server) {
        final ActivityHierarchyServer.Proxy proxy = new ActivityHierarchyServer.Proxy();
        proxy.addServer(server);
        proxy.addServer(Registry.SERVER);
        return proxy;
    }
}
