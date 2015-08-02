package ru.princeparadoxes.smsdelivery.ui.create.database;

import dagger.Component;
import ru.princeparadoxes.smsdelivery.SmsComponent;

@CreateDatabaseScope
@Component(dependencies = SmsComponent.class)
public interface CreateDatabaseComponent {
    void inject(CreateDatabaseActivity activity);

    void inject(CreateDatabaseView view);
}
