package ru.princeparadoxes.smsdelivery.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.base.ActivityConnector;

import javax.inject.Inject;

@ApplicationScope
public class ApplicationSwitcher extends ActivityConnector<Activity> {

    @Inject
    public ApplicationSwitcher() {
    }

    public void openFeedbackEmailApplication() {
        final Activity activity = getAttachedObject();
        if (activity == null) return;

//        String msg = "Short about device:\n" + DeviceManagerTools.getDetailDeviceInfo() + "\n----\n";
        String email = activity.getResources().getString(R.string.feedback_email);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        activity.startActivity(Intent.createChooser(emailIntent,
                activity.getResources().getString(R.string.feedback_open_though)));
    }

    public void openGooglePlayAppPage() {
        final Activity activity = getAttachedObject();
        if (activity == null) return;

        final String appPackageName = activity.getPackageName();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
