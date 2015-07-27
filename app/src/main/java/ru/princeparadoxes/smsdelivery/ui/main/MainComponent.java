package ru.princeparadoxes.smsdelivery.ui.main;

import ru.princeparadoxes.smsdelivery.SmsComponent;
import ru.princeparadoxes.smsdelivery.ui.main.send.SendView;
import ru.princeparadoxes.smsdelivery.ui.main.databases.DatabasesView;
import ru.princeparadoxes.smsdelivery.ui.main.archive.ArchiveView;
import ru.princeparadoxes.smsdelivery.ui.settings.SettingsView;

import dagger.Component;

@MainScope
@Component(dependencies = SmsComponent.class, modules = MainModule.class)
public interface MainComponent extends MainDepencencies{
    void inject(MainActivity activity);

    void inject(MainView view);

    void inject(SendView view);

    void inject(DatabasesView view);

    void inject(ArchiveView view);

    void inject(SettingsView view);
}
