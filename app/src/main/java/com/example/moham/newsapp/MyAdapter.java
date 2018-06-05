package com.example.moham.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<News> myList;

    public MyAdapter(Context context, List<News> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(myList.get(position));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView sectionName;
        TextView author;
        TextView publicationDate;
        String webURl;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            sectionName = itemView.findViewById(R.id.section_name);
            author = itemView.findViewById(R.id.author);
            publicationDate = itemView.findViewById(R.id.publication_date);
            itemView.setOnClickListener(this);

        }

        public void bind(News news) {
            StringBuilder authors = null;
            if(news.getAuthors() != null){
                authors = new StringBuilder("By ");
            for(int i=0; i<news.getAuthors().length;i++){
                authors.append(news.getAuthors()[i]);

                if(i+1 != news.getAuthors().length)
                    authors.append(", ");
                }
            }
            title.setText(news.getTitle());
            publicationDate.setText(news.getPublicationDate());
            sectionName.setText(news.getSectionName());

            if(authors != null)
            author.setText(authors.toString());

            webURl = news.getWebUrl();
        }

        @Override
        public void onClick(View v) {
            Uri webPage = Uri.parse(webURl);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            if(intent.resolveActivity(context.getPackageManager()) != null)
                context.startActivity(intent);
        }
    }
}
