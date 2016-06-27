package com.skypayjm.androidbootstrap.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Debug;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by sky on 20/8/2015.
 */
public class Utility {
    public static final String TAG = "Utility";
    public static final int GOOGLE_SIGNUP = 1;
    public static final int GOOGLE_SALES = 2;
    public static final int GOOGLE_REGISTER_DEEPLINK = 3;
    public static final String PREF_UTM = "utm";
    public static final String PREF_UTM_CREATED = "utm_created_at";
    public static final String PREF_SUPPORT_CHAT_START = "support_chat_started";
    public static final int SERVICES = 0;
    public static final int INFO = 1;
    public static final int REVIEWS = 2;
    public static final long SYNC_CHAT_IN_PROGRESS = 0;
    public static final long SYNC_CHAT_NOT_DONE = -1;
    public static final long SYNC_CHAT_NETWORK_ERROR = -2;

    static Utility utility;

    private Utility() {

    }

    public static Utility getInstance() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    public static boolean deleteFileFromPath(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    public Fragment getVisibleFragment(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();
        if (fragmentManager.getFragments() != null) {
            return fragmentManager.getFragments().get(count > 0 ? count - 1 : count);
        }
        return null;
    }

    /**
     * @param string the string to be formatted
     * @param color  has to be in the format of a hex code
     * @return formatted string
     */
    public String formatStringColor(String string, int color) {
        String сolorString = String.format("%X", color);
        if (String.format("%X", color).length() == 8) {
            сolorString = String.format("%X", color).substring(2); //strip away the alpha portion
        }
        return String.format("<font color=\"#%s\">%s</font>", сolorString, string);
    }

    /**
     * This method returns true if the collection is null or is empty.
     *
     * @param collection collection object
     * @return true | false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * This method returns true of the map is null or is empty.
     *
     * @param map map object
     * @return true | false
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * This method returns true if the objet is null.
     *
     * @param object object
     * @return true | false
     */
    public static boolean isEmpty(Object object) {
        return object == null;
    }

    /**
     * This method returns true if the input array is null or its length is zero.
     *
     * @param array array object
     * @return true | false
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * This method returns true if the input string is null or its length is zero.
     *
     * @param string string object
     * @return true | false
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void changeTabsFont(TabLayout tabLayout, Typeface tf) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf);
                }
            }
        }
    }

    public static int containerHeight(Activity ba) {
        DisplayMetrics dm = new DisplayMetrics();
        ba.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Timber.d("Screen Height of " + Build.MANUFACTURER + " " + Build.DEVICE + " "
                + Build.MODEL + " is " + Integer.toString(dm.heightPixels));

        return dm.heightPixels;
    }

    public static int containerWidth(Activity ba) {
        DisplayMetrics dm = new DisplayMetrics();
        ba.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Timber.d("Screen Width of " + Build.MANUFACTURER + " " + Build.DEVICE + " "
                + Build.MODEL + " is " + Integer.toString(dm.widthPixels));

        return dm.widthPixels;
    }

    public static void colorizeToolbar(Toolbar toolbarView, int toolbarIconsColor) {
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbarView.getChildCount(); i++) {
            final View v = toolbarView.getChildAt(i);

            //Step 1 : Changing the color of back button (or open drawer button).
            if (v instanceof ImageButton) {
                //Action Bar back button
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }

            //Step 2: Changing the color of title and subtitle.
            toolbarView.setTitleTextColor(toolbarIconsColor);
            toolbarView.setSubtitleTextColor(toolbarIconsColor);

        }
    }

    public static void logHeap() {
        Double allocated = (double) Debug.getNativeHeapAllocatedSize() / (double) (1048576);
        Double available = (double) Debug.getNativeHeapSize() / 1048576.0;
        Double free = (double) Debug.getNativeHeapFreeSize() / 1048576.0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Log.d("MEMORY", "debug. =================================");
        Log.d("MEMORY", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
        Log.d("MEMORY", "debug.memory: allocated: " + df.format((double) (Runtime.getRuntime().totalMemory() / 1048576)) + "MB of " + df.format((double) (Runtime.getRuntime().maxMemory() / 1048576)) + "MB (" + df.format((double) (Runtime.getRuntime().freeMemory() / 1048576)) + "MB free)");
    }

    public static void cleanupDrawables(View view) {
        cleanupDrawable(view.getBackground());

        if (view instanceof ImageView)
            cleanupDrawable(((ImageView) view).getDrawable());
        else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            Drawable[] compounds = tv.getCompoundDrawables();
            for (Drawable compound : compounds) cleanupDrawable(compound);
        } else if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++)
                cleanupDrawables(vg.getChildAt(i));
            vg.removeAllViews();
        }
    }

    public static void cleanupDrawable(Drawable d) {
        if (d == null)
            return;

        d.setCallback(null);

        if (d instanceof BitmapDrawable)
            ((BitmapDrawable) d).getBitmap().recycle();
        else if (d instanceof LayerDrawable) {
            LayerDrawable layers = (LayerDrawable) d;
            for (int i = 0; i < layers.getNumberOfLayers(); i++)
                cleanupDrawable(layers.getDrawable(i));
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

    public static void printMap(Map<String, ?> params) {
        if (!Utility.isEmpty(params)) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                Timber.d(entry.getKey() + " --> " + entry.getValue());
            }
        }
    }
}
