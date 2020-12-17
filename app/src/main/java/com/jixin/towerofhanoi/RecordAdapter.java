package com.jixin.towerofhanoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Record> mList;


    public RecordAdapter(Context context,List<Record>list){
        mContext=context;
        mList=list;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_record,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        holder.playerId.setText(mList.get(position).getId());
        holder.playerGrade.setText(mList.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView playerId;
        private final TextView playerGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerGrade=itemView.findViewById(R.id.tv_recordGrade);
            playerId=itemView.findViewById(R.id.tv_recordId);
        }
    }
}
