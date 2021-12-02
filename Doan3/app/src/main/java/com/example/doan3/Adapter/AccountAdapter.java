package com.example.doan3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.Chat;
import com.example.doan3.R;

import java.util.List;

public class AccountAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Chat> accountList;

    public AccountAdapter(Context context, int layout, List<Chat> accountList) {
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
        Chat ch=accountList.get(position);
        convertView =inflater.inflate(layout,null);
        ImageView imvAvatar= convertView.findViewById(R.id.imvAvatar_accct);
        TextView tvMesshint=convertView.findViewById(R.id.tv_messhint_acct);
        TextView nameAccount=convertView.findViewById(R.id.tv_tenaccount_accct);

//        imvAvatar.setImageResource(acc.getAvatar());
//        tvMesshint.setText(acc.getMess());
//        nameAccount.setHint(ch.get());
        return convertView;
    }
}
