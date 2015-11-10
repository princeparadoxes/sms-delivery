package ru.princeparadoxes.smsdelivery.ui.create.database;

import dagger.Component;
import ru.princeparadoxes.smsdelivery.SmsComponent;
import ru.princeparadoxes.smsdelivery.ui.create.database.first.step.FirstStepView;
import ru.princeparadoxes.smsdelivery.ui.create.database.second.step.SecondStepView;

@CreateDatabaseScope
@Component(dependencies = SmsComponent.class)
public interface CreateDatabaseComponent {
    void inject(CreateDatabaseActivity activity);

    void inject(CreateDatabaseView view);

    void inject(FirstStepView view);

    void inject(SecondStepView view);
}
