package ru.stefa.tizarhunter.stefasms.screens.main.base.list.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.CreateBaseType;
import ru.stefa.tizarhunter.stefasms.data.models.NewBaseDialogModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.misc.met.validators.EmptyValidator;
import ru.stefa.tizarhunter.stefasms.misc.met.validators.IfBaseNameExistValidator;

public class NewBaseDialog extends AppCompatDialog implements NewBaseDialogItem
        .NewBaseDialogItemListener {
    @BindView(R.id.new_base_dialog_name)
    MaterialEditText name;
    @BindView(R.id.new_base_dialog_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.new_base_dialog_ok)
    View okButton;
    @BindView(R.id.new_base_dialog_cancel)
    View cancelButton;

    private NewBaseDialogListener newBaseDialogListener;
    private SimpleBindableAdapter<NewBaseDialogModel> adapter;
    private NewBaseDialogModel selectedItem;

    public NewBaseDialog(final Context context, List<NumbersBaseModel> basesList) {
        super(context, R.style.AlertDialogTheme);
        setTitle(R.string.new_base_dialog_title);
        setContentView(R.layout.new_base_dialog);
        ButterKnife.bind(this);
        initViews(basesList);
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
