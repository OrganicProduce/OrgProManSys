package com.example.gurungpreyong.orgpromansys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class home extends AppCompatActivity {
    Button log;
    EditText new_usrname,new_pass;
    ProgressDialog progressDialog;
    private FirebaseAuth mauth;
    AlertDialog.Builder builder=null;
    AlertDialog dialog=null;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    Toast.makeText(home.this, "Admin Welcome", Toast.LENGTH_LONG).show();
                    log.setVisibility(View.GONE);
                }
            }
        };
        mauth.addAuthStateListener(authStateListener);
        Button button=findViewById(R.id.b);
        Button fruits=findViewById(R.id.c);
        Button dairy=findViewById(R.id.d);
        Button spices=findViewById(R.id.e);
        Button flower=findViewById(R.id.flower);
        Button herb=findViewById(R.id.h);


        log=findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });

        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this,dairyproducts.class);

                startActivity(i);
            }
        });        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Vegetables.class);
                startActivity(i);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, Fruits.class);
                startActivity(i);
            }
        });
        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(home.this, Flower.class);
                startActivity(i);
            }
        });






        // Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);

        ////Intent i = Intent.createChooser(emailIntent, "Send email to Organicproduce");
        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //  startActivity(i);
    }
    // if (id == R.id.exit)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Exit ?");
        builder.setPositiveButton("No",null);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                //bt.disable();
                finish();
                System.exit(0);
            }
        });
        builder.create();
        builder.show();
    }
    //// return super.onOptionsItemSelected(item);
    //
    // }
    public void AddUser()
    {
        LayoutInflater linf=LayoutInflater.from(home.this);
        View inflator=linf.inflate(R.layout.update,null);
        builder=new AlertDialog.Builder(home.this);
        builder.setTitle("Admin Login...");
        builder.setView(inflator);
        new_usrname=inflator.findViewById(R.id.new_usrname);
        new_pass=inflator.findViewById(R.id.new_pass);
        builder.setPositiveButton("Cancel",null);
        builder.setNegativeButton("Login",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {

            }
        });
        dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getusername=new_usrname.getText().toString().trim();
                String getuserpas=new_pass.getText().toString().trim();
                if (new_usrname.getText().toString().trim().equalsIgnoreCase("")) {
                    new_usrname.setError("This field can not be blank");}
                if (new_pass.getText().toString().trim().equalsIgnoreCase("")) {
                    new_pass.setError("This field can not be blank");}
                if(!TextUtils.isEmpty(getusername)&&!TextUtils.isEmpty(getuserpas)){
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    mauth.signInWithEmailAndPassword(getusername,getuserpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())

                            {progressDialog.dismiss();

                                Toast.makeText(home.this, "Logged In...", Toast.LENGTH_LONG).show();

                                log.setVisibility(View.GONE);
                            }
                            else
                            {
                                Toast.makeText(home.this, "Something went wrong...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
        //builder.show();
    }
}