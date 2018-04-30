package udacity.designvilla;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.splashscreenjava.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rom4ek.arcnavigationview.ArcNavigationView;

import java.util.ArrayList;

import udacity.designvilla.Util.SystemBarColorScheme;
import udacity.designvilla.home.StaggeredRecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ArcNavigationView mNavigationView;
    private Toolbar mToolbar;
    private FirebaseUser mUser;

    private static final String TAG = "HomeActivity";
    public static final int COLUMNS = 2;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mDrawerLayout = findViewById(R.id.home_navigation_layout);
        mNavigationView = findViewById(R.id.home_navigation_view);

        initToolbar();

       /* mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.drawer_icon);*/


        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.main_layout, new HomeFragment());
        tx.commit();


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as selected to persist highlight
                item.setChecked(true);
                Fragment fragment = new HomeFragment();

                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.favourites:
                        fragment = new FavouritesFragment();
                        break;
                    case R.id.about:
                        //TODO:Design About layout layout_about
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setView(R.layout.layout_about);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case R.id.sign_out:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(HomeActivity.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.exit:
                        //TODO:Implement exit dialog
                        break;
                    default:
                        fragment = new HomeFragment();
                }

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_layout, fragment);
                transaction.commit();
                //close the navigation bar when clicked
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        initImageBitmaps();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting,menu);
        SystemBarColorScheme.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SystemBarColorScheme.setSystemBarColor(this, R.color.grey_5);
        SystemBarColorScheme.setSystemBarLight(this);
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: called");

        imageUrls.add("http://www.tellmehow.co/wp-content/uploads/2017/06/ezgif.com-optimize-696x522.gif");
        titles.add("Sample 1");

        imageUrls.add("https://cdn.dribbble.com/users/296956/screenshots/2378854/attachments/457538/real-pixels.jpg");
        titles.add("Sample 2");

        imageUrls.add("https://cdn.dribbble.com/users/1242930/screenshots/2895164/car_insurance_app.jpg");
        titles.add("Sample 3");

        imageUrls.add("http://www.uishe.cn/wp-content/uploads/2017/11/tida-side-nav-and-login-ui@2x.jpg");
        titles.add("Sample 4");

        imageUrls.add("http://img.wdjimg.com/image/orion/8f56af0e1e38f7086fe3592202628d89_800_600.jpeg");
        titles.add("Sample 5");

        imageUrls.add("https://68.media.tumblr.com/ab4158fd14a3a5a5b9c376d71bfbfde1/tumblr_oexr83oacE1s5f7v4o1_1280.png");
        titles.add("Sample 6");

        imageUrls.add("https://instagram.fixj1-1.fna.fbcdn.net/vp/bdb00513792a3d04c1b10626de1870b5/5B6805DF/t51.2885-15/e35/17126561_841999502605622_5775365166275231744_n.jpg");
        titles.add("Sample 7");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerView");

        RecyclerView recyclerView = findViewById(R.id.staggered_recycler_view);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(this, titles, imageUrls);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(COLUMNS,
                LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}
