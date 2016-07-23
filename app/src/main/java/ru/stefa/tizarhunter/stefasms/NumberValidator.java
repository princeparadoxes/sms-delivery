package ru.stefa.tizarhunter.stefasms;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by ognyov on 25/02/16.
 */
public class NumberValidator extends METValidator {
    private int number;
    private boolean isMore;

    public NumberValidator(Context context, int number, boolean isMore) {
        super("Число должно быть " + (isMore ? "больше" : "меньше") + " чем " + number);
        this.number = number;
        this.isMore = isMore;
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        if (isMore) {
            return Integer.valueOf(text.toString()) >= number;
        } else {
            return Integer.valueOf(text.toString()) <= number;
        }
    }
}
