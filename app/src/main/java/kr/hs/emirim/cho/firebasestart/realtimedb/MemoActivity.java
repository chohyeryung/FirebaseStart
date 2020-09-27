package kr.hs.emirim.cho.firebasestart.realtimedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.emirim.cho.firebasestart.R;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener, MemoViewListener {

    private ArrayList<MemoItem> memoItems = null;
    private MemoAdapter memoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        init();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memobtn:
                regMemo();
                break;
        }
    }

    private void regMemo() {
        EditText titleedit=(EditText)findViewById(R.id.memotitle);
        EditText contentsedit=(EditText)findViewById(R.id.memocontents);

        if(titleedit.getText().toString().length()==0 || contentsedit.getText().toString().length()==0){
            Toast.makeText(this, "메모 제목 또는 메모 내용이 입력되지 않았습니다. 입력 후 다시 시작해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        MemoItem item=new MemoItem();
        item.setMemotitle(titleedit.getText().toString());
        item.setMemocontents(contentsedit.getText().toString());

        memoItems.add(item);
        memoAdapter.notifyDataSetChanged();
    }

    private void init(){
        memoItems=new ArrayList<>();
    }

    private void initView(){
        Button regbtn=(Button)findViewById(R.id.memobtn);
        regbtn.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.memolist);
        memoAdapter=new MemoAdapter(memoItems, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);
    }

    @Override
    public void OnItemClick(int position, View view) {

    }
}