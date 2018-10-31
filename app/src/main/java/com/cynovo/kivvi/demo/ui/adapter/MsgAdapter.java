package com.cynovo.kivvi.demo.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;

import java.util.List;

/**
 * Created by cynovo on 2016-06-06.
 */
public class MsgAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;

    public MsgAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.my_msg_item, null);
            holder.tvMsg = (TextView) convertView
                    .findViewById(R.id.my_msg_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String xxxx = mData.get(position);

        Log.i("=============", mData.get(position).contains("class") + "");
        //如果msg的信息中包含class，将信息用红色字体显示出来
        if(mData.get(position).contains("class")){
            holder.tvMsg.setTextColor(KivviApplication.getContext().getResources().getColor(R.color.red));
        }else{
            if(mData.get(position).contains("FALSE")) {
                holder.tvMsg.setTextColor(KivviApplication.getContext().getResources().getColor(R.color.red));
            }else {
                holder.tvMsg.setTextColor(KivviApplication.getContext().getResources().getColor(R.color.light_black));
            }
        }
        //如果msg的信息中包含prompt，将信息用蓝色字体显示出来
        holder.tvMsg.setText(mData.get(position));
        return convertView;
    }

    private final class ViewHolder {
        private TextView tvMsg;
    }
}
