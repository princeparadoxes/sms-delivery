package ru.stefa.tizarhunter.stefasms.data.files;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ru.stefa.tizarhunter.stefasms.R;

public class FilesActions {
    private Context mContext;

    public FilesActions(Context context) {
        mContext = context;
    }

    public ArrayList<String> readFileSD(String path) {
        ArrayList<String> numbers = new ArrayList<String>();

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, mContext.getString(R.string.files_sd_not_access) + Environment.getExternalStorageState(),
                    Toast.LENGTH_LONG).show();
            return numbers;
        }
        File numberFile = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(numberFile));

            String str = "";
            while ((str = br.readLine()) != null) {
                numbers.add(str);
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, R.string.files_file_not_fount, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(mContext, R.string.files_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return numbers;
    }

}
