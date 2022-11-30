package edu.uuerp;

import android.view.MenuItem;
import androidx.fragment.app.FragmentContainer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.itsaky.androidide.logsender.LogSender;

import edu.uuerp.ERPFragment;
import edu.uuerp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding ActivityMainView;
    private BottomNavigationView bottomNav;
    private FragmentContainerView fragmentContainer;

    private int startingPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove this line if you don't want AndroidIDE to show this app's logs
        LogSender.startLogging(this);

        ActivityMainView = ActivityMainBinding.inflate(getLayoutInflater());
        bottomNav = ActivityMainView.bottomNavBar;
        fragmentContainer = ActivityMainView.fragmentContainer;
        setContentView(ActivityMainView.getRoot());

        bottomNav.setOnItemSelectedListener(
                (menuItem) -> {
                    Fragment fragment = null;
                    int newPosition = 0;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_erp:
                            fragment = new ERPFragment();
                            newPosition = 0;
                            break;
                        case R.id.nav_faculty_list:
                            fragment = new FacultyListFragment();
                            newPosition = 1;
                            break;
                        case R.id.nav_about:
                            fragment = new AboutFragment();
                            newPosition = 2;
                            break;
                    }
                    if (startingPosition == newPosition) return true;
                    return loadFragment(fragment, newPosition);
                });
    }

    private boolean loadFragment(Fragment newFragment, int newPosition) {
        if (newFragment == null) return false;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // TODO: Do something about the jancky animation.
        if (startingPosition > newPosition)
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        else if (startingPosition < newPosition)
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        transaction.replace(fragmentContainer.getId(), newFragment);
        transaction.commit();

        startingPosition = newPosition;
        return true;
    }
}
