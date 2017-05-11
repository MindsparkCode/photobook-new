package cornelltech.cwkj.photobook_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.EditText;
import android.view.View;
import android.webkit.MimeTypeMap;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUri;

    private FirebaseUser mUser;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    public static final String firebase_database_path = "image";
    public static final int REQUEST_CODE = 39734;
    public String storage_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebase_database_path);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtImageName = (EditText) findViewById(R.id.txtImageName);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void btnBrowse_Click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecting Image"), REQUEST_CODE);
    }

    public void btnShowListImage_Click(View v) {
        Intent i = new Intent(MainActivity.this, ImageListActivity.class);
        startActivity(i);
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUploadPublic_Click(View v) {
        StorageMetadata mUploadImageMetadata = new StorageMetadata.Builder()
                .setCustomMetadata("User", "UNKNOWN")
                .setCustomMetadata("Permission", "public")
                .setCustomMetadata("Uri", imgUri.toString())
                .setCustomMetadata("Description", txtImageName.getText().toString())
                .build();
        UploadPhotos("public/", "public", mUploadImageMetadata);
    }

    // Upload Private Photos
    public void btnUploadPrivate_Click(View v) {
        StorageMetadata mUploadImageMetadata = new StorageMetadata.Builder()
                .setCustomMetadata("User", mUser.getUid())
                .setCustomMetadata("Permission", "private")
                .setCustomMetadata("Uri", imgUri.toString())
                .setCustomMetadata("Description", txtImageName.getText().toString())
                .build();
        UploadPhotos("private/" +  mUser.getUid() + "/", "private" , mUploadImageMetadata);

    }

    // Upload a Public or Private Photo
    public void UploadPhotos(String storage_path, final String permission, StorageMetadata mUploadImageMetadata) {

        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading a "+ permission + " image");
            dialog.show();

            StorageReference ref = mStorageRef.child(storage_path + System.currentTimeMillis() + "." + getImageExt(imgUri));

            ref.putFile(imgUri, mUploadImageMetadata)
                    // Handle Success
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                            ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(permission).child(uploadId).setValue(imageUpload);
                        }
                    })
                    // Handle Failure
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    // Handle progress
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please select an image!", Toast.LENGTH_SHORT).show();
        }
    }


    // Show Activity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // Start Login screen
    public void btnLogin_Click(View v) {
        Intent i = new Intent(MainActivity.this,GoogleSignInActivity.class);
        startActivity(i);
    }

    // Search or Browse Images
    public void btnSearchImage_Click(View v) {
        Intent i = new Intent(MainActivity.this, ImageListActivity.class);
        txtImageName = (EditText) findViewById(R.id.txtImageName);
        i.putExtra("query", txtImageName.getText().toString());
        startActivity(i);
    }
}
