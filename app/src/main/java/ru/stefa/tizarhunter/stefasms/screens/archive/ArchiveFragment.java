package ru.stefa.tizarhunter.stefasms.screens.archive;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class ArchiveFragment extends Fragment
{
    private Context mContext;
    private ArrayList<ArchiveModel> mArchiveModelList;
    private static final String ARG_SECTION_NUMBER = "section_archive";

    public static ArchiveFragment newInstance(int sectionNumber, Context context)
    {
        ArchiveFragment fragment = new ArchiveFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ArchiveFragment(Context context)
    {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.archive_listView);
        mArchiveModelList = new ArrayList<ArchiveModel>();
        for (int i = 0; i < 5; i++)
        {
            ArchiveModel archiveModel = new ArchiveModel();
            archiveModel.setText("Архивное смс" + i);
            archiveModel.setNumberSends(500 + i);
            //archiveModel.getDateFormat()
            mArchiveModelList.add(archiveModel);
        }
        ArchiveAdapter archiveAdapter = new ArchiveAdapter(mContext, mArchiveModelList);
        listView.setAdapter(archiveAdapter);
        return rootView;
    }
}
