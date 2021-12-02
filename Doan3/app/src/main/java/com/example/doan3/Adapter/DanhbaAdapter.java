package com.example.doan3.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan3.Empty.Account;
import com.example.doan3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhbaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Account> accountList;

    public DanhbaAdapter(Context context, int layout, List<Account> accountList) {
        this.context = context;
        this.layout = layout;
        this.accountList = accountList;
    }

    @Override
    public int getCount() {

        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Account acc=accountList.get(position);
        convertView =inflater.inflate(layout,null);
        ImageView imvAvatar= convertView.findViewById(R.id.imvAvatar_dbct);
        TextView nameAccount=convertView.findViewById(R.id.tv_danhbacuttom_nameacc);
        Picasso.get().load(Uri.parse(acc.getAvatar())).into(imvAvatar);
        nameAccount.setText(acc.getHoten());
        return convertView;
    }
}
