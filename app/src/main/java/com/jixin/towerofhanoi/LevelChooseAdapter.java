package com.jixin.towerofhanoi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LevelChooseAdapter extends RecyclerView.Adapter<LevelChooseAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;
    private SharedPreferences sp;

    public LevelChooseAdapter(Context context,List<String> list){
        mContext=context;
        mList=list;
    }

    @NonNull
    @Override
    public LevelChooseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.item_chooselevel,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelChooseAdapter.ViewHolder holder, final int position) {
        holder.button.setText(mList.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp=mContext.getSharedPreferences(Constants.SharedPreferenceName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt("GameLevel",position+3).apply();
                Intent intent=new Intent(mContext,GameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("GameLevel",position+3);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.btn_chooselevel);
        }
    }

}
