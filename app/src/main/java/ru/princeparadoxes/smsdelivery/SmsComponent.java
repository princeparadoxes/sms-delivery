package ru.princeparadoxes.smsdelivery;

import dagger.Component;

import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;
import ru.princeparadoxes.smsdelivery.ui.UiModule;

@ApplicationScope
@Component(modules = {SmsModule.class, UiModule.class})
public interface SmsComponent extends SmsDependencies {
    /**
     * An initializer that creates the graph from an application.
     */
    static final class Initializer {
        static SmsComponent init(SmsApplication app) {
            return DaggerSmsComponent.builder()
                    .smsModule(new SmsModule(app))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }
}
