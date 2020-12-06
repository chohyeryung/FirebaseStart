package kr.hs.emirim.cho.firebasestart.performance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.perf.metrics.HttpMetric;
import com.google.firebase.perf.metrics.Trace;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.hs.emirim.cho.firebasestart.R;

public class PerformanceActivity extends AppCompatActivity implements View.OnClickListener{

    private Trace trace;
    private static final String TAG="퍼포먼스 액티비티";
    final String IMAGE_URL="https://www.google.com/logos/doodles/2020/december-holidays-days-2-30-6753651837108830.5-s.png";
    //final String IMAGE_URL="https://asset.swarovski.com/images/$size_1450/t_swa103/b_rgb:fafafa,c_scale,dpr_3.0,f_auto,w_500/5464888_png/%ED%81%AC%EB%A6%AC%EC%8A%A4%EB%A7%88%EC%8A%A4-%ED%8A%B8%EB%A6%AC--%EA%B7%B8%EB%A6%B0-swarovski-5464888.png";
    //final String IMAGE_URL="data:image/png;base64,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";
    //final String IMAGE_URL="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";
    final String GOOGLE_URL="https://www.google.com/";


    @Override
    @AddTrace(name="onCreateTrace", enabled = true)
    protected void onCreate(Bundle savedInstanceState) {
        trace= FirebasePerformance.getInstance().newTrace("test_trace");
        trace.start();
        trace.putAttribute("onCreate", "start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        findViewById(R.id.foregroundbtn).setOnClickListener(this);
        findViewById(R.id.backgroundbtn).setOnClickListener(this);
        findViewById(R.id.networkbtn).setOnClickListener(this);
        trace.putAttribute("onCreate", "end");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundjob();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trace.stop();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.foregroundbtn:
                foregroundjob();
                break;
            case R.id.backgroundbtn:
                backgroundjob();
                break;
            case R.id.networkbtn:
                networkjob();
                break;
        }
    }

    private void foregroundjob(){
        somedo("[포어그라운드] 현재 i는 :", "foregroundjob");
    }

    private void somedo(String s, String tag) {
        trace.putAttribute(tag, "start");
        for(int i=0; i<1000; i++){
            try{
                Thread.sleep(10);
            }catch(Exception e){
                Log.e(TAG, s+i);
            }
        }
        trace.putAttribute(tag, "end");
    }

    private void backgroundjob(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                somedo("[백그라운드] 현재 i는 :", "backgroundjob");
            }
        }).start();
    }
    private void networkjob(){
        trace.putAttribute("networkjob", "start");
        loadImageFromWeb(R.id.showperformanceimg, IMAGE_URL);
        //Glide.with(this).load(IMAGE_URL).into((ImageView)findViewById(R.id.showperformanceimg));
        manualNetworkTrace();
        trace.putAttribute("networkjob", "end");
    }

    private void manualNetworkTrace() {
        try{
            byte[] data="TESTESTESTETSTSETETSTSETSE!".getBytes();
            HttpMetric metric=FirebasePerformance.getInstance().newHttpMetric(GOOGLE_URL,
                    FirebasePerformance.HttpMethod.GET);
            final URL url=new URL(GOOGLE_URL);
            metric.start();
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try{
                DataOutputStream outputStream=new DataOutputStream(conn.getOutputStream());
                outputStream.write(data);
            }catch(IOException ignored){
                ;
            }
            metric.setRequestPayloadSize(data.length);
            metric.setHttpResponseCode(conn.getResponseCode());
            conn.getInputStream();
            conn.disconnect();
            metric.stop();
        }catch(Exception e){

        };
    }

    private void loadImageFromWeb(int target, String url) {
        ImageView showImageView=findViewById(target);
        Glide.with(this)
                .load(url)
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent)))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(showImageView);
    }
}