package ru.stefa.tizarhunter.stefasms.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danil on 01.08.2016.
 */
public class NewBaseDialogModel {
    private int id;
    private int name;
    private boolean isCheck;

    public NewBaseDialogModel(int id, int name) {
        this.id = id;
        this.name = name;
        this.isCheck = false;
    }

    public NewBaseDialogModel(int id, int name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
    }

    public static List<NewBaseDialogModel> getAllTypes() {
        List<NewBaseDialogModel> models = new ArrayList<>(CreateBaseType.values().length);
        for (CreateBaseType createBaseType : CreateBaseType.values()) {
            models.add(new NewBaseDialogModel(createBaseType.getId(), createBaseType.getName()));
        }
        return models;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public NewBaseDialogModel setCheck(boolean check) {
        isCheck = check;
        return this;
    }
}
