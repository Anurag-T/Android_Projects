package com.example.githubrepoviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubrepoviewer.database.RepoEntity;


import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    private Context mContext;
    private List<RepoEntity> mEntities;
    final private ListItemClickListener mOnClickListener;
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public MainAdapter(Context context,ListItemClickListener listener){
    mOnClickListener = listener;
    mContext = context;
    }
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_repos,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setRepos(List<RepoEntity> entities){
        this.mEntities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mEntities == null || mEntities.size() == 0){
            return 0;
        }
        return mEntities.size();
    }

    public  class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRepoName;
        private TextView mRepoDesc;
        private Button mSend;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mRepoName = itemView.findViewById(R.id.tv_list_reponame);
            mRepoDesc = itemView.findViewById(R.id.tv_list_repodesc);
            mSend = itemView.findViewById(R.id.button_list_sent);

        }
        @SuppressLint("SetTextI18n")
        public void bind(int position){
            RepoEntity currentEntity =  mEntities.get(position);
            String desc = currentEntity.getRepo_description();
            if(desc.length() > 100){
                mRepoDesc.setText(desc.substring(0,80));
            }else{
                mRepoDesc.setText(desc);
            }

            mRepoName.setText(currentEntity.getRepo_name());
            mSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ShareCompat.IntentBuilder.from((Activity) mContext).setType("text/plain").setText(currentEntity.getUrl())
                            .createChooserIntent();



//



//

                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    }
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
