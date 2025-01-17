package android.eservices.dynamicfragmentmenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationInterface {

    private final static String FRAGMENT_NUMBER_KEY = "Fragment_Number";
    private final static String FRAGMENT_STORED_KEY = "Fragment_Stored";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private SelectableNavigationView navigationView;
    private SparseArray<Fragment> fragmentArray;
    private Fragment currentFragment;

    final FragmentManager fm = getSupportFragmentManager();
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationElements();

        if (savedInstanceState != null) {
            // TODO : FAIRE LA MEME CHOSE POUR LE COMPTEUR BAS NIVEAU
            //State had been saved so we need to restore the right fragment
            //No need to check the menu item because savedInstance restores automatically the view states
            //We need to store the fragment inside our array so it won't be recreated
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STORED_KEY);
            replaceFragment(currentFragment,"titre");
            fragmentArray.append(savedInstanceState.getInt(FRAGMENT_NUMBER_KEY), currentFragment);
        } else {
            //Set default screen to "My Selection", i.e. the first menu element
            navigationView.setSelectedItem(navigationView.getMenu().getItem(0));
        }

        //Let's imagine we retrieve the stored counter state, before creating the favorite Fragment
        //and then be able to update and manage its state.
        updateFavoriteCounter(3);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setupNavigationElements() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        fragmentArray = new SparseArray<>(3);
        fragmentArray.append(0,new FavoritesFragment());
        fragmentArray.append(1,new SelectedFragment());

        navigationView = findViewById(R.id.navigation);
        navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.favorites:
                        replaceFragment(fragmentArray.get(0),"Favorites");
                        return true;

                    case R.id.list:
                        replaceFragment(fragmentArray.get(1),"Selection");
                        return true;

                    case R.id.logoff:
                        logoff();
                        break;

                }

                //TODO react according to the selected item menu
                //We need to display the right fragment according to the menu item selection.
                //Any created fragment must be cached so it is only created once.
                //You need to implement this "cache" manually : when you create a fragment based on the menu item,
                //store it the way you prefer, so when you select this menu item later, you first check if the fragment already exists
                //and then you use it. If the fragment doesn't exist (it is not cached then) you get an instance of it and store it in the cache.


                //TODO when we select logoff, I want the Activity to be closed (and so the Application, as it has only one activity)

                //check in the doc what this boolean means and use it the right way ...
                // 	true to display the item as the selected item
                return false;
            }
        });
    }


    private void replaceFragment(Fragment newFragment, String title) {
        //TODO replace fragment inside R.id.fragment_container using a FragmentTransaction

        fm.beginTransaction().replace(R.id.fragment_container,newFragment).commit();
        currentFragment = newFragment;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        //lorsqu'on fait closeDrawer, le menu se repli apres selection de l'item
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void logoff() {
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void updateFavoriteCounter(int counter) {
        String counterContent = counter > 9 ? getString(R.string.counter_full) : Integer.toString(counter);
        View counterView = navigationView.getMenu().getItem(1).getActionView();
        counterView.setVisibility(counter > 0 ? View.VISIBLE : View.GONE);
        ((TextView) counterView.findViewById(R.id.counter_view)).setText(counterContent);
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we need to save the state of the current selected fragment,
        //so in case of orientation change, the right fragment will be displayed
        savedInstanceState.putInt(FRAGMENT_NUMBER_KEY, navigationView.getCheckedItem().getOrder());
        getSupportFragmentManager().putFragment(savedInstanceState, FRAGMENT_STORED_KEY, currentFragment);

    }


    //TODO saveInstanceState to handle
    //TODO first save the currently displayed fragment index using the key FRAGMENT_NUMBER_KEY, and getOrder() on the menu item
    //Reminder, to get the selected item in the menu, we can use myNavView.getCheckedItem()
    //TODO then save the current state of the fragment, you may read https://stackoverflow.com/questions/15313598/once-for-all-how-to-correctly-save-instance-state-of-fragments-in-back-stack

}
