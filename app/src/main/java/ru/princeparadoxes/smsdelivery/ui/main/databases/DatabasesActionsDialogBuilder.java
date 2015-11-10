package ru.princeparadoxes.smsdelivery.ui.main.databases;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ActivityConnector;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

@ApplicationScope
public class DatabasesActionsDialogBuilder extends ActivityConnector<Activity> {

    @Nullable
    private
    OnDatabasesDialogListener onDatabasesDialogListener;
    @Nullable
    private AlertDialog alertDialog;

    @OnClick(R.id.main_databases_dialog_delete)
    protected void delete() {
        if (onDatabasesDialogListener != null && alertDialog != null){
            onDatabasesDialogListener.delete();
            alertDialog.dismiss();
        }
    }

    @OnClick(R.id.main_databases_dialog_rename)
    protected void rename() {
        if (onDatabasesDialogListener != null && alertDialog != null){
            onDatabasesDialogListener.rename();
            alertDialog.dismiss();
        }
    }

    @OnClick(R.id.main_databases_dialog_move_to_top)
    protected void moveToTop() {
        if (onDatabasesDialogListener != null && alertDialog != null){
            onDatabasesDialogListener.moveToTop();
            alertDialog.dismiss();
        }
    }

    @Inject
    public DatabasesActionsDialogBuilder() {

    }

    public void setOnDatabasesDialogListener(OnDatabasesDialogListener onDatabasesDialogListener) {
        this.onDatabasesDialogListener = onDatabasesDialogListener;
    }

    public void openDialog() {
        if (alertDialog == null || (!alertDialog.isShowing())) {
            final Activity activity = getAttachedObject();
            if (activity == null) return;
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            final View chooseDialog = layoutInflater.inflate(R.layout.main_databases_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(layoutInflater.getContext());
            ButterKnife.inject(this, chooseDialog);
            builder.setView(chooseDialog);
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public interface OnDatabasesDialogListener {
        void delete();

        void rename();

        void moveToTop();
    }
}
