package in.brainwired.www.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView username_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }

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
            case R.id.menuLogout:
                                SharedPrefManager.getInstance(this).LogOut();
                                finish();
                                startActivity(new Intent(this,LoginActivity.class));
                                break;
        }
        return true;
    }
}
