package com.yz.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yz.account.R;
import com.yz.data.bean.AccountType;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private List<AccountType> data;

    private Context mContext;

    public SpinnerAdapter(Context context,List<AccountType> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.item_text,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(data.get(position).getTypeName());

        return convertView;
    }

    class ViewHolder {

        TextView tvName;

        public ViewHolder(View convertView){
            tvName =  convertView.findViewById(R.id.tv_type);
        }
    }
}
