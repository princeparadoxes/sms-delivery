package ru.stefa.tizarhunter.stefasms.files;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FilesActions
{
    private Context mContext;

    public FilesActions(Context context)
    {
        mContext = context;
    }

    public ArrayList<String> readFileSD(String path)
    {
        ArrayList<String> numbers = new ArrayList<String>();

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(mContext, "SD-карта не доступна: " + Environment.getExternalStorageState(),
                    Toast.LENGTH_LONG).show();
            return numbers;
        }
        File numberFile = new File(path);
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(numberFile));

            String str = "";
            while ((str = br.readLine()) != null)
            {
                numbers.add(str);
            }

        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(mContext, "Ошибка. Файл не найден", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Toast.makeText(mContext, "Ошибка", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return numbers;
    }

}
