package ru.stefa.tizarhunter.stefasms.navigation;

import android.content.Context;
import android.content.Intent;

import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.screens.number.list.ContactListActivity;
import ru.stefa.tizarhunter.stefasms.screens.settings.SettingsActivity;

/**
 * Created by Danil on 05.08.2016.
 */
public class Router {
    public static final String NUMBER_LIST_BASE = "number.list.base";
    private Router() {
    }

    public static void goToNumberList(Context context, NumbersBaseModel model){
        Intent intent = new Intent(context, ContactListActivity.class);
        intent.putExtra(NUMBER_LIST_BASE, model);
        context.startActivity(intent);

    }
    public static void goToSettings(Context context){
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
