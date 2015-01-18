package ru.stefa.tizarhunter.stefasms.screens.send;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class SendFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Context mContext;
    private String mBaseName;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_send, container, false);
        ((Button) rootView.findViewById(R.id.send_button_base)).setOnClickListener(new View.OnClickListener()
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
                            ((Button) rootView.findViewById(R.id.send_button_base)).setEnabled(true);
                            mBaseName = nameBase;
                        }
                        else
                        {
                            ((Button) rootView.findViewById(R.id.send_button_base)).setEnabled(false);
                            mBaseName = null;
                        }
                    }
                });
            }
        });

//        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
//        tv.setText("Отправка");
        return rootView;
    }
}

