package bypankaj.wallanime_theanimewallpaperapp;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class download extends Fragment {

    private View mView;
    private Context mContext;
    private RecyclerView mSearchList;
    private EditText search_edit;
    private ImageView ok;
    private SearchAdapter mAdapter;
    private final List<CircleFeatured> list =  new ArrayList<>();
    private DatabaseReference mDatabase;
    private Query query;
    private GridLayoutManager mLayoutManager;


    public download() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_download, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Circle");
        mContext = getContext();
        mSearchList = (RecyclerView) mView.findViewById(R.id.list);
        search_edit = (EditText) mView.findViewById(R.id.search);
        ok = (ImageView) mView.findViewById(R.id.ok);
        mAdapter = new SearchAdapter(list,mContext);
        mSearchList.addItemDecoration(new SpacingGridLayout(0));
        mLayoutManager = new GridLayoutManager(mContext,2);
        mSearchList.setHasFixedSize(true);
        mSearchList.setLayoutManager(mLayoutManager);
        mSearchList.setAdapter(mAdapter);
        if(TextUtils.isEmpty(search_edit.getText())){
            list.clear();
            loadPosts();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    String search = search_edit.getText().toString().replaceAll(" ","").toLowerCase();
                    loadSearch(search);
                }
            });
        }else{
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    String search = search_edit.getText().toString().replaceAll(" ","").toLowerCase();
                    loadSearch(search);
                }
            });

        }
        return mView;
    }
    private void loadPosts() {

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CircleFeatured recipies =  dataSnapshot.getValue(CircleFeatured.class);
                list.add(recipies);
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
    private void loadSearch(String search) {

        query = mDatabase.orderByChild("search").startAt(search).endAt(search+"\uf8ff");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CircleFeatured recipies =  dataSnapshot.getValue(CircleFeatured.class);
                list.add(recipies);
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