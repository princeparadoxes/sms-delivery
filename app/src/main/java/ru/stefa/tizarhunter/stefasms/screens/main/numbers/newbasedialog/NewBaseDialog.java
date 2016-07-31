package ru.stefa.tizarhunter.stefasms.screens.main.numbers.newbasedialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.CreateBaseType;
import ru.stefa.tizarhunter.stefasms.data.models.NewBaseDialogModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.misc.met.validators.EmptyValidator;
import ru.stefa.tizarhunter.stefasms.misc.met.validators.IfBaseNameExistValidator;

public class NewBaseDialog extends AppCompatDialog implements NewBaseDialogItem
        .NewBaseDialogItemListener {
    private MaterialEditText name;
    private RecyclerView recyclerView;
    private View okButton;
    private View cancelButton;

    private NewBaseDialogListener newBaseDialogListener;
    private SimpleBindableAdapter<NewBaseDialogModel> adapter;
    private NewBaseDialogModel selectedItem;

    public NewBaseDialog(final Context context, List<NumbersBaseModel> basesList) {
        super(context, R.style.AlertDialogTheme);
        setTitle(R.string.new_base_dialog_title);
        setContentView(R.layout.new_base_dialog);
        findViews();
        initViews(basesList);
    }

    private void findViews() {
        name = (MaterialEditText) findViewById(R.id.new_base_dialog_name);
        recyclerView = (RecyclerView) findViewById(R.id.new_base_dialog_recycler);
        okButton = findViewById(R.id.new_base_dialog_ok);
        cancelButton = findViewById(R.id.new_base_dialog_cancel);
    }

    private void initViews(List<NumbersBaseModel> basesList) {
        name.addValidator(new EmptyValidator(getContext()));
        name.addValidator(new IfBaseNameExistValidator(getContext(), basesList));

        adapter = new SimpleBindableAdapter<>(R.layout.new_base_dialog_item,
                NewBaseDialogItem.class);
        adapter.setActionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addAll(NewBaseDialogModel.getAllTypes());
        selectItem(adapter.getItem(CreateBaseType.IMPORT_FROM_FILE.ordinal()));

        okButton.setOnClickListener(v -> {
            if (newBaseDialogListener == null || !name.validate()) return;
            newBaseDialogListener.onOkButtonClick(name.getText().toString(), selectedItem);
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());
    }

    @Override
    public void OnItemClickListener(int position, NewBaseDialogModel item) {
        deselectLast();
        selectItem(item);
    }

    private void deselectLast() {
        selectedItem.setCheck(false);
        adapter.notifyItemChanged(adapter.indexOf(selectedItem));
    }

    private void selectItem(NewBaseDialogModel item) {
        item.setCheck(true);
        adapter.notifyItemChanged(adapter.indexOf(item));
        selectedItem = item;
    }

    public NewBaseDialog setNewBaseDialogListener(NewBaseDialogListener newBaseDialogListener) {
        this.newBaseDialogListener = newBaseDialogListener;
        return this;
    }

    public interface NewBaseDialogListener {
        void onOkButtonClick(String name, NewBaseDialogModel newBaseDialogModel);
    }
}
