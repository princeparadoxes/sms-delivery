package ru.stefa.tizarhunter.stefasms.screens.main.archive;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.DatabaseActions;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class ArchiveFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_archive";
    private List<ArchiveModel> mArchiveModelList;
    private DatabaseActions mDatabaseActions;

    public static ArchiveFragment newInstance(int sectionNumber, Context context) {
        ArchiveFragment fragment = new ArchiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);
        mDatabaseActions = new DatabaseActions();
        mDatabaseActions.connectionDatabase(getActivity());
        ListView listView = (ListView) rootView.findViewById(R.id.archive_listView);
        mArchiveModelList = mDatabaseActions.getAllArchive();
        Collections.reverse(mArchiveModelList);
        ArchiveAdapter archiveAdapter = new ArchiveAdapter(getActivity(), mArchiveModelList);
        listView.setAdapter(archiveAdapter);
        if (mArchiveModelList.size() == 0) {
            rootView.findViewById(R.id.archive_empty_text).setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
