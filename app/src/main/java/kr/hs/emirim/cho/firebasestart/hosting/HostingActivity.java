package kr.hs.emirim.cho.firebasestart.hosting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.hs.emirim.cho.firebasestart.R;

public class HostingActivity extends AppCompatActivity {

    private WebView mWebView=null;

    public class WebCustomClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting);

        mWebView=(WebView)findViewById(R.id.mywebview);
        mWebView.setWebViewClient(new WebCustomClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://fir-start-face0.web.app/");
    }
}