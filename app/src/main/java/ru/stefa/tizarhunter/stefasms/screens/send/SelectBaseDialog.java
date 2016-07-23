package ru.stefa.tizarhunter.stefasms.screens.send;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.stefa.tizarhunter.stefasms.data.DatabaseActions;
import ru.stefa.tizarhunter.stefasms.screens.numbers.NumbersModel;

public class SelectBaseDialog extends AlertDialog.Builder {
    private int mSelect = -1;
    private TextView mTitle;

    public SelectBaseDialog(final Context context, final Callback callback) {
        super(context);
        mTitle = createTitle(context);
        mTitle.setText("Выберите базу номеров");
        LinearLayout linearLayout = createMainLayout(context);
        DatabaseActions databaseActions = new DatabaseActions();
        databaseActions.connectionDatabase(context);
        final List<NumbersModel> stringList = databaseActions.listTables();
        linearLayout.addView(createRadioButtons(context, stringList));
        setCustomTitle(mTitle).setView(linearLayout).setPositiveButton(android.R.string.ok, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mSelect != -1) {
                    callback.ok(stringList.get(mSelect).getName().replaceAll("\\s+", "_"));
                } else {
                    Toast.makeText(context, "Вы не выбрали ни одной базы", Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(android.R.string.cancel, null);

    }

    private TextView createTitle(Context context) {
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        return textView;
    }

    private LinearLayout createMainLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }


    private RadioGroup createRadioButtons(Context context, List<NumbersModel> stringList) {
        RadioGroup radioGroup = new RadioGroup(context);
        for (int i = 0; i < stringList.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(stringList.get(i).getName().replaceAll("_", " "));
            final int finalI = i;
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mSelect = finalI;
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
    }
}
