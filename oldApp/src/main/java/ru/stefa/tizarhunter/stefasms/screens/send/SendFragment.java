package ru.stefa.tizarhunter.stefasms.screens.send;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.database.DatabaseActions;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class SendFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Context mContext;
    private String mBaseName;
    ArrayList<String> mNumbers;
    DatabaseActions mDatabaseActions;

    private Button mSendButton;
    private EditText mMessageEditText;
    private TextView mNumberSymbols;

    private static final String NUMBER_SYMBOLS = "Введено %d из %d символов";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SendFragment newInstance(int sectionNumber, Context context)
    {
        SendFragment fragment = new SendFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SendFragment(Context context)
    {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_send, container, false);
        mSendButton = (Button) rootView.findViewById(R.id.send_button_send);
        mMessageEditText = (EditText) rootView.findViewById(R.id.send_edit_message);
        mNumberSymbols = (TextView) rootView.findViewById(R.id.send_number_symbols);
        mNumberSymbols.setText(String.format(NUMBER_SYMBOLS, 0, 70));
        mDatabaseActions = new DatabaseActions();
        mDatabaseActions.connectionDatabase(mContext);
        rootView.findViewById(R.id.send_button_base).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SelectBaseDialog selectBaseDialog = new SelectBaseDialog(mContext, new SelectBaseDialog.Callback()
                {
                    @Override
                    public void ok(String nameBase)
                    {
                        if (nameBase != null)
                        {
                            mBaseName = nameBase;
                            mNumbers = mDatabaseActions.readTableColumn(nameBase, DatabaseActions.NUMBER);
                            mSendButton.setOnClickListener(mSendClickListener);
                        }
                        else
                        {
                            mBaseName = null;
                            mSendButton.setOnClickListener(null);
                        }
                    }
                });
                selectBaseDialog.show();
            }
        });
        mMessageEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                mNumberSymbols.setText(String.format(NUMBER_SYMBOLS, mMessageEditText.getText().toString().length(), 70));
            }
        });
        return rootView;
    }

    View.OnClickListener mSendClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            sendSms();
        }
    };

    private void sendSms()
    {
        String sms = mMessageEditText.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        if (sms.length() < 70)
        {
            for (int i = 0; i < mNumbers.size(); i++)
            {
                try
                {
                    smsManager.sendTextMessage(mNumbers.get(i), null, mMessageEditText.getText().toString(), null, null);
                    Toast.makeText(mContext, "Сообщение " + (i + 1) + " из " + mNumbers.size() + " " +
                            "послано", Toast.LENGTH_SHORT).show();
                    Thread.sleep(500);
                } catch (Exception e) {
                    Toast.makeText(mContext, "Сообщения не отправленны",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            mDatabaseActions.addToArchive(mMessageEditText.getText().toString(), mNumbers.size(),
                    System.currentTimeMillis());
        }
        else
        {
            Toast.makeText(mContext, "Смс не должно быть более 70 символов" ,Toast.LENGTH_LONG).show();
        }
    }
}

