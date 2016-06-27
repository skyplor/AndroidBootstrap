package com.skypayjm.androidbootstrap.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky on 3/11/15.
 */
public class FontManager {
    private static FontManager instance;

    //    private AssetManager mgr;
    private Context mContext;

    private Map<String, Typeface> mFonts;

    private FontManager(Context context) {
//        mgr = _mgr;
        mContext = context;
        mFonts = new HashMap<>();
    }

    /**
     * Builds a typeface list by font file name, the file name are after apply used as typeface key.
     */
    public FontManager define(String[] fonts) {
        Typeface typeface;

        for (String font : fonts) {
            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + font);
            mFonts.put(font, typeface);
        }

        return this;
    }

    public static void init(Context context) {
        instance = new FontManager(context);
    }

    public static FontManager getInstance() {
        return instance;
    }

    public Typeface getFont(String asset) {
        if (mFonts.containsKey(asset))
            return mFonts.get(asset);

//        Typeface font = null;
//
//        try {
//            font = Typeface.createFromAsset(mgr, asset);
//            mFonts.put(asset, font);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (font == null) {
//            try {
//                String fixedAsset = fixAssetFilename(asset);
//                font = Typeface.createFromAsset(mgr, fixedAsset);
//                fonts.put(asset, font);
//                fonts.put(fixedAsset, font);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        return font;
        return null;
    }

    private String fixAssetFilename(String asset) {
        // Empty font filename?
        // Just return it. We can't help.
        if (Utility.isEmpty(asset))
            return asset;

        // Make sure that the font ends in '.ttf' or '.otf'
        if ((!asset.endsWith(".ttf")) && (!asset.endsWith(".otf")))
            asset = String.format("%s.ttf", asset);

        return asset;
    }
}
