package com.example.felix.payitoff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.felix.payitoff.dummy.DummyContent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements PayingFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    Fragment activeFragment;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            if ( activeFragment != null ) {
                        fragmentTransaction.remove(activeFragment);
            }
            // On select listeners for the tabs at the bottom
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    activeFragment =  PayingFragment.newInstance(2);
                    fragmentTransaction.add(R.id.content, activeFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        activeFragment =  PayingFragment.newInstance(2);
        // Start the first default fragment...
        FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.content, activeFragment).commit();

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
