package kr.hs.emirim.cho.firebasestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import kr.hs.emirim.cho.firebasestart.authentication.AuthActivity;
import kr.hs.emirim.cho.firebasestart.cloudstorage.CloudStorageActivity;
import kr.hs.emirim.cho.firebasestart.cloudstorage.UploadActivity;
import kr.hs.emirim.cho.firebasestart.firestore.FirestoreActivity;
import kr.hs.emirim.cho.firebasestart.hosting.HostingActivity;
import kr.hs.emirim.cho.firebasestart.performance.PerformanceActivity;
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

        Button firebaseperformancebtn=(Button)findViewById(R.id.firebaseperformancebtn);
        firebaseperformancebtn.setOnClickListener(this);

        FirebaseCrashlytics.getInstance().log("안녕하세요~ 크래쉬리틱스님~");

    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(this,"버튼 클릭", Toast.LENGTH_SHORT).show();
        Log.d("파베: Main", "파베어스 버튼 눌림!");
        Intent intent;
        switch (view.getId()) {
            case R.id.firebaseauthbtn:
                intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                break;
            case R.id.firebaserealtimedbbtn :
                intent = new Intent(this, MemoActivity.class);
                startActivity(intent);
                break;
            case R.id.firebasefirestorebtn:
                intent=new Intent(this, FirestoreActivity.class);
                startActivity(intent);
                break;
            case R.id.firebasestoragebtn:
                intent=new Intent(this, CloudStorageActivity.class);
                startActivity(intent);
                break;
            case R.id.firebasehostingebtn:
                Log.d("MainActivity====>", "파이어베이스 클라우드호스팅 버튼 눌림!");
                intent=new Intent(this, HostingActivity.class);
                startActivity(intent);
                break;
            case R.id.firebaseperformancebtn:
                Log.d("MainActivity====>", "파이어베이스 퍼포먼스 버튼 눌림!");
                intent=new Intent(this, PerformanceActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}