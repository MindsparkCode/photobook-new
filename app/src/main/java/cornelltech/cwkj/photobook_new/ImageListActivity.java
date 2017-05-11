package cornelltech.cwkj.photobook_new;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.ArrayList;

/*
* Show all images when search query is empty or
* Show selected images matching search query
*/
public class ImageListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView mListView;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imgList = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.listViewImage);

        Intent mIntent = getIntent();
        final String mSearchQuery = mIntent.getStringExtra("query");

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        // Show the progress of loading images
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading images...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.firebase_database_path);

        // Search in the right child database node according to the user login status
         if(mUser==null){
             mDatabaseRef = mDatabaseRef.child("public");
         } else {
             mDatabaseRef = mDatabaseRef.child("private");
         }

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                // Iterate through the database records for  images to show in imgList
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);

                    // Show an image when (a) search query is empty or
                    // (b) the search query mateches image description
                    if(mSearchQuery != null ){
                        if(img.name.equals(mSearchQuery) || mSearchQuery.isEmpty()) {
                            imgList.add(img);
                        }
                    }
                }

                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Dismiss the progress of loading images
                progressDialog.dismiss();
            }
        });
    }
}
