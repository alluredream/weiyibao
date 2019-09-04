package com.android.test.makestart2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.test.makestart2.R;

import java.util.List;

/**
 * Created by AllureDream on 2018-11-19.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<String> data;
    //private List<Notice>data;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerAdapter(Context context, List<String> datas){
        this.context = context;
        this.data = datas;
        inflater = LayoutInflater.from(context);
    }
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    //添加数据
    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
        for(int i=0;i<data.size();i++){
            holder.tv.setText(data.get(position));
        }

        System.out.println("这是"+data.size());
        if(onItemClickListener!=null){
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
