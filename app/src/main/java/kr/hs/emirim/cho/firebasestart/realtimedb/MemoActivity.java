package kr.hs.emirim.cho.firebasestart.realtimedb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import kr.hs.emirim.cho.firebasestart.R;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener, MemoViewListener {

    private ArrayList<MemoItem> memoItems = null;
    private MemoAdapter memoAdapter = null;
    private String username=null;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();

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

    @Override
    protected void onStart(){
        super.onStart();

        addChildEvent();
    }

    private void regMemo() {
        EditText titleedit=(EditText)findViewById(R.id.memotitle);
        EditText contentsedit=(EditText)findViewById(R.id.memocontents);

        if(titleedit.getText().toString().length()==0 || contentsedit.getText().toString().length()==0){
            Toast.makeText(this, "메모 제목 또는 메모 내용이 입력되지 않았습니다. 입력 후 다시 시작해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        MemoItem item=new MemoItem();
        item.setUser(this.username);
        item.setMemotitle(titleedit.getText().toString());
        item.setMemocontents(contentsedit.getText().toString());

        databaseReference.child("memo").push().setValue(item);

//        memoItems.add(item);
//        memoAdapter.notifyDataSetChanged();
    }

    private void addChildEvent(){
        databaseReference.child("memo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("cholingling", "addChildEvent in");
                MemoItem item=snapshot.getValue(MemoItem.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init(){
        memoItems=new ArrayList<>();

        username="user_"+new Random().nextInt(1000);
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