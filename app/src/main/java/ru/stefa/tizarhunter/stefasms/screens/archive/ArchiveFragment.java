package ru.stefa.tizarhunter.stefasms.screens.archive;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.database.DatabaseActions;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class ArchiveFragment extends Fragment
{
    private Context mContext;
    private List<ArchiveModel> mArchiveModelList;
    private static final String ARG_SECTION_NUMBER = "section_archive";
    private DatabaseActions mDatabaseActions;

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
        mDatabaseActions = new DatabaseActions();
        mDatabaseActions.connectionDatabase(mContext);
        ListView listView = (ListView) rootView.findViewById(R.id.archive_listView);
        mArchiveModelList = mDatabaseActions.getAllArchive();
        ArchiveAdapter archiveAdapter = new ArchiveAdapter(mContext, mArchiveModelList);
        listView.setAdapter(archiveAdapter);
        if (mArchiveModelList.size() == 0)
        {
            ((TextView) rootView.findViewById(R.id.archive_empty_text)).setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
