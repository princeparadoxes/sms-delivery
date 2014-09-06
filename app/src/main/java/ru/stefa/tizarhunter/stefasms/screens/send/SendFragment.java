package ru.stefa.tizarhunter.stefasms.screens.send;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class SendFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SendFragment newInstance(int sectionNumber)
    {
        SendFragment fragment = new SendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SendFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_send, container, false);

//        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
//        tv.setText("Отправка");
        return rootView;
    }
}

