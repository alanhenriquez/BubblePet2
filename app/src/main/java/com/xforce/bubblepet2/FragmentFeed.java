package com.xforce.bubblepet2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xforce.bubblepet2.adapters.TargetaPet;
import com.xforce.bubblepet2.adapters.TargetaPetAdapter;
import com.xforce.bubblepet2.dataFromDataBase.GetDataUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentFeed extends Fragment {

    ImageView imageView;
    private TargetaPetAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public FragmentFeed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Rename and change types of parameters
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        imageView = view.findViewById(R.id.imagePet);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);


        FirebaseDatabase database = GetDataUser.DataOnActivity.getInstanceFD();
        adapter = new TargetaPetAdapter(view.getContext());
        LinearLayoutManager l = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });


        //Cargamos los datos
        DatabaseReference databaseReference = database.getReference("targetaFeed");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    String ids = Objects.requireNonNull(child.getValue()).toString();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(ids).child("PetData");
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            TargetaPet targetaPet = snapshot.getValue(TargetaPet.class);
                            adapter.addTargetaPet(targetaPet);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Agregamos las nuevas tarjetas cuando estas sean agregadas por nuevos usuarios
        databaseReference = database.getReference("targetaFeed");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    String ids = Objects.requireNonNull(child.getValue()).toString();

                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(ids).child("PetData");
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            TargetaPet targetaPet = snapshot.getValue(TargetaPet.class);
                            adapter.addTargetaPet(targetaPet);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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