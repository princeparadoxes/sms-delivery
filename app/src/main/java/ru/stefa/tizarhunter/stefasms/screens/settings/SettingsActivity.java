package ru.stefa.tizarhunter.stefasms.screens.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.preferences.Storage;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_message_delay_edit)
    MaterialEditText mMessageDelay;
    @BindView(R.id.settings_window_size_edit)
    MaterialEditText mMessagePortion;
    @BindView(R.id.settings_window_delay_edit)
    MaterialEditText mMessagePortionDelay;
    @BindView(R.id.settings_apply)
    Button mApplyButton;

    private View.OnClickListener mApplyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Storage.mMessageDelay.set(mMessageDelay.getText().toString());
            Storage.mMessagePortion.set(mMessagePortion.getText().toString());
            Storage.mMessagePortionDelay.set(mMessagePortionDelay.getText().toString());
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        mMessageDelay.setText(Storage.mMessageDelay.getString());
        mMessagePortion.setText(Storage.mMessagePortion.getString());
        mMessagePortionDelay.setText(Storage.mMessagePortionDelay.getString());
        mApplyButton.setOnClickListener(mApplyListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
