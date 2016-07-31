package ru.stefa.tizarhunter.stefasms.screens.main.numbers.newbasedialog;

import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.NewBaseDialogModel;

/**
 * Created by Danil on 28.07.2016.
 */
public class NewBaseDialogItem extends BindableViewHolder<NewBaseDialogModel,
        NewBaseDialogItem.NewBaseDialogItemListener> {

    AppCompatRadioButton radioButton;

    public NewBaseDialogItem(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        radioButton = (AppCompatRadioButton) itemView.findViewById(R.id.new_base_dialog_item_radio);
    }

    @Override
    public void bindView(int position, NewBaseDialogModel item, NewBaseDialogItemListener actionListener) {
        super.bindView(position, item, actionListener);
        radioButton.setText(item.getName());
        radioButton.setChecked(item.isCheck());
    }

    interface NewBaseDialogItemListener extends BindableViewHolder.ActionListener<NewBaseDialogModel> {}
}
