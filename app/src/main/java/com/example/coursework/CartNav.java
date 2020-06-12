package com.example.coursework;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coursework.model.Menu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class CartNav extends Fragment {

    private static final int PICK_GALLERY_IMAGE = 234;
    public static Context contextOfApplication;
    private Uri filePath;
    Button add, combo, korean, breakfast, chinese;
    ImageView food;
    EditText title, price;
    ProgressBar mProgressBar;
    FirebaseStorage fire;
    StorageReference storeRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.cart_frag, container, false);
        title=view.findViewById(R.id.title_id);
        food=view.findViewById(R.id.uploadImage);
        price=view.findViewById(R.id.price_id);
        add=view.findViewById(R.id.add_id);
        combo=view.findViewById(R.id.combo_btn);
        chinese=view.findViewById(R.id.chinese_btn);
        korean=view.findViewById(R.id.korean_btn);
        breakfast=view.findViewById(R.id.breakfast_btn);


        fire=FirebaseStorage.getInstance();
        storeRef=fire.getReference();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        contextOfApplication = getApplicationContext();
        mProgressBar = view.findViewById(R.id.progress_bar);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });

        combo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCombo();
            }
        });

        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadKorean();
            }
        });

       breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBreakfast();
            }
        });

        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadChinese();
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
        startActivityForResult(Intent.createChooser(intent,"Select an image"), PICK_GALLERY_IMAGE);
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
        //String imagekey=databaseReference.push().getKey();

        if (filePath != null) {
            final String keys=databaseReference.push().getKey();
            storeRef.child("Food").child(keys).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Food").child(keys).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage=String.valueOf(uri);
                            databaseReference.push().getKey();
                            Menu menu = new Menu(pricee,uriimage, name);
                            databaseReference.child("Food").push().setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getActivity(), OrderNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadCombo(){
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {
            final String key=databaseReference.push().getKey();
            storeRef.child("Combo").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Combo").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage=String.valueOf(uri);
                            databaseReference.push().getKey();
                            Menu menu = new Menu(pricee,uriimage, name);
                            databaseReference.child("Combo").push().setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getActivity(), OrderNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadKorean(){
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {
            final String key=databaseReference.push().getKey();
            storeRef.child("Korean").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Korean").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage=String.valueOf(uri);
                            databaseReference.push().getKey();
                            Menu menu = new Menu(pricee,uriimage, name);
                            databaseReference.child("Korean").push().setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getActivity(), OrderNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadBreakfast(){
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {
            final String key=databaseReference.push().getKey();
            storeRef.child("Breakfast").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Breakfast").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage=String.valueOf(uri);
                            databaseReference.push().getKey();
                            Menu menu = new Menu(pricee,uriimage, name);
                            databaseReference.child("Breakfast").push().setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getActivity(), OrderNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadChinese(){
        final String name = title.getText().toString();
        final String pricee = price.getText().toString();
        if (filePath != null) {
            final String key=databaseReference.push().getKey();
            storeRef.child("Chinese").child(key).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storeRef.child("Chinese").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uriimage=String.valueOf(uri);
                            databaseReference.push().getKey();
                            Menu menu = new Menu(pricee,uriimage, name);
                            databaseReference.child("Chinese").push().setValue(menu);
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getActivity(), OrderNav.class));

                        }
                    });
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please select the product image", Toast.LENGTH_SHORT).show();
        }

    }

}
