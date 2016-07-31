package ru.stefa.tizarhunter.stefasms.screens.main.numbers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.SmsApplication;
import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.files.FilesActions;
import ru.stefa.tizarhunter.stefasms.data.files.OpenFileDialog;
import ru.stefa.tizarhunter.stefasms.data.models.CreateBaseType;
import ru.stefa.tizarhunter.stefasms.data.models.NewBaseDialogModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.screens.main.numbers.newbasedialog.NewBaseDialog;

public class NumberBaseListFragment extends Fragment implements NumberBaseListItem
        .NumberBaseListItemListener, NewBaseDialog.NewBaseDialogListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private SimpleBindableAdapter<NumbersBaseModel> adapter;
    private DataService dataService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_number_base_list, container, false);
        dataService = ((SmsApplication) getActivity().getApplication()).getDataService();
        findViews(rootView);
        initViews();
        loadData();
        return rootView;
    }

    private void findViews(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.number_base_list_recycler);
        fab = (FloatingActionButton) root.findViewById(R.id.number_base_list_fab);
    }

    private void initViews() {
        adapter = new SimpleBindableAdapter<>(R.layout.number_base_list_item,
                NumberBaseListItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab.setOnClickListener(v -> {
            NewBaseDialog newBaseDialog = new NewBaseDialog(getActivity(), adapter.getItems());
            newBaseDialog.setNewBaseDialogListener(this);
            newBaseDialog.show();
        });
    }

    private void loadData() {
        dataService.getNumbersBaseList()
                .compose(DataService.applySchedulers())
                .subscribe(adapter::addAll,
                        throwable -> {
                            //TODO show error
                        });
    }

    @Override
    public void OnItemClickListener(int position, NumbersBaseModel item) {

    }

    @Override
    public void onOkButtonClick(String name, NewBaseDialogModel newBaseDialogModel) {
        switch (CreateBaseType.getTypeForId(newBaseDialogModel.getId())) {
            case IMPORT_FROM_FILE:
                OpenFileDialog fileDialog = new OpenFileDialog(getActivity()).setOpenDialogListener(fileName -> {
                    FilesActions filesActions = new FilesActions(getActivity());
                    ArrayList<String> numbers = filesActions.readFileSD(fileName);
                    dataService.createNumbersBase(name, numbers)
                            .compose(DataService.applySchedulers())
                            .subscribe(model -> {
                                adapter.add(model);
                                //TODO open activity with numbers
                            }, throwable -> {
                                //TODO show error
                            });
                });
                dataService.createNumbersBase(name)
                        .compose(DataService.applySchedulers())
                        .subscribe(nameBase -> {
                            fileDialog.show();
                        }, throwable -> {
                            //TODO show error
                        });

                break;
            case INPUT_FROM_KEYBOARD:
                dataService.createNumbersBase(name)
                        .compose(DataService.applySchedulers())
                        .subscribe(model -> {
                            adapter.add(model);
                            //TODO open activity with numbers
                        }, throwable -> {
                            //TODO show error
                        });
                break;
            default:
                break;
        }
    }
}