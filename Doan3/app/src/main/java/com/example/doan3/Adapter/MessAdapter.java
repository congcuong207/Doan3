package com.example.doan3.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan3.Empty.Chat;
import com.example.doan3.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import static com.example.doan3.Introl.information;


public class MessAdapter extends BaseAdapter {
    private Context context;
    private int layout1;
    private int layout2;
    private List<Chat> messList;
    boolean kt=true;

    public MessAdapter(Context context, int layout1, int layout2, List<Chat> messList) {
        this.context = context;
        this.layout1 = layout1;
        this.layout2 = layout2;
        this.messList = messList;
    }

    @Override
    public int getCount() {
        return messList.size();
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
        Chat c=messList.get(position);
        if(c.getNguoigui().equalsIgnoreCase(information.getTaikhoan()))
        {
            convertView =inflater.inflate(layout2,null);
            TextView tvMess=convertView.findViewById(R.id.tvMess);
            tvMess.setText(c.getMess());
            kt=true;
        }
        else if(kt)
        {
            convertView =inflater.inflate(layout1,null);
            ImageView imvAvatar= convertView.findViewById(R.id.imvAvatar);
            TextView tvMess=convertView.findViewById(R.id.tvMess);
            Picasso.get().load(Uri.parse(c.getAvnguoigui())).into(imvAvatar);
            tvMess.setText(c.getMess());
            kt=false;
        }
        else {
            convertView =inflater.inflate(R.layout.messcontinue,null);
            TextView tvMess=convertView.findViewById(R.id.tvMesscontinue);
            tvMess.setText(c.getMess());
        }
        return convertView;
    }
}
