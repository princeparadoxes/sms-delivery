package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.database.DatabaseActions;
import ru.stefa.tizarhunter.stefasms.misc.OpenFileDialog;

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
        ListView listView = (ListView) rootView.findViewById(R.id.number_listView);
        DatabaseActions databaseActions = new DatabaseActions();
        databaseActions.connectionDatabase(mContext);
        NumbersAdapter numbersAdapter = new NumbersAdapter(mContext, databaseActions.listTables());
        listView.setAdapter(numbersAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                if(position == 0)
                {
                    OpenFileDialog fileDialog = new OpenFileDialog(mContext)
                            .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                                @Override
                                public void OnSelectedFile(String fileName) {
                                    Toast.makeText(getActivity(), fileName, Toast.LENGTH_LONG).show();
                                }
                            });
                    fileDialog.show();
                }
            }
        });
        return rootView;
    }
}
