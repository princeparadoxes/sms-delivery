package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewNumberDialog extends AlertDialog.Builder
{
    private TextView mTitle;
    private EditText mEditText;

    public NewNumberDialog(final Context context, final Callback callback)
    {
        super(context);
        mTitle = createTitle(context);
        mTitle.setText("Новый номер");
        LinearLayout linearLayout = createMainLayout(context);
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Small);
        textView.setText("Введите новый номер");
        linearLayout.addView(textView);
        mEditText = new EditText(context);
        linearLayout.addView(mEditText);
        setCustomTitle(mTitle).setView(linearLayout).setPositiveButton(android.R.string.ok, new DialogInterface
                .OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (!mEditText.getText().toString().isEmpty())
                        {
                            callback.ok(mEditText.getText().toString());
                        }
                        else
                        {
                            Toast.makeText(context,"Введите новый номер!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, null);

    }

    private TextView createTitle(Context context)
    {
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        return textView;
    }

    private LinearLayout createMainLayout(Context context)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private TextView createTextView(Context context, int style)
    {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, style);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        return textView;
    }

    public static interface Callback
    {
        void ok(String newNumber);
    }
}
