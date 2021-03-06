package com.example.gurungpreyong.orgpromansys;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class dairyproducts extends AppCompatActivity {
    private RecyclerView postinsta;
    private DatabaseReference mdatabase;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener authStateListener;
    ProgressBar progressBar1;
    Button add;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dairyproducta);
        mauth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    add.setVisibility(View.VISIBLE);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(dairyproducts.this,Admin.class);
                            i.putExtra("keyroot","dairyproducta");
                            startActivity(i);
                        }
                    });
                }
            }
        };
        progressBar1 = findViewById(R.id.progressBar);
        progressBar1.setVisibility(View.VISIBLE);

        add = findViewById(R.id.addmore);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("dairyproducta");
        postinsta = findViewById(R.id.postinsta);
        postinsta.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mdatabase.keepSynced(true);
        postinsta.setLayoutManager(mLayoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(authStateListener);
        FirebaseRecyclerAdapter<gtr, Vegetables.BlogViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<gtr, Vegetables.BlogViewholder>(gtr.class,R.layout.design2,Vegetables.BlogViewholder.class,mdatabase) {
            @Override
            protected void populateViewHolder(Vegetables.BlogViewholder viewHolder, gtr model, int position) {
                final String pskey = getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setHomestayPic(getApplicationContext(), model.getHomestayPic());
                viewHolder.setHomeaddress(model.getHomeaddress());
                viewHolder.setPrice(model.getPrice());
                progressBar1.setVisibility(View.GONE);
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(dairyproducts.this, "Loading details...", Toast.LENGTH_LONG).show();
                        mdatabase.child(pskey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Intent intent = new Intent(dairyproducts.this, Main2Activity.class);
                                intent.putExtra("message", pskey);
                                intent.putExtra("keyroot","dairyproducta");
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        postinsta.setAdapter(firebaseRecyclerAdapter);
        mdatabase.keepSynced(true);
    }

    public static class BlogViewholder extends RecyclerView.ViewHolder {
        View view;
        public BlogViewholder(View itemview)
        {
            super(itemview);
            view=itemview;

        }
        public void setName(String nname)
        {
            TextView ptitle=view.findViewById(R.id.hotelname);
            ptitle.setText(nname);
        }
        public void setHomeaddress(String Hommeaddress) {
            TextView det = view.findViewById(R.id.hoteladdress);
            det.setText(Hommeaddress);
        }
        public void setHomestayPic(Context ctx, String image) {
            ImageView post=view.findViewById(R.id.hotelpic);
            Picasso.with(ctx).load(image).into(post);
        }
        public void setPrice(String Pprice) {
            TextView jo=view.findViewById(R.id.rate);
            jo.setText(Pprice);
        }
    }
}