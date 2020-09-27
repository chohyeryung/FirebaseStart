package kr.hs.emirim.cho.firebasestart.realtimedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;

import kr.hs.emirim.cho.firebasestart.R;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private  Context context=null;
    private ArrayList<MemoItem> memoItems=null;
    private MemoViewListener memoViewListener=null;

    public MemoAdapter(ArrayList<MemoItem> items, Context context, MemoViewListener listener){
        this.memoItems=items;
        this.context=context;
        this.memoViewListener=listener;
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_item_list, viewGroup, false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(memoItems.get(i).getMemotitle());
        viewHolder.contentsView.setText(memoItems.get(i).getMemocontents());
    }

    @Override
    public int getItemCount() {
        return memoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleView=null;
        public TextView contentsView=null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.memotitle);
            contentsView=itemView.findViewById(R.id.memocontents);
        }

        @Override
        public void onClick(View v) {

        }
    }
}