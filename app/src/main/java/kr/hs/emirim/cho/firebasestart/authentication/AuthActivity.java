package kr.hs.emirim.cho.firebasestart.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kr.hs.emirim.cho.firebasestart.R;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button firebaseuibtn = findViewById(R.id.firebase_ui_btn);
        firebaseuibtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firebase_ui_btn :
                Intent i = new Intent(this, FirebaseUIActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}