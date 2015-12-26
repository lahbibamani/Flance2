package com.aw.flance2;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.aw.flance2.catalogue.Product;
import com.aw.flance2.login.AbstractGetNameTask;
import com.aw.flance2.ui.catalogue.ProductDetailsFragment;
import com.aw.flance2.ui.catalogue.ProductsFragment;
import android.app.Fragment;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductsFragment.OnListFragmentInteractionListener {

    ImageView imageProfile;
    TextView textViewName, textViewEmail, textViewGender, textViewBirthday;
    String textName, textEmail, textGender, textBirthday, userImageUrl;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        imageProfile = (ImageView) headerLayout.findViewById(R.id.profile_image);
        textViewName = (TextView) headerLayout.findViewById(R.id.textViewNameValue);
        textViewEmail = (TextView) headerLayout.findViewById(R.id.textViewEmailValue);
        textViewGender = (TextView) headerLayout.findViewById(R.id.textViewGenderValue);
        textViewBirthday = (TextView) headerLayout.findViewById(R.id.textViewBirthdayValue);

        /**
         * get user email using intent
         */

        Intent intent = getIntent();
        textEmail = intent.getStringExtra("email_id");
        System.out.println(textEmail);
        textViewEmail.setText(textEmail);

        /**
         * get user data from google account
         */
        try {
            System.out.println("On Home Page***"
                    + AbstractGetNameTask.GOOGLE_USER_DATA);
            JSONObject profileData = new JSONObject(
                    AbstractGetNameTask.GOOGLE_USER_DATA);

            if (profileData.has("picture")) {
                userImageUrl = profileData.getString("picture");
                new GetImageFromUrl().execute(userImageUrl);
            }
            if (profileData.has("name")) {
                textName = profileData.getString("name");
                textViewName.setText(textName);
            }
           /* if (profileData.has("gender")) {
                textGender = profileData.getString("gender");
                textViewGender.setText(textGender);
            }
            if (profileData.has("birthday")) {
                textBirthday = profileData.getString("birthday");
                textViewBirthday.setText(textBirthday);
            }*/

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // update the main content by replacing fragments
        ProductsFragment fragment = new ProductsFragment();
        fragment.setMListener(this);
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

        // update selected item and title, then close the drawer
        setTitle("Gallery");


    }

    @Override
    public void onBackPressed() {
            // turn on the Navigation Drawer image;
            // this is called in the LowerLevelFragments
        toggle.setDrawerIndicatorEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            // update the main content by replacing fragments
            ProductsFragment fragment = new ProductsFragment();
            fragment.setMListener(this);
            getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

            // update selected item and title, then close the drawer
            setTitle("Gallery");

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Product product) {
        //disable the toggle menu and show up carat
        //toggle.setDrawerIndicatorEnabled(false);
        Fragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("prod", product);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
        setTitle(product.getName());
    }




    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageProfile.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
