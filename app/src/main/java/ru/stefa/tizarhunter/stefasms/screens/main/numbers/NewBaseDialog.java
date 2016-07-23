package ru.stefa.tizarhunter.stefasms.screens.main.numbers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;

public class NewBaseDialog extends AlertDialog.Builder {
    private int mChoise = -1;
    private TextView mTitle;
    private EditText mEditText;

    public NewBaseDialog(final Context context, final Callback callback) {
        super(context);
        mTitle = createTitle(context);
        mTitle.setText(R.string.numbers_new_base);
        LinearLayout linearLayout = createMainLayout(context);
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Small);
        textView.setText(R.string.numbers_type_base_name);
        linearLayout.addView(textView);
        mEditText = new EditText(context);
        linearLayout.addView(mEditText);
        List<String> stringList = new ArrayList<String>();
        stringList.add(context.getString(R.string.numbers_type_type));
        stringList.add(context.getString(R.string.numbers_type_import));
        linearLayout.addView(createRadioButtons(context, stringList));
        setCustomTitle(mTitle).setView(linearLayout).setPositiveButton(android.R.string.ok, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!mEditText.getText().toString().isEmpty()) {

                    if (mChoise == 0) {
                        callback.ok(mEditText.getText().toString());
                    } else {
                        callback.okImport(mEditText.getText().toString());
                    }
                } else {
                    Toast.makeText(context, R.string.numbers_type_base_name, Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(android.R.string.cancel, null);

    }

    private TextView createTitle(Context context) {
        return createTextView(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
    }

    private LinearLayout createMainLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }


    private RadioGroup createRadioButtons(Context context, List<String> stringList) {
        RadioGroup radioGroup = new RadioGroup(context);
        for (int i = 0; i < stringList.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(stringList.get(i));
            final int finalI = i;
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mChoise = finalI;
                }
            });
            radioGroup.addView(radioButton);
        }
        return radioGroup;
    }

    private TextView createTextView(Context context, int style) {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, style);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        return textView;
    }

    public static interface Callback {
        void ok(String nameBase);

        void okImport(String nameBase);
    }
}
