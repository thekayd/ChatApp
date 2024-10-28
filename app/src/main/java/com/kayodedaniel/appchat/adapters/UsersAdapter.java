package com.kayodedaniel.appchat.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kayodedaniel.appchat.R;
import com.kayodedaniel.appchat.databinding.ItemContainerUserBinding;
import com.kayodedaniel.appchat.listeners.UserListener;
import com.kayodedaniel.appchat.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> users;

    private final UserListener userListener;

    public UsersAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerUserBinding binding;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemContainerUserBinding.bind(itemView);
        }

        void setUserData(User user) {
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);


            // Check if the image is null or empty before attempting to decode
            if (user.image != null && !user.image.isEmpty()) {
                binding.imageProfile.setImageBitmap(getUserImage(user.image));
            } else {
                // Set a default image or placeholder if user.image is null
                binding.imageProfile.setImageResource(R.drawable.ic_launcher_background);
            }

            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
