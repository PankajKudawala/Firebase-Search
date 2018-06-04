package by.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSearch extends AppCompatActivity {
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;
    private ReferenceAdapter mAdapter;
    private final List<Reference> list =  new ArrayList<>();

    private DatabaseReference mDatabase;
    private Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mDatabase = FirebaseDatabase.getInstance().getReference("Reference");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        mAdapter = new ReferenceAdapter(list,this);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setAdapter(mAdapter);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
			//Hardcoded but still working fine
            String searchText = mSearchField.getText().toString().toLowerCase().replaceAll("-","").replaceAll(" ","");
            list.clear();
            loadPosts(searchText);

            }
        });
       }
    private void loadPosts(String search) {

        query = mDatabase.orderByChild("search").startAt(search).endAt(search+"\uf8ff");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Reference reference =  dataSnapshot.getValue(Reference.class);
                list.add(reference);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
