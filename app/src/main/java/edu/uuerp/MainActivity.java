package edu.uuerp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itsaky.androidide.logsender.LogSender;

import edu.uuerp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding ActivityMainView;
    private BottomNavigationView bottomNav;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove this line if you don't want AndroidIDE to show this app's logs
        LogSender.startLogging(this);

        ActivityMainView = ActivityMainBinding.inflate(getLayoutInflater());
        bottomNav = ActivityMainView.bottomNavBar;
        viewPager = ActivityMainView.viewPager;
        setContentView(ActivityMainView.getRoot());

        viewPager.setAdapter(new ViewPagerAdapter(this));
        viewPager.setUserInputEnabled(false);

        bottomNav.setOnItemSelectedListener(
                (menuItem) -> {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_erp:
                            viewPager.setCurrentItem(0, false);
                            break;

                        case R.id.navigation_faculty_list:
                            viewPager.setCurrentItem(1, false);
                            break;

                        case R.id.navigation_about:
                            viewPager.setCurrentItem(2, false);
                            break;
                    }
                    return true;
                });
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ERPFragment();
                case 1:
                    return new FacultyListFragment();

                case 2:
                    return new AboutFragment();

                default:
                    return new Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
