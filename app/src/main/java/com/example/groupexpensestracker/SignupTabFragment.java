package com.example.groupexpensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTabFragment extends Fragment {
    EditText eml,mobile_no,pass,addrs;
    Button sgnup;
    FirebaseAuth firebaseAuth;
    float v=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        super.onCreate(savedInstanceState);

        eml = root.findViewById(R.id.email);
        mobile_no= root.findViewById(R.id.mbl_no);
        addrs= root.findViewById(R.id.addr);
        pass = root.findViewById(R.id.pswrd);
        sgnup = root.findViewById(R.id.signup);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getContext(),MainActivity.class));
            getActivity().finish();
        }

        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=eml.getText().toString().trim();
                String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
                String checkPassword ="[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";
                               //at least 1 digit
                                //at least 1 lower case letter
                                //at least 1 upper case letter
                              //any letter
                           //at least 1 special character
                                  //no white spaces
                                      //at least 6 characters


                String checknumber="[0-9]{10,13}";

                String password=pass.getText().toString().trim();
                String mobno=mobile_no.getText().toString().trim();
                String address=addrs.getText().toString().trim();

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


                if(TextUtils.isEmpty(mobno)){
                    mobile_no.setError("Mobile number is Required");
                    return;
                }
                else if (mobno.matches(checknumber)) {
                    mobile_no.setError("Invalid Mobile Number");
                    return;
                }



               if(TextUtils.isEmpty(address)){
                   addrs.setError("Address is Required");
                   return;
               }
               
               firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getContext(),MainActivity.class));
                       }else{
                           Toast.makeText(getActivity(), "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });

}


        });

        eml.setTranslationX(0);
        mobile_no.setTranslationX(0);
        addrs.setTranslationX(0);
        pass.setTranslationX(0);
        sgnup.setTranslationX(0);

        eml.setAlpha(v);
        mobile_no.setAlpha(v);
        addrs.setAlpha(v);
        pass.setAlpha(v);
        sgnup.setAlpha(v);

        eml.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mobile_no.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        addrs.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        sgnup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();


        return root;
    }
}
