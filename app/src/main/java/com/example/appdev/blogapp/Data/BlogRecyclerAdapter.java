package com.example.appdev.blogapp.Data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.appdev.blogapp.Model.Blog;
import com.example.appdev.blogapp.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by test on 3/23/2018.
 */

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postrow,parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(BlogRecyclerAdapter.ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        String imageUrl = null;

        holder.title.setText(blog.getTitle());
        holder.description.setText(blog.getDesc());
        holder.timestamp.setText(blog.getTimeStamp());
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimeStamp())).getTime());
        holder.timestamp.setText(formattedDate);
        imageUrl = blog.getImage();

        //use picasso library to load images
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title;
        public TextView description;
        public TextView timestamp;
        String userId;

        public ViewHolder(View view,Context ctx){
            super(view);
            context = ctx ;
            title= (TextView) view.findViewById(R.id.postTitleListId);
            description = (TextView ) view.findViewById(R.id.postTextListId);
            image = (ImageView) view.findViewById(R.id.PostImageviewId);
            timestamp = (TextView) view.findViewById(R.id.PostTimeStampId);



        }

    }

}
