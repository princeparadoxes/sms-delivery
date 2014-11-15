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

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.database.DatabaseActions;
import ru.stefa.tizarhunter.stefasms.files.FilesActions;
import ru.stefa.tizarhunter.stefasms.files.OpenFileDialog;
import ru.stefa.tizarhunter.stefasms.misc.MultiChoiceImpl;

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
        final DatabaseActions databaseActions = new DatabaseActions();
        databaseActions.connectionDatabase(mContext);
        final NumbersAdapter numbersAdapter = new NumbersAdapter(mContext, databaseActions.listTables());
        listView.setAdapter(numbersAdapter);
        //Указываем ListView, что мы хотим режим с мультивыделением
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        //Указываем обработчик такого режима
        listView.setMultiChoiceModeListener(new MultiChoiceImpl(listView, new MultiChoiceImpl.OnClickMenuListener()
        {
            @Override
            public void OnDeleteClick(List<String> selectedElements)
            {
                for (int i=0; i<selectedElements.size();i++)
                {
                    databaseActions.dropTable(selectedElements.get(i));
                }
                numbersAdapter.notifyDataSetChanged();

            }
        }));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                if(position == 0)
                {
                    NewBaseDialog newBaseDialog = new NewBaseDialog(mContext, new NewBaseDialog.Callback()
                    {
                        @Override
                        public void ok(final String nameBase)
                        {
                            databaseActions.createTableNumbers(nameBase);
                        }

                        @Override
                        public void okImport(final String nameBase)
                        {
                            databaseActions.createTableNumbers(nameBase);
                            OpenFileDialog fileDialog = new OpenFileDialog(mContext)
                                    .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                                        @Override
                                        public void OnSelectedFile(String fileName) {
                                            FilesActions filesActions =  new FilesActions(mContext);
                                            ArrayList<String> numbersFromFile = filesActions.readFileSD(fileName);
                                            databaseActions.insertNumbersInTable(nameBase, numbersFromFile);
                                            Toast.makeText(getActivity(), fileName, Toast.LENGTH_LONG).show();
                                        }
                                    });
                            fileDialog.show();
                        }
                    });
                    newBaseDialog.show();

                }
            }
        });
        return rootView;
    }
}
