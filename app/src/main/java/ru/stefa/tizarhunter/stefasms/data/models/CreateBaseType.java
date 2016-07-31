package ru.stefa.tizarhunter.stefasms.data.models;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by Danil on 27.07.2016.
 */
public enum CreateBaseType {
    IMPORT_FROM_FILE(0, R.string.create_base_type_import_from_file),
    INPUT_FROM_KEYBOARD(1, R.string.create_base_type_input_from_keyboard);

    private int id;
    private int name;

    CreateBaseType(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }

    public static CreateBaseType getTypeForId(int id) {
        for (CreateBaseType createBaseType : values()) {
            if (createBaseType.getId() == id) {
                return createBaseType;
            }
        }
        return IMPORT_FROM_FILE;
    }
}
