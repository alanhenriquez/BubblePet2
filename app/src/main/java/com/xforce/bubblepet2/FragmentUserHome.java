package com.xforce.bubblepet2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.adapters.TargetaPet;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUserHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUserHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentUserHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUserHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUserHome newInstance(String param1, String param2) {
        FragmentUserHome fragment = new FragmentUserHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.userName).setValuePath("PerfilData/user").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.userMail).setValuePath("CountData/userMail").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.biografia_perfil_content).setValuePath("PerfilData/userName").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.text_targeta_pet_content_info_1).setValuePath("PetData/nombre").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.text_targeta_pet_content_info_2).setValuePath("PetData/edad").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.text_targeta_pet_content_info_3).setValuePath("PetData/color").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.text_targeta_pet_content_info_4).setValuePath("PetData/raza").getData();
        GetDataUser.DataOnFragment.build(view).setElementbyId(R.id.text_targeta_pet_content_info_5).setValuePath("PetData/salud").getData();
        GetDataUser.DataOnFragment.build(view,FragmentUserHome.this).setElementbyId(R.id.imgPhoto).setValuePath("ImageData/imgPerfil/ImageMain").getData();
        GetDataUser.DataOnFragment.build(view,FragmentUserHome.this).setElementbyId(R.id.imagePet).setValuePath("ImageData/imgPetPerfil/ImageMain").getData();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(GetDataUser.DataOnActivity.getUserId()).child("PetData");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TargetaPet targetaPet = snapshot.getValue(TargetaPet.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }


    public interface OnFragmentInteractionListener {
    }

}