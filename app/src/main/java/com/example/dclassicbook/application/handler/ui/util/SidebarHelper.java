package com.example.dclassicbook.application.handler.ui.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.ui.BookListActivity;
import com.example.dclassicbook.application.handler.ui.HomeActivity;
import com.example.dclassicbook.application.handler.ui.OurStoreActivity;

public class SidebarHelper {

    public enum Page { HOME, BOOKLIST, STORE }

    public static void setup(Activity activity, DrawerLayout drawerLayout, Page activePage) {
        int activeColor = activity.getColor(R.color.primary50);
        int inactiveColor = activity.getColor(R.color.accent10);

        int[] iconIds = {R.id.iconHome, R.id.iconBookList, R.id.iconOurStores};
        int[] textIds = {R.id.textHome, R.id.textBookList, R.id.textOurStores};

        for (int id : iconIds) {
            ((ImageView) activity.findViewById(id)).setImageTintList(ColorStateList.valueOf(inactiveColor));
        }
        for (int id : textIds) {
            ((TextView) activity.findViewById(id)).setTextColor(inactiveColor);
        }

        int activeIconId, activeTextId;
        switch (activePage) {
            case HOME:
                activeIconId = R.id.iconHome;
                activeTextId = R.id.textHome;
                break;
            case BOOKLIST:
                activeIconId = R.id.iconBookList;
                activeTextId = R.id.textBookList;
                break;
            default:
                activeIconId = R.id.iconOurStores;
                activeTextId = R.id.textOurStores;
                break;
        }

        ((ImageView) activity.findViewById(activeIconId)).setImageTintList(ColorStateList.valueOf(activeColor));
        ((TextView) activity.findViewById(activeTextId)).setTextColor(activeColor);

        activity.findViewById(R.id.btnHamburg).setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));

        activity.findViewById(R.id.sidebarHome).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if (activePage != Page.HOME) {
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.sidebarBookList).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if (activePage != Page.BOOKLIST) {
                Intent intent = new Intent(activity, BookListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.sidebarOurStores).setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if (activePage != Page.STORE) {
                Intent intent = new Intent(activity, OurStoreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            }
        });

        activity.findViewById(R.id.sidebarLogout).setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent, ActivityOptions.makeCustomAnimation(activity, 0, 0).toBundle());
            activity.finish();
        });
    }
}
