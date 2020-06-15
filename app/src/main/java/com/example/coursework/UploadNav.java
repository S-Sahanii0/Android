package com.example.coursework;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coursework.Authentication.LoginActivity;
import com.example.coursework.model.Menu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class UploadNav extends Fragment {

    private static final int PICK_GALLERY_IMAGE = 234;
    FirebaseAuth.AuthStateListener mauthAuthStateListener;
    public static Context contextOfApplication;
    String finalPushKey;
    private Uri filePath;
    ImageView food;
    EditText title, price;
    ProgressBar mProgressBar;
    FirebaseStorage fire;
    StorageReference storeRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Spinner mSpinner;
    private Button doneButton;
    AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.upload_frag, container, false);
        title=view.findViewById(R.id.title_id);
        food=view.findViewById(R.id.uploadImage);
        price=view.findViewById(R.id.price_id);
        mSpinner = view.findViewById(R.id.spinner_id);
        doneButton = view.findViewById(R.id.done);
        fire = FirebaseStorage.getInstance();
        storeRef = fire.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        finalPushKey= databaseReference.push().getKey();


        contextOfApplication = getApplicationContext();
        mProgressBar = view.findViewById(R.id.progress_bar);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.loading_dialog, null));
                builder.setCancelable(true);

                dialog = builder.create();

                switch (mSpinner.getSelectedItem().toString()){
                    case "Add to Main Menu":
                        uploadData();

                        break;
                    case "Add to Combo Menu":
                        uploadCombo();
                        uploadData();

                        break;
                    case "Add to Chinese Menu":
                        uploadChinese();
                        uploadData();

                        break;
                    case "Add to Korean Menu":
                        uploadKorean();
                        uploadData();

                        break;
                    case "Add to Breakfast Menu":
                        uploadBreakfast();
                        uploadData();

                        break;
                }

            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });
        return view;
    }


    private Context getApplicationContext() {
        return contextOfApplication;
    }


    private void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.with(getActivity()).load(filePath).into(food);
        }
    }

    private String getFileExtension(Uri uri) {
        android.content.Context applicationContext = getActivity().getApplicationContext();
        ContentResolver cr = applicationContext.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void uploadData() {
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();

        if (filePath != null) {
            dialog.show();
            final String keys=databaseReference.push().getKey();
            storeRef.child("Food").child(keys).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Food").child(keys).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);
                            Menu menu = new Menu(pricee, uriimage, name, finalPushKey);
                            databaseReference.child("Food").child(finalPushKey).setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            //startActivity(new Intent(getActivity(), MenuNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadCombo() {
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {

            dialog.show();
            final String key=databaseReference.push().getKey();
            storeRef.child("Combo").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Combo").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);;
                            Menu menu = new Menu(pricee, uriimage, name, finalPushKey);
                            databaseReference.child("Combo").child(finalPushKey).setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            //startActivity(new Intent(getActivity(), MenuNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadKorean() {
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {

            dialog.show();
            final String key=databaseReference.push().getKey();
            storeRef.child("Korean").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Korean").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);
                            Menu menu = new Menu(pricee, uriimage, name, finalPushKey);
                            databaseReference.child("Korean").child(finalPushKey).setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            //startActivity(new Intent(getActivity(), MenuNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadBreakfast() {
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {

            dialog.show();
            final String key=databaseReference.push().getKey();

            storeRef.child("Breakfast").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Breakfast").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);
                            Menu menu = new Menu(pricee, uriimage, name, finalPushKey);
                            databaseReference.child("Breakfast").child(finalPushKey).setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();


                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadChinese() {
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {
            dialog.show();
            final String key=databaseReference.push().getKey();
            storeRef.child("Chinese").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Chinese").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage = String.valueOf(uri);
                            Menu menu = new Menu(pricee, uriimage, name, finalPushKey);
                            databaseReference.child("Chinese").child(finalPushKey).setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            //startActivity(new Intent(getActivity(), MenuNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

}
