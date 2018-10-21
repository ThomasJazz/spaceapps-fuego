package com.thomasdevelops.spaceappsfuego.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thomasdevelops.spaceappsfuego.pojo.FeedItem;

import java.util.ArrayList;
import java.util.List;

// DAO methods
public class FirestoreRepository {
    private static final String COLLECTION_FEED = "feed";
    private FirebaseFirestore db;

    public FirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public List<FeedItem> getFeedItems() {
        final List<FeedItem> items = new ArrayList<>();
        db.collection(COLLECTION_FEED)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot: task.getResult()) {
                                FeedItem item = snapshot.toObject(FeedItem.class);
                                items.add(item);
                            }
                        }
                    }
                });
        return items;
    }
}
