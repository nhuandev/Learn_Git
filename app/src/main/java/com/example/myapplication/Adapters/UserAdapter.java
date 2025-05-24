package com.example.myapplication.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemUserBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(
                android.view.LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.tvName.setText(user.getName());
        holder.binding.tvEmail.setText(user.getEmail());
        // Tải ảnh mẫu bằng Glide
        Glide.with(holder.itemView.getContext())
                .load("https://picsum.photos/200?random=" + user.getId())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
