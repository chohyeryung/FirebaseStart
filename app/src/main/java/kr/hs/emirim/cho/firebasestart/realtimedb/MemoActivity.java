package kr.hs.emirim.cho.firebasestart.realtimedb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import kr.hs.emirim.cho.firebasestart.R;
import kr.hs.emirim.cho.firebasestart.authentication.AuthActivity;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener, MemoViewListener {

    private String uid=null;
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
            case R.id.reguser:
                writeNewUser();
                break;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        addChildEvent();
        addValueEventListener();
    }

    private void addValueEventListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("cho", "Value="+snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void regMemo() {
        if(uid==null){
            Toast.makeText(this, "메모를 추가하기 위해서는 Firebase 인증이 되어야합니다. Firebase 인증 후 다시 진행해주세요.", Toast.LENGTH_LONG).show();
            return;
        }


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

        databaseReference.child("memo").child(uid).push().setValue(item);

//        memoItems.add(item);
//        memoAdapter.notifyDataSetChanged();
    }

    private void addChildEvent(){
        databaseReference.child("memo").child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(getApplicationContext(), "새글이 등록됨", Toast.LENGTH_SHORT).show();
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
        Button userbtn=(Button)findViewById(R.id.reguser);
        userbtn.setOnClickListener(this);

        username="user_"+new Random().nextInt(1000);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            this.uid=user.getUid();
        }else{
            Toast.makeText(this, "로그인 하세요", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, AuthActivity.class));
        }
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

    private void writeNewUser(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String name=user.getDisplayName();
            Toast.makeText(getApplicationContext(), "표시 이름 : "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
            String email=user.getEmail();
            String uid=user.getUid();

            UserInfo userInfo=new UserInfo();
            userInfo.setUserpwd("1234");
            userInfo.setUsername(name);
            userInfo.setEmailaddr(email);
            //메인페이지로

            databaseReference.child("users").child(uid).setValue(userInfo);
        }else{
            //로그인페이지로
            Toast.makeText(getApplicationContext(), "그런 사용자 없음", Toast.LENGTH_SHORT).show();
        }



        //databaseReference.child("users").push().setValue(userInfo);
    }

    @Override
    public void OnItemClick(int position, View view) {

    }
}