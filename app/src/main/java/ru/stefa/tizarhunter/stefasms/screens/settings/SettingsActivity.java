package ru.stefa.tizarhunter.stefasms.screens.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.data.Storage;

public class SettingsActivity extends AppCompatActivity {

    private MaterialEditText mMessageDelay;
    private MaterialEditText mMessagePortion;
    private MaterialEditText mMessagePortionDelay;
    private Button mApplyButton;
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
        findViews();
        initViews();
    }

    private void findViews() {
        mMessageDelay = (MaterialEditText) findViewById(R.id.settings_message_delay_edit);
        mMessagePortion = (MaterialEditText) findViewById(R.id.settings_window_size_edit);
        mMessagePortionDelay = (MaterialEditText) findViewById(R.id.settings_window_delay_edit);
        mApplyButton = (Button) findViewById(R.id.settings_apply);
    }

    private void initViews() {
        mMessageDelay.setText(Storage.mMessageDelay.getString());
        mMessagePortion.setText(Storage.mMessagePortion.getString());
        mMessagePortionDelay.setText(Storage.mMessagePortionDelay.getString());
        mApplyButton.setOnClickListener(mApplyListener);
    }
}
