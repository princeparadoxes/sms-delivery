package ru.stefa.tizarhunter.stefasms.screens.main.send;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.SmsApplication;
import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.database.DatabaseService;
import ru.stefa.tizarhunter.stefasms.data.models.NumberBaseModel;
import ru.stefa.tizarhunter.stefasms.data.preferences.Storage;
import ru.stefa.tizarhunter.stefasms.screens.main.send.select.base.dialog.SelectBaseDialog;

/**
 * Created by tizarhunter on 17.08.14.
 */
public class SendFragment extends Fragment implements SelectBaseDialog.SelectBaseDialogListener{
    private static final String KEY_DB_MANE = "key.db.name";

    private Button mSendButton;
    private MaterialEditText mMessageEditText;
    private Button mChooseBaseButton;
    private MaterialEditText mEditFrom;
    private MaterialEditText mEditTo;
    private TextView mCountNumbersInBase;
    private ProgressBar mProgressBar;

    private SelectBaseDialog selectBaseDialog;

    private ArrayList<String> mNumbers;
    private DatabaseService mDatabaseService;
    private DataService dataService;
    private ArrayList<Runnable> runnables = new ArrayList<>();
    View.OnClickListener mStopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (Runnable runnable : runnables) {
                mSendButton.removeCallbacks(runnable);
            }
            runnables.clear();
            mSendButton.setOnClickListener(mSendClickListener);
            mSendButton.setText(R.string.send_send);
        }
    };
    View.OnClickListener mSendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendSms();
            mSendButton.setOnClickListener(mStopClickListener);
            mSendButton.setText(R.string.send_stop_send);
        }
    };
    private String selectedBaseName;

    public SendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_send, container, false);
        mDatabaseService = new DatabaseService();
        mDatabaseService.connectionDatabase(getActivity());
        dataService = ((SmsApplication) getActivity().getApplication()).getDataService();
        findViews(rootView);
        initViews();
        return rootView;
    }

    private void findViews(View rootView) {
        mSendButton = (Button) rootView.findViewById(R.id.send_button_send);
        mMessageEditText = (MaterialEditText) rootView.findViewById(R.id.send_edit_message);
        mChooseBaseButton = (Button) rootView.findViewById(R.id.send_button_base);
        mEditFrom = (MaterialEditText) rootView.findViewById(R.id.send_from);
        mEditTo = (MaterialEditText) rootView.findViewById(R.id.send_to);
        mCountNumbersInBase = (TextView) rootView.findViewById(R.id.send_count_numbers_in_base);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.send_progress);
    }

    private void initViews() {
        if (selectedBaseName != null) {
            chooseDB();
        }
        mChooseBaseButton.setOnClickListener(view -> selectBaseDialog.show());
        selectBaseDialog = new SelectBaseDialog(getContext());
        selectBaseDialog.setListener(this);
        dataService.getNumberBaseList()
                .compose(DataService.applySchedulers())
                .subscribe(selectBaseDialog::bindData, throwable -> {});
    }

    private void chooseDB() {
        mChooseBaseButton.setText(getString(R.string.send_selected_base, selectedBaseName));
        mNumbers = mDatabaseService.readTableColumn(selectedBaseName, DatabaseService.NUMBER);
        mSendButton.setOnClickListener(mSendClickListener);
        mCountNumbersInBase.setVisibility(View.VISIBLE);
        mCountNumbersInBase.setText(getString(R.string.send_numbers_in_base, mNumbers.size()));
        ((View) mEditTo.getParent()).setVisibility(View.VISIBLE);
        mEditFrom.setText(String.valueOf(1));
        mEditTo.setText(String.valueOf(mNumbers.size()));
    }

    private void sendSms() {
        final String sms = mMessageEditText.getText().toString();
        final SmsManager smsManager = SmsManager.getDefault();
        mProgressBar.setVisibility(View.VISIBLE);
        Integer from = Integer.valueOf(mEditFrom.getText().toString());
        Integer to = Integer.valueOf(mEditTo.getText().toString());
        int offset = 0;
        int sended = 0;
        final List<String> numbers = mNumbers.subList(from - 1, to);
        mProgressBar.setMax(numbers.size());
        for (int i = 0; i < numbers.size(); i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        smsManager.sendTextMessage(numbers.get(finalI), null, sms, null, null);
                        Toast.makeText(getActivity(), getActivity().getString(R.string
                                .send_sms_sended, finalI + 1, numbers.size()), Toast.LENGTH_SHORT).show();
                        mProgressBar.setProgress(finalI + 1);
                        if (finalI + 1 == numbers.size()) {
                            runnables.clear();
                            mSendButton.setOnClickListener(mSendClickListener);
                            mSendButton.setText(R.string.send_send);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), R.string.send_messages_not_sended, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };
            runnables.add(runnable);
            mSendButton.postDelayed(runnable, offset);
            sended++;
            if (sended < Storage.mMessagePortion.get()) {
                offset += Storage.mMessageDelay.get();
            } else {
                sended = 0;
                offset += Storage.mMessagePortionDelay.get();
            }
        }
        mDatabaseService.addToArchive(mMessageEditText.getText().toString(), numbers.size(),
                System.currentTimeMillis());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_DB_MANE, selectedBaseName);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) return;
        selectedBaseName = savedInstanceState.getString(KEY_DB_MANE);
        if (selectedBaseName != null) {
            chooseDB();
        }
    }

    @Override
    public void onSelectBaseDialogItemClick(NumberBaseModel numberBaseModel) {
        selectedBaseName = numberBaseModel.getName();
        chooseDB();
    }
}

