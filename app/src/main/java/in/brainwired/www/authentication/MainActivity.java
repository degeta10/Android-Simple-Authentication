package in.brainwired.www.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView username_Text;
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
        getSupportActionBar().setTitle(R.string.app_name);
        username_Text=findViewById(R.id.username);
        username_Text.setText(SharedPrefManager.getInstance(this).getUsername());

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
