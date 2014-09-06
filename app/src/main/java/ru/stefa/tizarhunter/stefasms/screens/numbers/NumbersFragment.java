package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.database.DatabaseActions;

public class NumbersFragment extends Fragment
{
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static NumbersFragment newInstance(int sectionNumber, Context context)
    {
        NumbersFragment fragment = new NumbersFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NumbersFragment(Context context)
    {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_number, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        DatabaseActions databaseActions = new DatabaseActions();
        databaseActions.connectionDatabase(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1, databaseActions.listTables());
        listView.setAdapter(adapter);
//        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
//        tv.setText("Номера");
        return rootView;
    }
}
