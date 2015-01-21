package ru.stefa.tizarhunter.stefasms.screens.send;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

        return rootView;
    }

    View.OnClickListener mSendClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mDatabaseActions.addToArchive(mMessageEditText.getText().toString(), mNumbers.size(),
                    System.currentTimeMillis());
        }
    };

    private void sendSms()
    {

    }
}

