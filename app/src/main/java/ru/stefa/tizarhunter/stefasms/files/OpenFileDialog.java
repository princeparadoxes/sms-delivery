package ru.stefa.tizarhunter.stefasms.files;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.R;

public class OpenFileDialog extends AlertDialog.Builder
{

    private String mCurrentPath = Environment.getExternalStorageDirectory().getPath();
    private List<File> mFiles = new ArrayList<File>();
    private TextView mTitle;
    private ListView mListView;
    private FilenameFilter mFilenameFilter;
    private int mSelectedIndex = -1;
    private OpenDialogListener mListener;
    private Drawable mFolderIcon;
    private Drawable mFileIcon;
    private String mAccessDeniedMessage;

    public interface OpenDialogListener
    {
        public void OnSelectedFile(String fileName);
    }

    private class FileAdapter extends ArrayAdapter<File>
    {

        public FileAdapter(Context context, List<File> files)
        {
            super(context, android.R.layout.simple_list_item_1, files);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(R.color.number_listview_element_text);
            File file = getItem(position);
            view.setText(file.getName());
            if (file.isDirectory())
            {
                setDrawable(view, mFolderIcon);
            }
            else
            {
                setDrawable(view, mFileIcon);
                if (mSelectedIndex == position)
                {
                    view.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
                }
                else
                {
                    view.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
                }
            }
            return view;
        }

        private void setDrawable(TextView view, Drawable drawable)
        {
            if (view != null)
            {
                if (drawable != null)
                {
                    drawable.setBounds(0, 0, 60, 60);
                    view.setCompoundDrawables(drawable, null, null, null);
                }
                else
                {
                    view.setCompoundDrawables(null, null, null, null);
                }
            }
        }
    }

    public OpenFileDialog(Context context)
    {
        super(context);
        mTitle = createTitle(context);
        changeTitle();
        LinearLayout linearLayout = createMainLayout(context);
        linearLayout.addView(createBackItem(context));
        mListView = createListView(context);
        linearLayout.addView(mListView);
        setCustomTitle(mTitle).setView(linearLayout).setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (mSelectedIndex > -1 && mListener != null)
                {
                    mListener.OnSelectedFile(mListView.getItemAtPosition(mSelectedIndex).toString());
                }
            }
        }).setNegativeButton(android.R.string.cancel, null);
    }

    @Override
    public AlertDialog show()
    {
        mFiles.addAll(getFiles(mCurrentPath));
        mListView.setAdapter(new FileAdapter(getContext(), mFiles));
        return super.show();
    }

    public OpenFileDialog setFilter(final String filter)
    {
        mFilenameFilter = new FilenameFilter()
        {

            @Override
            public boolean accept(File file, String fileName)
            {
                File tempFile = new File(String.format("%s/%s", file.getPath(), fileName));
                if (tempFile.isFile())
                {
                    return tempFile.getName().matches(filter);
                }
                return true;
            }
        };
        return this;
    }

    public OpenFileDialog setOpenDialogListener(OpenDialogListener listener)
    {
        this.mListener = listener;
        return this;
    }

    public OpenFileDialog setFolderIcon(Drawable drawable)
    {
        this.mFolderIcon = drawable;
        return this;
    }

    public OpenFileDialog setFileIcon(Drawable drawable)
    {
        this.mFileIcon = drawable;
        return this;
    }

    public OpenFileDialog setAccessDeniedMessage(String message)
    {
        this.mAccessDeniedMessage = message;
        return this;
    }

    private static Display getDefaultDisplay(Context context)
    {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    private static Point getScreenSize(Context context)
    {
        Point screeSize = new Point();
        getDefaultDisplay(context).getSize(screeSize);
        return screeSize;
    }

    private static int getLinearLayoutMinHeight(Context context)
    {
        return getScreenSize(context).y;
    }

    private LinearLayout createMainLayout(Context context)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setMinimumHeight(getLinearLayoutMinHeight(context));
        return linearLayout;
    }

    private int getItemHeight(Context context)
    {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new DisplayMetrics();
        context.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeightSmall, value, true);
        getDefaultDisplay(context).getMetrics(metrics);
        return (int) TypedValue.complexToDimension(value.data, metrics);
    }

    private TextView createTextView(Context context, int style)
    {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, style);
        int itemHeight = getItemHeight(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        textView.setMinHeight(itemHeight);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        return textView;
    }

    private TextView createTitle(Context context)
    {
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        return textView;
    }

    private TextView createBackItem(Context context)
    {
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Small);
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_arrow_back_grey600_24dp);
        drawable.setBounds(0, 0, 60, 60);
        textView.setText("Назад");
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                File file = new File(mCurrentPath);
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null)
                {
                    mCurrentPath = parentDirectory.getPath();
                    RebuildFiles(((FileAdapter) mListView.getAdapter()));
                }
            }
        });
        return textView;
    }

    public int getTextWidth(String text, Paint paint)
    {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width() + 80;
    }

    private void changeTitle()
    {
        String titleText = mCurrentPath;
        int screenWidth = getScreenSize(getContext()).x;
        int maxWidth = (int) (screenWidth * 0.99);
        if (getTextWidth(titleText, mTitle.getPaint()) > maxWidth)
        {
            while (getTextWidth("..." + titleText, mTitle.getPaint()) > maxWidth)
            {
                int start = titleText.indexOf("/", 2);
                if (start > 0)
                {
                    titleText = titleText.substring(start);
                }
                else
                {
                    titleText = titleText.substring(2);
                }
            }
            mTitle.setText("..." + titleText);
        }
        else
        {
            mTitle.setText(titleText);
        }
    }

    private List<File> getFiles(String directoryPath)
    {
        File directory = new File(directoryPath);
        List<File> fileList = Arrays.asList(directory.listFiles(mFilenameFilter));
        Collections.sort(fileList, new Comparator<File>()
        {
            @Override
            public int compare(File file, File file2)
            {
                if (file.isDirectory() && file2.isFile())
                {
                    return -1;
                }
                else if (file.isFile() && file2.isDirectory())
                {
                    return 1;
                }
                else
                {
                    return file.getPath().compareTo(file2.getPath());
                }
            }
        });
        return fileList;
    }

    private void RebuildFiles(ArrayAdapter<File> adapter)
    {
        try
        {
            List<File> fileList = getFiles(mCurrentPath);
            mFiles.clear();
            mSelectedIndex = -1;
            mFiles.addAll(fileList);
            adapter.notifyDataSetChanged();
            changeTitle();
        }
        catch (NullPointerException e)
        {
            String message = getContext().getResources().getString(android.R.string.unknownName);
            if (!mAccessDeniedMessage.equals(""))
            {
                message = mAccessDeniedMessage;
            }
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private ListView createListView(Context context)
    {
        ListView listView = new ListView(context);
        listView.setDivider(getContext().getResources().getDrawable(R.drawable.listview_devider));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l)
            {
                final ArrayAdapter<File> adapter = (FileAdapter) adapterView.getAdapter();
                File file = adapter.getItem(index);
                if (file.isDirectory())
                {
                    mCurrentPath = file.getPath();
                    RebuildFiles(adapter);
                }
                else
                {
                    if (index != mSelectedIndex)
                    {
                        mSelectedIndex = index;
                    }
                    else
                    {
                        mSelectedIndex = -1;
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return listView;
    }
}
