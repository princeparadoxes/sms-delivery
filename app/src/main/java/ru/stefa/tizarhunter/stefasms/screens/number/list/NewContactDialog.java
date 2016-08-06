package ru.stefa.tizarhunter.stefasms.screens.number.list;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.models.ContactModel;

public class NewContactDialog extends AppCompatDialog {

    @BindView(R.id.new_contact_dialog_number)
    MaterialEditText number;
    @BindView(R.id.new_contact_dialog_ok)
    View okButton;
    @BindView(R.id.new_contact_dialog_cancel)
    View cancelButton;

    private NewContactDialogListener newContactDialogListener;

    public NewContactDialog(final Context context) {
        super(context);
        setTitle(R.string.new_contact_dialog_title);
        setContentView(R.layout.new_contact_dialog);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//        number.setText("+7");

        okButton.setOnClickListener(v -> {
            if (newContactDialogListener == null || !number.validate()) return;
            newContactDialogListener.onOkButtonClick(new ContactModel());
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());
    }

    public NewContactDialog setNewContactDialogListener(NewContactDialogListener newContactDialogListener) {
        this.newContactDialogListener = newContactDialogListener;
        return this;
    }

    public interface NewContactDialogListener {
        void onOkButtonClick(ContactModel contactModel);
    }
}
