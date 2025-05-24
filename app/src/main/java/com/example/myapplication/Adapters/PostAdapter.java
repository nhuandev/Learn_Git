package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Post;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemPostBinding;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    // Khởi tạo danh sách bài viết
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    // ViewHolder class
    public static class PostViewHolder extends RecyclerView.ViewHolder{
        ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

        Post post = posts.get(position);
        holder.binding.tvTitle.setText(post.getTitle());
        holder.binding.tvBody.setText(post.getBody());
        Glide.with(holder.itemView.getContext())
                .load("https://picsum.photos/200?random=" + post.getId())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
