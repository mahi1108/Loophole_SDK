package com.cynovo.kivvi.demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<String> allDatas;

    public MyAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.allDatas = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return allDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = inflater.inflate(R.layout.activity_grid_item, null);
            holder.text = (TextView) view.findViewById(R.id.gridview_item_text);
            holder.imageView = (ImageView)view.findViewById(R.id.showIcon);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String label = allDatas.get(i);
        if(BaseAction.actionName.containsKey(label)){
            holder.imageView.setImageResource(R.mipmap.file);
//            holder.text.setBackgroundColor(mContext.getResources().getColor(R.color.white_1));
        }else{
            holder.imageView.setImageResource(R.mipmap.folder);
//            holder.text.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
        }
        holder.text.setText(label);
        return view;
    }
    public class ViewHolder {
        protected ImageView imageView;
        protected TextView text;

    }
}


