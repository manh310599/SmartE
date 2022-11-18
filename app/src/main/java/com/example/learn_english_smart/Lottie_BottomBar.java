package com.example.learn_english_smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.wwdablu.soumya.lottiebottomnav.FontBuilder;
import com.wwdablu.soumya.lottiebottomnav.FontItem;
import com.wwdablu.soumya.lottiebottomnav.ILottieBottomNavCallback;
import com.wwdablu.soumya.lottiebottomnav.LottieBottomNav;
import com.wwdablu.soumya.lottiebottomnav.MenuItem;
import com.wwdablu.soumya.lottiebottomnav.MenuItemBuilder;

import java.util.ArrayList;

public class Lottie_BottomBar extends AppCompatActivity implements ILottieBottomNavCallback{

    FragmentTransaction transaction = null;
    LottieBottomNav bottomNav;
    ArrayList<MenuItem> list;
    public Lottie_BottomBar activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottie_bottombar);

        bottomNav   = findViewById(R.id.bottom_nav);

        //Set font item
        FontItem fontItem = FontBuilder.create("Trang chủ")
                .selectedTextColor(Color.BLACK)
                .unSelectedTextColor(Color.GRAY)
                .selectedTextSize(16) //SP
                .unSelectedTextSize(12) //SP
                .setTypeface(Typeface.createFromAsset(getAssets(), "coffeesugar.ttf"))
                .build();

        //Menu Dashboard
        MenuItem item1 = MenuItemBuilder.create("home1.json", MenuItem.Source.Assets, fontItem, "dash")
                .pausedProgress(1f)
                .loop(false)
                .build();

        //Example Spannable String (at Menu Gifts)
        SpannableString spannableString = new SpannableString("Tra cứu");
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Menu Gifts
        fontItem = FontBuilder.create(fontItem).setTitle(spannableString).build();
        MenuItem item2 = MenuItemBuilder.createFrom(item1, fontItem)
                .selectedLottieName("notepad.json")
                .unSelectedLottieName("notepad.json")
                .loop(true)
                .build();

        //Menu Mail
        fontItem = FontBuilder.create(fontItem).setTitle("Học phần").build();
        MenuItem item3 = MenuItemBuilder.createFrom(item1, fontItem)
                .selectedLottieName("term.json")
                .unSelectedLottieName("term.json")
                .pausedProgress(0.75f)
                .build();

        //Menu Settings
        fontItem = FontBuilder.create(fontItem).setTitle("Cài đặt").build();
        MenuItem item4 = MenuItemBuilder.createFrom(item1, fontItem)
                .selectedLottieName("settings.json")
                .unSelectedLottieName("settings.json")
                .build();

        list = new ArrayList<>(4);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        bottomNav.setCallback(this);
        bottomNav.setMenuItemList(list);
        bottomNav.setSelectedIndex(0); //first selected index

        //First selected fragment
        setFragment(new DashboardFragment());
    }



    public void onMenuSelected(int oldIndex, int newIndex, MenuItem menuItem) {
        switch (newIndex) {
            case 0: {
                setFragment(new DashboardFragment());
                break;
            }
            case 1: {
                setFragment(new GiftsFragment());
                break;
            }
            case 2: {
                setFragment(new MailFragment());
                break;
            }
            case 3: {
                setFragment(new SettingsFragment());
                break;
            }
        }
    }

    @Override
    public void onAnimationStart(int index, MenuItem menuItem) {

    }

    @Override
    public void onAnimationEnd(int index, MenuItem menuItem) {

    }

    @Override
    public void onAnimationCancel(int index, MenuItem menuItem) {

    }

    private void setFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }







}