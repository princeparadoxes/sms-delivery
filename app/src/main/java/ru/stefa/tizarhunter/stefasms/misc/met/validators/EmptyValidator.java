package ru.stefa.tizarhunter.stefasms.misc.met.validators;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by ognyov on 25/02/16.
 */
public class EmptyValidator extends METValidator {

    public EmptyValidator(Context context) {
        super(context.getString(R.string.validate_empty_field));
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return !isEmpty;
    }
}
