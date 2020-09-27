package kr.hs.emirim.cho.firebasestart.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import kr.hs.emirim.cho.firebasestart.R;

public class FirebaseUIActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_u_i);

        Button firebaseuiauthbtn = findViewById(R.id.firebase_ui_auth_btn);
        firebaseuiauthbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.firebase_ui_auth_btn :
                signIn();
                break;
            default:
                break;
        }
    }

    private void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(AuthUI.getDefaultTheme())
                        .setLogo(R.drawable.ic_launcher_background)
                        .setAvailableProviders(getSelectedProviders())
                        .setTosAndPrivacyPolicyUrls("https://naver.com", "https://google.com")
                        .setIsSmartLockEnabled(true)
                        .build(), RC_SIGN_IN);
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        CheckBox googlechk = (CheckBox)findViewById(R.id.google_provider);
        CheckBox facebookchk = (CheckBox)findViewById(R.id.facebook_provider);
        CheckBox emailchk = (CheckBox)findViewById(R.id.email_provider);
        CheckBox githubchk = (CheckBox)findViewById(R.id.github_provider);

        if(googlechk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        }
        if(facebookchk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        }
        if(emailchk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
        }
        if(githubchk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.GitHubBuilder().build());
        }
        return selectedProviders;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                Intent i = new Intent(this, SignedInActivity.class);
                i.putExtras(data);
                startActivity(i);

                Toast.makeText(this,"로그인에 성공하셨습니다.",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,"로그인에 실패하셨습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
