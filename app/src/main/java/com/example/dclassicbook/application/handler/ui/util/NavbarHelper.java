package com.example.dclassicbook.application.handler.ui.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.ui.BookListActivity;
import com.example.dclassicbook.application.handler.ui.HomeActivity;
import com.example.dclassicbook.application.handler.ui.OurStoreActivity;

public class NavbarHelper {

    public enum Page { HOME, BOOKLIST, STORE }

    public static void setup(Activity activity, Page activePage) {
        int activeColor = activity.getColor(R.color.primary50);
        int inactiveColor = activity.getColor(R.color.accent10);

        int[] iconIds = {R.id.navIconHome, R.id.navIconBookList, R.id.navIconStore};
        int[] textIds = {R.id.navTextHome, R.id.navTextBookList, R.id.navTextStore};

        for (int id : iconIds) {
            ImageView iv = activity.findViewById(id);
            iv.setSelected(false);
            iv.setImageTintList(null);
        }
        for (int id : textIds) {
            ((TextView) activity.findViewById(id)).setTextColor(inactiveColor);
        }

        int activeIconId, activeTextId;
        switch (activePage) {
            case HOME:
                activeIconId = R.id.navIconHome;
                activeTextId = R.id.navTextHome;
                break;
            case BOOKLIST:
                activeIconId = R.id.navIconBookList;
                activeTextId = R.id.navTextBookList;
                break;
            default:
                activeIconId = R.id.navIconStore;
                activeTextId = R.id.navTextStore;
                break;
        }

        ((ImageView) activity.findViewById(activeIconId)).setSelected(true);
        ((TextView) activity.findViewById(activeTextId)).setTextColor(activeColor);

        activity.findViewById(R.id.navHome).setOnClickListener(v -> {
            if (activePage != Page.HOME) {
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.navBookList).setOnClickListener(v -> {
            if (activePage != Page.BOOKLIST) {
                Intent intent = new Intent(activity, BookListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.navStore).setOnClickListener(v -> {
            if (activePage != Page.STORE) {
                Intent intent = new Intent(activity, OurStoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.navLogout).setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            activity.finish();
        });
    }
}
