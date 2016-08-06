package ru.stefa.tizarhunter.stefasms.misc.met.validators;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by ognyov on 25/02/16.
 */
public class PhoneValidator extends METValidator {

    public PhoneValidator(Context context) {
        super("");
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        text = text.toString().replaceAll("((\\+7)|[^\\d])", "");
        return Patterns.PHONE.matcher(text).matches() && text.length() == 10;
    }
}
