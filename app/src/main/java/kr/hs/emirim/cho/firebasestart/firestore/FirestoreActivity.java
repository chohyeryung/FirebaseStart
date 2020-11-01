package kr.hs.emirim.cho.firebasestart.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

import kr.hs.emirim.cho.firebasestart.R;

public class FirestoreActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseFirestore db= null;
    private ListenerRegistration eventQueryListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);

        findViewById(R.id.adddatabtn).setOnClickListener(this);
        
        findViewById(R.id.setdatabtn).setOnClickListener(this);

        findViewById(R.id.deletedocbtn).setOnClickListener(this);

        findViewById(R.id.deletefieldbtn).setOnClickListener(this);

        findViewById(R.id.select_data_btn).setOnClickListener(this);

        findViewById(R.id.select_where_data_btn).setOnClickListener(this);
        
        findViewById(R.id.firestorelistenerdatabtn).setOnClickListener(this);

        findViewById(R.id.querydatabtn).setOnClickListener(this);
        db= FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adddatabtn:
                addData();
                break;
            case R.id.setdatabtn:
                setData();
                break;
            case R.id.deletedocbtn:
                deleteDoc();
                break;
            case R.id.deletefieldbtn:
                deleteField();
                break;
            case R.id.select_data_btn:
                selectDoc();
                break;
            case R.id.select_where_data_btn:
                selectWhereDoc();
                break;
            case R.id.firestorelistenerdatabtn:
                listenerDoc();
                break;
            case R.id.querydatabtn:
                listenerQueryDoc();
                break;
        }
    }

    private void listenerQueryDoc() {
        Log.e("hi","리스너 쿼리 도큐먼트 시작");

        if(eventQueryListener != null) {
            return;
        }

        eventQueryListener =
                db.collection("users")
                        .whereEqualTo("id","hong")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                Log.e("hi","ListenerQueryDoc in 1");

                                if(error!=null) {
                                    Log.e("hi","error : "+error);
                                    return;
                                }

                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    Log.e("hi","Type"+dc.getType());
                                    switch (dc.getType()) {
                                        case ADDED:
                                            Log.e("hi","New city");
                                    }
                                }
                            }
                        });

    }

    private void listenerDoc() {
        final DocumentReference docRef=db.collection("users").document("userinfo");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("hi","Listen failed", error);
                }
                if(value!=null && value.exists()){
                    Log.e("hi","현재 데이터 : "+ value.getData());

                }else{
                    Log.e("hi","현재 데이터 : null", error);

                }
            }
        });
    }

    private void selectWhereDoc() {
        db.collection("users").whereEqualTo("age", 18).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("ohoh", document.getId()+"=>"+document.getData());
                                UserInfo userInfo=document.toObject(UserInfo.class);
                                Log.d("oh", "name : "+userInfo.getName());
                                Log.d("oh", "address : "+userInfo.getAddress());
                                Log.d("oh", "id : "+userInfo.getId());
                                Log.d("oh", "pwd : "+userInfo.getPwd());
                                Log.d("oh", "age : "+userInfo.getAge());

                            }
                        }else{
                            Log.d("oh","Error getting documents : "+task.getException());
                        }
                    }
                });
    }

    private void selectDoc() {
        DocumentReference docRef=db.collection("users").document("userinfo");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d("oh", "Document Snapshot data : "+documentSnapshot.getData());

                        UserInfo userInfo=documentSnapshot.toObject(UserInfo.class);
                        Log.d("oh", "name : "+userInfo.getName());
                        Log.d("oh", "id : "+userInfo.getId());
                        Log.d("oh", "pwd : "+userInfo.getPwd());
                        Log.d("oh", "age : "+userInfo.getAge());

                    }else{
                        Log.d("oh","해당 데이터가 없습니다.");
                    }
                }else{
                    Log.e("oh", "get failed with "+task.getException());
                }
            }
        });

    }

    private void deleteField() {
        DocumentReference docRef=db.collection("users").document("userinfo");

        Map<String, Object> updates=new HashMap<>();
        updates.put("address", FieldValue.delete());

       docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Log.d("cholingling", "DocumentSnapshot successfully deleted!");
           }
       });
    }

    private void deleteDoc() {
        Map<String, Object> member=new HashMap<>();
        member.put("name", "조혤령");
        member.put("address", "경기도");
        member.put("age", 18);
        member.put("id", "my");
        member.put("pw", "hello!");

        db.collection("users").document("userinfo").
               delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("chohyeryung", "DocumentSnapshot 성공적으로 삭제됨");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("chohyeryung", "Document Error!!"+e.getMessage());
                    }
                });
    }

    private void setData() {
        Map<String, Object> member=new HashMap<>();
        member.put("name", "조혤령");
        member.put("address", "경기도");
        member.put("age", 18);
        member.put("id", "my");
        member.put("pw", "hello!");

        db.collection("users").document("userinfo").
                set(member).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("chohyeryung", "DocumentSnapshot 성공적으로 저장됨");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("chohyeryung", "Document Error!!"+e.getMessage());
                    }
                });
    }

    private void addData() {
        Map<String, Object> member=new HashMap<>();
        member.put("name", "조링링");
        member.put("address", "용인시");
        member.put("age", "18");
        member.put("id", "lingling");
        member.put("pw", "hello!");

        db.collection("users").add(member).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("chohyeryung", "Document ID = "+documentReference.get());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("chohyeryung", "Document Error!!"+e.getMessage());
                    }
                });
    }
}