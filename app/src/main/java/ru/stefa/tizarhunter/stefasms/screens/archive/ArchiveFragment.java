package ru.stefa.tizarhunter.stefasms.screens.archive;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class ArchiveFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ArchiveFragment newInstance(int sectionNumber)
    {
        ArchiveFragment fragment = new ArchiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ArchiveFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);
//        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
//        tv.setText("Отправленные");
        return rootView;
    }
}
