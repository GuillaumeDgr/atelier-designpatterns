package fr.wildcodeschool.atelierdesignpatterns;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class NewsSingleton extends Observable {

    private static NewsSingleton sInstance = null;
    private List<NewsModel> mNewsList = new ArrayList<>();

    private NewsSingleton() {

    }

    static NewsSingleton getInstance() {
        if (sInstance == null) {
            sInstance = new NewsSingleton();
        }
        return sInstance;
    }

    void loadNews() {
        // TODO : load news from Firebase then notifiy observers
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newsRef = database.getReference();
        newsRef.child("news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    NewsModel newsModel = dsp.getValue(NewsModel.class);
                    mNewsList.add(newsModel);
                }
                setChanged();
                notifyObservers();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    List<NewsModel> getNews() {
        return mNewsList;
    }
}
