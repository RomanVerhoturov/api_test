

package istu.edu.irnitu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import istu.edu.irnitu.adapters.TabsFragmentAdapter;
import istu.edu.irnitu.fragments.AbstractTabFragment;
import istu.edu.irnitu.fragments.EventsFragment;
import istu.edu.irnitu.fragments.NewsFragment;
import istu.edu.irnitu.fragments.ResourceFragment;
import istu.edu.irnitu.fragments.ScheduleFragment;


public class MainActivity extends AppCompatActivity implements EventsFragment.OnFragmentInteractionListener{
    private static final String LOG_TAG = "LOG_TAG_MAIN";
    private ArrayList<AbstractTabFragment> tabFragmentList;


    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabsFragmentAdapter adapter;
    private TabLayout tabLayout;
    private String userCookies;
    private FloatingActionButton fabBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_logo_irnitu);
        initTabs(R.id.viewPager_main, R.id.tabLayout_main);

    }

    private void initTabs(int viewPagerId, int tabLayoutId) {
        tabFragmentList = new ArrayList<>();
        tabFragmentList.add(0, ResourceFragment.getInstance(MainActivity.this, userCookies));
        tabFragmentList.add(1, NewsFragment.getInstance(MainActivity.this, userCookies));
        tabFragmentList.add(2, EventsFragment.getInstance(MainActivity.this, userCookies));
        tabFragmentList.add(3, ScheduleFragment.getInstance(MainActivity.this));

        viewPager = (ViewPager) findViewById(viewPagerId);
        adapter = new TabsFragmentAdapter(MainActivity.this, getSupportFragmentManager(), tabFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) findViewById(tabLayoutId);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //getTabLayout().setTabGravity(TabLayout.GRAVITY_CENTER);
        //дополнять слушателем здесь
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(LOG_TAG, "MainActivity onPageSelected " + String.valueOf(position));


                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2: {
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 2) {
//            ScheduleFragment fragment = (ScheduleFragment) adapter.getItem(2);
//            if (fragment != null && fragment.getmWebView().canGoBack()) {
//                fragment.getmWebView().goBack();
//            } else {
//                super.onBackPressed();
//            }
//        } else if (viewPager.getCurrentItem() == 3) {
//            ScheduleFragment fragment = (ScheduleFragment) adapter.getItem(3);
//            if (fragment != null && fragment.getmWebView().canGoBack()) {
//                fragment.getmWebView().goBack();
//            } else {
//                super.onBackPressed();
//            }
//        } else {
            super.onBackPressed();
 //       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
