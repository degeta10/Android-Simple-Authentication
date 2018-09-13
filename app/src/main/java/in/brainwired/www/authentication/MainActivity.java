package in.brainwired.www.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_Nav;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private FrameLayout main_Frame;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }
        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home);
        main_Frame=findViewById(R.id.main_frame);
        main_Nav=findViewById(R.id.main_page_navbar);
        homeFragment=new HomeFragment();
        chatFragment=new ChatFragment();
        setFragment(homeFragment);
        main_Nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home :    setFragment(homeFragment);
                                            getSupportActionBar().setTitle(R.string.home);
                                            return true;

                    case R.id.nav_chat :    setFragment(chatFragment);
                                            getSupportActionBar().setTitle(R.string.chat);
                                            return true;

                    default:    return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn() == false)
        {
            Intent start_intent = new Intent(MainActivity.this,StartActivity.class);
            startActivity(start_intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout_option:    goToStart();
                                        break;
        }
        return true;
    }

    public void goToStart()
    {
        SharedPrefManager.getInstance(this).LogOut();
        finish();
        Intent intent = new Intent(getApplicationContext(),StartActivity.class);
        startActivity(intent);
    }
}
