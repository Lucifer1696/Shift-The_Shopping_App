package com.example.shift.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shift.R;
import com.example.shift.login.SignInActivity;
import com.example.shift.login.SignUpActivity;
import com.example.shift.ui.fragmentsfornav.Account;
import com.example.shift.ui.fragmentsfornav.MyCartFragment;
import com.example.shift.ui.fragmentsfornav.MyOrdersFragment;
import com.example.shift.ui.fragmentsfornav.WishList;
import com.example.shift.ui.fragmentsfornav.shopBycategory;
import com.example.shift.ui.fragmentsfornav.todays_deal;
import com.example.shift.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int SHOPBYCATEGORY_FRAGMENT = 1;
    private static final int TODAYSDEALS_FRAGMENT = 2;
    private static final int ORDERS_FRAGMENT = 3;
    private static final int MYACCOUNT_FRAGMENT = 4;
    private static final int WISHLIST_FRAGMENT = 5;
    private static final int SELLONSHIFT_FRAGMENT = 6;
    private static final int NOTIFICATION_FRAGMENT = 7;
    private static final int SETTING_FRAGMENT = 8;
    private static final int CART_FRAGMENT = 9;
    public static Boolean showCart =false;
    public boolean setSignUpFragment=false;

    private int currentFragment = -1;
    String Cusname,Cusemail;

    private FrameLayout frameLayout;
    private ImageView actionBarLogo;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private int labelConst = 1;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    TextView customernametv,customeremailtv;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //actionBarLogo = findViewById(R.id.actionbarLogo);
        setSupportActionBar(toolbar);

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        editor=shrd.edit();

        drawer = findViewById(R.id.drawer_layout);
        if(shrd.contains("CID"))
            setSignUpFragment=true;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.getMenu().getItem(0).setChecked(true);
        frameLayout = findViewById(R.id.nav_host_fragment);

        if (showCart) {
            drawer.setDrawerLockMode(labelConst);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("MY Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.opennav, R.string.closeNav);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }
        setNameEmail();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.send_feedback, null);
        alertDialog.setView(customLayout);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText editText = customLayout.findViewById(R.id.editText);
                Toast.makeText(MainActivity.this,editText.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            Dialog signInDialog = new Dialog(MainActivity.this);
            signInDialog.setContentView(R.layout.sign_in_dialog);
            signInDialog.setCancelable(true);
            signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            Button dialogSignInBtn = signInDialog.findViewById(R.id.Sign_in_btn);
            Button dialogSignUpBtn = signInDialog.findViewById(R.id.Sign_up_btn);
            Intent signUpintent = new Intent(MainActivity.this, SignUpActivity.class);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            gotoFragment("My cart", new MyCartFragment(), CART_FRAGMENT);
            return true;
        } else if (id == R.id.action_notifiaction) {
            Intent signInintent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(signInintent);
            return true;
        }else if(id == android.R.id.home){
            if(showCart){
                showCart =false;
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if(currentFragment == HOME_FRAGMENT){
                currentFragment = -1;
                super.onBackPressed();
            }else {
                if(showCart){
                    showCart = false;
                    finish();
                }else {
                    invalidateOptionsMenu();
                    gotoFragment("SHIFT",new HomeFragment(),HOME_FRAGMENT);

                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            invalidateOptionsMenu();
            gotoFragment("SHIFT",new HomeFragment(),HOME_FRAGMENT);
            drawer.close();
        } else if (id == R.id.nav_Shop_by_Category) {
            gotoFragment("Categories", new shopBycategory(), SHOPBYCATEGORY_FRAGMENT);
            drawer.close();
        } else if (id == R.id.nav_today_deals) {
            gotoFragment("Deals Of the Day", new todays_deal(), TODAYSDEALS_FRAGMENT);
            drawer.close();
        } else if (id == R.id.nav_Orders) {
            gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
            drawer.close();
        } else if (id == R.id.nav_WishList) {
            gotoFragment("My WishList", new WishList(), WISHLIST_FRAGMENT);
            drawer.close();
        } else if (id == R.id.nav_Account) {
            gotoFragment("My Account", new Account("Customer","CID"), MYACCOUNT_FRAGMENT);
            drawer.close();
        }else if (id == R.id.nav_Logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to Log out ?");
            builder.setTitle("Log out");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    editor.clear();
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }else if (id == R.id.nav_feedback) {
            showAlertDialog();
        }
        else if (id == R.id.nav_Share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent,"Share via");
            startActivity(sendIntent);
        }
        return false;
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (currentFragment != fragmentNo) {
            currentFragment = fragmentNo;
            invalidateOptionsMenu();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentno) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentno);
    }

    public void setNameEmail(){
        View navHeaderView = navigationView.getHeaderView(0);
        Cusname=shrd.getString("CName","Name ");
        Cusemail=shrd.getString("CEmail","Email");
        customernametv = (TextView) navHeaderView.findViewById(R.id.Seller_name);
        customernametv.setText(Cusname);
        customeremailtv = (TextView) navHeaderView.findViewById(R.id.customer_email);
        customeremailtv.setText(Cusemail);
    }
}