package in.apptonic.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button submit;
    private EditText addTitle;
    private List<Essay> allEssay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        allEssay = new ArrayList<>();

        addTitle = findViewById(R.id.addTitle);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredTitle = addTitle.getText().toString();
                if (TextUtils.isEmpty(enteredTitle)) {
                    Toast.makeText(MainActivity.this, "You must enter a Title first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (enteredTitle.length() < 6) {
                    Toast.makeText(MainActivity.this, "Entered Title length must be more than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Essay essayObject = new Essay(enteredTitle);
                myRef.push().setValue(essayObject);
                addTitle.setText("");

            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addNewTitle(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                addNewTitle(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeTitle(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addNewTitle(DataSnapshot dataSnapshot) {

        for (DataSnapshot singeleSnapshot : dataSnapshot.getChildren()) {

            String addTitle = singeleSnapshot.getValue(String.class);
            allEssay.add(new Essay(addTitle));
            adapter = new MyAdapter(MainActivity.this, allEssay);
            recyclerView.setAdapter(adapter);

        }
    }

    private void removeTitle(DataSnapshot dataSnapshot) {

        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String removeTitle = singleSnapshot.getValue(String.class);
            for (int i = 0; i < allEssay.size(); i++) {

                if (allEssay.get(i).getSubject().equals(removeTitle)) {
                    allEssay.remove(i);
                }
            }

            Log.d(TAG, "Task tile" + removeTitle);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);


        }
    }

}


