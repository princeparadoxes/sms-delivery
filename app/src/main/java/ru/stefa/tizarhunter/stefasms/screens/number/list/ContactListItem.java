package ru.stefa.tizarhunter.stefasms.screens.number.list;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.ContactModel;

/**
 * Created by Danil on 25.07.2016.
 */
public class ContactListItem extends BindableViewHolder<ContactModel,
        ContactListItem.NumberListItemListener> {
    @BindView(R.id.contact_list_item_number)
    TextView number;

    public ContactListItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(int position, ContactModel item, NumberListItemListener actionListener) {
        super.bindView(position, item, actionListener);
        number.setText(item.getNumber());
    }

    interface NumberListItemListener extends BindableViewHolder.ActionListener<ContactModel> {
    }
}
