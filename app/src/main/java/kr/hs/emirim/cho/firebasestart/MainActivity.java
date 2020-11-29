package kr.hs.emirim.cho.firebasestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.hs.emirim.cho.firebasestart.authentication.AuthActivity;
import kr.hs.emirim.cho.firebasestart.cloudstorage.CloudStorageActivity;
import kr.hs.emirim.cho.firebasestart.cloudstorage.UploadActivity;
import kr.hs.emirim.cho.firebasestart.firestore.FirestoreActivity;
import kr.hs.emirim.cho.firebasestart.hosting.HostingActivity;
import kr.hs.emirim.cho.firebasestart.realtimedb.MemoActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firebaseauthbtn = (Button) findViewById(R.id.firebaseauthbtn);
        firebaseauthbtn.setOnClickListener(this);

        Button firebaserealdbbtn = (Button) findViewById(R.id.firebaserealtimedbbtn);
        firebaserealdbbtn.setOnClickListener(this);

        Button firebasefirestorebtn=(Button)findViewById(R.id.firebasefirestorebtn);
        firebasefirestorebtn.setOnClickListener(this);

        Button firebasestoragebtn=(Button)findViewById(R.id.firebasestoragebtn);
        firebasestoragebtn.setOnClickListener(this);

        Button firebasehostingebtn=(Button)findViewById(R.id.firebasehostingebtn);
        firebasehostingebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(this,"버튼 클릭", Toast.LENGTH_SHORT).show();
        Log.d("파베: Main", "파베어스 버튼 눌림!");

        switch (view.getId()) {
            case R.id.firebaseauthbtn:
                Intent i = new Intent(this, AuthActivity.class);
                startActivity(i);
                break;
            case R.id.firebaserealtimedbbtn :
                Intent i2 = new Intent(this, MemoActivity.class);
                startActivity(i2);
                break;
            case R.id.firebasefirestorebtn:
                Intent i3=new Intent(this, FirestoreActivity.class);
                startActivity(i3);
                break;
            case R.id.firebasestoragebtn:
                Intent i4=new Intent(this, CloudStorageActivity.class);
                startActivity(i4);
                break;
            case R.id.firebasehostingebtn:
                Log.d("MainActivity====>", "파이어베이스 클라우드호스팅 버튼 눌림!");
                Intent i5=new Intent(this, HostingActivity.class);
                startActivity(i5);
                break;
            default:
                break;
        }
    }
}