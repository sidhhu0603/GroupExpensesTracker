package com.example.groupexpensestracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText eml,pass;
    TextView frgtpss;
    Button lgn;
    float v=0;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false );

        eml = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pswrd);
        frgtpss = root.findViewById(R.id.forgetpswrd);
        lgn = root.findViewById(R.id.login);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getContext(),MainActivity.class));
            getActivity().finish();
        }

        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=eml.getText().toString().trim();
                String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
                String checkPassword = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";
                String password=pass.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    eml.setError("Email is required");
                    return;
                }

                else if (!email.matches(checkEmail)) {
                    eml.setError("Invalid Email!");
                    return ;
                }

                if(TextUtils.isEmpty(password)){
                    pass.setError("Password is Required");
                    return;
                }else if (!password.matches(checkPassword)) {
                    pass.setError("Invalid Password!");
                    return ;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),MainActivity.class));
                        }else{
                            Toast.makeText(getActivity(), "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });

        frgtpss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email-id To Receive The Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Extracting the email and sending the link
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Reset Link Has Been Sent To Your Entered Email-id", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error ! Reset Has Not Been Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the Dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        eml.setTranslationX(0);
        pass.setTranslationX(0);
        frgtpss.setTranslationX(0);
        lgn.setTranslationX(0);

        eml.setAlpha(v);
        pass.setAlpha(v);
        frgtpss.setAlpha(v);
        lgn.setAlpha(v);

        eml.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        frgtpss.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        lgn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;
    }
}
