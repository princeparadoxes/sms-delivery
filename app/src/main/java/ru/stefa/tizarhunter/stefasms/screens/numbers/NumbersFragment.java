package ru.stefa.tizarhunter.stefasms.screens.numbers;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    private @NonNull HeaderHolder mHeaderHolder;
    private NumbersAdapter mNumbersAdapter;
    private ArrayList<NumbersModel> mAdapterData;
    private DatabaseActions mDatabaseActions;
    ListView mListView;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_number, container, false);

        mDatabaseActions = new DatabaseActions();
        mDatabaseActions.connectionDatabase(mContext);
        mAdapterData = mDatabaseActions.listTables();
        mNumbersAdapter = new NumbersAdapter(mContext, mAdapterData);
        mListView = (ListView) rootView.findViewById(R.id.number_listView);
        LinearLayout linearLayout = new LinearLayout(mContext);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.number_listview_header, linearLayout, false);
        linearLayout.addView(header);
        mListView.addHeaderView(linearLayout);
        mListView.setAdapter(mNumbersAdapter);
        mHeaderHolder = new HeaderHolder(header);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);


        changeHeaderToTables();
        return rootView;
    }

    private void changeHeaderToTables()
    {
        mHeaderHolder.mAddTextView.setText("Добавить новую базу номеров");
        mHeaderHolder.mAddContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NewBaseDialog newBaseDialog = new NewBaseDialog(mContext, new NewBaseDialog.Callback()
                {
                    @Override
                    public void ok(final String nameBase)
                    {
                        if (dataBaseNameValidation(nameBase) != null)
                        {
                            mDatabaseActions.createTableNumbers(dataBaseNameValidation(nameBase));
                            ArrayList<String> numbers = mDatabaseActions.readTableColumn(dataBaseNameValidation
                                    (nameBase), DatabaseActions.NUMBER);
                            NumbersModel numbersModel = new NumbersModel();
                            numbersModel.setName(nameBase);
                            numbersModel.setSize(numbers.size());
                            changeHeaderToNumbers(numbersModel);
                            updateListView(stringsToNumberModel(numbers));
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Неправильное имя базы данных", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void okImport(final String nameBase)
                    {
                        if (dataBaseNameValidation(nameBase) != null)
                        {
                            mDatabaseActions.createTableNumbers(dataBaseNameValidation(nameBase));
                            OpenFileDialog fileDialog = new OpenFileDialog(mContext).setOpenDialogListener(new OpenFileDialog.OpenDialogListener()
                                    {
                                        @Override
                                        public void OnSelectedFile(String fileName)
                                        {
                                            FilesActions filesActions = new FilesActions(mContext);
                                            ArrayList<String> numbersFromFile = filesActions.readFileSD(fileName);
                                            mDatabaseActions.insertNumbersInTable(dataBaseNameValidation(nameBase),
                                                    numbersFromFile);
                                            ArrayList<String> strings = mDatabaseActions.readTableColumn(dataBaseNameValidation
                                                    (nameBase), DatabaseActions.NUMBER);
                                            NumbersModel numbersModel = new NumbersModel();
                                            numbersModel.setName(nameBase);
                                            numbersModel.setSize(strings.size());
                                            changeHeaderToNumbers(numbersModel);
                                            updateListView(stringsToNumberModel(strings));
                                            Toast.makeText(getActivity(), fileName, Toast.LENGTH_LONG).show();
                                        }
                                    });
                            fileDialog.show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Неправильное имя базы данных", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                newBaseDialog.show();
            }
        });
        mHeaderHolder.mNumbersContainer.setVisibility(View.GONE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                changeHeaderToNumbers(mAdapterData.get((int) id));
                ArrayList<String> strings = mDatabaseActions.readTableColumn(mAdapterData.get((int) id).getName(),
                        DatabaseActions.NUMBER);
                updateListView(stringsToNumberModel(strings));
            }
        });
        mListView.setMultiChoiceModeListener(new MultiChoiceImpl(mListView, new MultiChoiceImpl.OnClickMenuListener()
        {
            @Override
            public void OnDeleteClick(List<String> selectedElements)
            {
                for (int i = 0; i < selectedElements.size(); i++)
                {
                    mDatabaseActions.dropTable(selectedElements.get(i));
                }
                updateListView(mDatabaseActions.listTables());
            }
        }));
    }


    private String dataBaseNameValidation(String name)
    {

        for (int i = 0; i < mAdapterData.size(); i++)
        {
            if (mAdapterData.get(i).getName().equals(name))
            {
                return null;
            }
        }
        return name.replaceAll("\\s+", "_");
    }

    private void changeHeaderToNumbers(final NumbersModel numbersModel)
    {
        mHeaderHolder.mAddTextView.setText("Добавить новый номер");
        mHeaderHolder.mAddContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NewNumberDialog newNumberDialog = new NewNumberDialog(mContext, new NewNumberDialog.Callback()
                {
                    @Override
                    public void ok(String newNumber)
                    {
                        ArrayList<String> number = new ArrayList<String>();
                        number.add(newNumber);
                        mDatabaseActions.insertNumbersInTable(numbersModel.getName(), number);
                        ArrayList<String> strings = mDatabaseActions.readTableColumn(numbersModel.getName(),
                                DatabaseActions.NUMBER);
                        updateListView(stringsToNumberModel(strings));
                    }
                });
                newNumberDialog.show();
            }
        });
        mHeaderHolder.mNameBaseText.setText(numbersModel.getName().replaceAll("_", " "));
        mHeaderHolder.mSizeBase.setText(numbersModel.getSize() + "");
        mHeaderHolder.mNumbersContainer.setVisibility(View.VISIBLE);
        mHeaderHolder.mNumbersContainer.setOnClickListener(null);
        mHeaderHolder.mBackImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateListView(mDatabaseActions.listTables());
                changeHeaderToTables();
            }
        });
        mListView.setOnItemClickListener(null);
        mListView.setMultiChoiceModeListener(new MultiChoiceImpl(mListView, new MultiChoiceImpl.OnClickMenuListener()
        {
            @Override
            public void OnDeleteClick(List<String> selectedElements)
            {
                mDatabaseActions.deleteNumbersFromTable(numbersModel.getName(),(ArrayList<String>) selectedElements);
                ArrayList<String> strings = mDatabaseActions.readTableColumn(numbersModel.getName(),
                        DatabaseActions.NUMBER);
                updateListView(stringsToNumberModel(strings));
            }
        }));
    }


    private void updateListView(ArrayList<NumbersModel> newList)
    {
        mAdapterData.clear();
        mAdapterData.addAll(newList);
        mNumbersAdapter.notifyDataSetChanged();
    }

    private ArrayList<NumbersModel> stringsToNumberModel(ArrayList<String> strings)
    {
        ArrayList<NumbersModel> numbersModels = new ArrayList<NumbersModel>();
        for (int i = 0; i < strings.size(); i++)
        {
            NumbersModel numbersModel = new NumbersModel();
            numbersModel.setName(strings.get(i));
            numbersModel.setSize(0);
            numbersModels.add(numbersModel);
        }
        return numbersModels;
    }

    public static class HeaderHolder
    {
        public final @NonNull FrameLayout mAddContainer;
        public final @NonNull TextView mAddTextView;
        public final @NonNull LinearLayout mNumbersContainer;
        public final @NonNull TextView mNameBaseText;
        public final @NonNull TextView mSizeBase;
        public final @NonNull ImageView mBackImage;


        public HeaderHolder(View view)
        {
            mAddContainer = (FrameLayout) view.findViewById(R.id.header_tables_add);
            mAddTextView = (TextView) view.findViewById(R.id.header_listview_text);
            mNumbersContainer = (LinearLayout) view.findViewById(R.id.header_numbers_container);
            mNameBaseText = (TextView) view.findViewById(R.id.name_base);
            mSizeBase = (TextView) view.findViewById(R.id.base_size);
            mBackImage = (ImageView) view.findViewById(R.id.back_image);
        }
    }
}
