package ru.stefa.tizarhunter.stefasms.misc.met.validators;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;

/**
 * Created by Danil on 01.08.2016.
 */
public class IfBaseNameExistValidator extends METValidator {
    private List<NumbersBaseModel> list;

    public IfBaseNameExistValidator(Context context, List<NumbersBaseModel> list) {
        super(context.getString(R.string.validate_number_base_exist));
        this.list = list;
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        for (NumbersBaseModel model : list) {
            if (model.getName().equals(text.toString())) return false;
        }
        return true;
    }
}
