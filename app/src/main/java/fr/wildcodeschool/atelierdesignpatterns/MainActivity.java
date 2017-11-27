package fr.wildcodeschool.atelierdesignpatterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private NewsAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : init singleton then load news
        NewsSingleton newsSingleton = NewsSingleton.getInstance();
        newsSingleton.addObserver(this);
        newsSingleton.loadNews();

        // setup the adapter
        RecyclerView newsListView = findViewById(R.id.news_list);
        newsListView.setHasFixedSize(true);
        newsListView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsAdapter(new NewsAdapter.NewsClickListener() {
            @Override
            public void onClick(NewsModel newsModel) {
                // TODO : call NewsActivity
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("news", newsModel);
                startActivity(intent);
            }
        });
        newsListView.swapAdapter(mAdapter, false);

        Button addNews = findViewById(R.id.add_news);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, JournalistActivity.class));
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof NewsSingleton) {
            // update the adapter and refresh the list
            NewsSingleton newsSingleton = (NewsSingleton) observable;
            mAdapter.updateAdapter(newsSingleton.getNews());
        }
    }
}
