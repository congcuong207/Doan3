package com.example.doan3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.socket.client.Socket;

import com.example.doan3.Adapter.MessAdapter;
import com.example.doan3.ChooseAvatar;
import com.example.doan3.Empty.Account;
import com.example.doan3.Empty.Chat;
import com.example.doan3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.doan3.Activity.giaodienchinh.ktvideo;
import static com.example.doan3.Activity.giaodienchinh.IdNguoinhan;
import static com.example.doan3.Activity.giaodienchinh.AvtNguoinhan;
import static com.example.doan3.Activity.giaodienchinh.HotenNguoinhan;
import static com.example.doan3.Introl.information;


public class MainActivity extends AppCompatActivity {
    ListView lv_mess;
    ImageButton btn_send;
    ArrayList<Chat> arrayList;
    EditText edt_mess;
    MessAdapter adapter;
    TextView textViewName;
    ImageView imageViewAVT, imv_videocall;
    private Socket mSocket;
    DatabaseReference db;
    boolean kt = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        arrayList = new ArrayList<>();
        adapter = new MessAdapter(this, R.layout.mess, R.layout.mess2, arrayList);
        lv_mess.setAdapter(adapter);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Chats");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chat acc = snapshot1.getValue(Chat.class);
                    if (acc.getNguoigui().equalsIgnoreCase(information.getTaikhoan()) && acc.getNguoinhan().equalsIgnoreCase(IdNguoinhan) || acc.getNguoigui().equalsIgnoreCase(IdNguoinhan) && acc.getNguoinhan().equalsIgnoreCase(information.getTaikhoan())) {
                        arrayList.add(new Chat(acc.getNguoigui(), acc.getAvnguoigui(), acc.getNguoinhan(), acc.getAvnguoinhan(), acc.getMess()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_mess.getText().toString().trim().equals("")) {
                    addChats(new Chat(information.getTaikhoan(), information.getAvatar(), IdNguoinhan, AvtNguoinhan, edt_mess.getText().toString().trim()));
                    edt_mess.setText("");
                }
            }
        });
        Picasso.get().load(Uri.parse(AvtNguoinhan)).into(imageViewAVT);
        textViewName.setText(HotenNguoinhan);
        imv_videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Meeting.videoCall(information.getTaikhoan() + IdNguoinhan, MainActivity.this);
                    addChats(new Chat(information.getTaikhoan(), information.getAvatar(), IdNguoinhan, AvtNguoinhan, information.getTaikhoan() + " đang yêu cầu bạn tham gia cuộc họp"));
            }
        });
        lv_mess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position).getMess().equalsIgnoreCase(IdNguoinhan + " đang yêu cầu bạn tham gia cuộc họp")) {
                    Meeting.videoCall(IdNguoinhan + information.getTaikhoan(), MainActivity.this);
                }
            }
        });
    }

    private void Init() {
        imv_videocall = findViewById(R.id.imv_videocall);
        lv_mess = findViewById(R.id.lv_mess_activity_main);
        btn_send = findViewById(R.id.imbtn_send_activitymain);
        edt_mess = findViewById(R.id.edt_mess_activity_main);
        imageViewAVT = findViewById(R.id.imv_Avatar_main);
        textViewName = findViewById(R.id.tv_name_main);
    }

    public void addChats(Chat ch) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Chats").push().setValue(ch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                }
            }
        });

    }
}