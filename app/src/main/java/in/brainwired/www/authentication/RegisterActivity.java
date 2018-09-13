package in.brainwired.www.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout register_Username,register_Password,register_Email;
    private Button register_Button;
    private TextView header_Text;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_Username = findViewById(R.id.register_username);
        register_Email = findViewById(R.id.register_email);
        register_Password = findViewById(R.id.register_password);

        register_Button=findViewById(R.id.register_button);
        header_Text=findViewById(R.id.header_text);
        dialog = new ProgressDialog(this);

        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    public void goToLogin()
    {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerUser() {
        final String username = register_Username.getEditText().getText().toString();
        final String email = register_Email.getEditText().getText().toString();
        final String password = register_Password.getEditText().getText().toString();

        dialog.setMessage("Registering User...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            Toast.makeText(RegisterActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            if (!jsonObject.getBoolean("error"))
                            {
                                goToLogin();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this,"JSON Error",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(request);

    }
}
