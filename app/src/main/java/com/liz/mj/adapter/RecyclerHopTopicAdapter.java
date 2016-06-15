package com.liz.mj.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.liz.mj.R;
import com.liz.mj.bean.HotTopic;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description:
 * author: lizwangying@icloud.com
 * date: 2016-04-25 20:22
 * version:
 */
public class RecyclerHopTopicAdapter extends RecyclerView.Adapter<RecyclerHopTopicAdapter.MyViewHolder> {
    private List<HotTopic> hotTopicsList;
    private Context context;
    String imageUri;
    public RecyclerHopTopicAdapter(List<HotTopic> hotTopicsList, Context context) {
        this.hotTopicsList = hotTopicsList;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.listview_topic,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HotTopic topic = hotTopicsList.get(position);
        String date = topic.getCreatedAt();
        holder.textViewDay.setText(date.substring(8, 10));
        holder.textViewMonth.setText(date.substring(5, 7) + "月");
        holder.textViewTime.setText(date.substring(11, 16));
        holder.textViewTopic.setText(topic.getTopic());
        holder.textViewSeen.setText(topic.getSeen() + "");
        holder.textViewLike.setText(topic.getLike() + "");
        imageUri = hotTopicsList.get(position).getTopicPic().getFileUrl(context);
        Uri uri = Uri.parse(imageUri);
        holder.draweeView.setImageURI(uri);

        //创建DraweeController
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //加载的图片URI地址
                .setUri(Uri.parse(imageUri))
                //设置点击重试是否开启
                .setTapToRetryEnabled(true)
                //设置旧的Controller
                .setOldController(holder.draweeView.getController())
                //构建
                .build();

        //设置DraweeController
        holder.draweeView.setController(controller);


        if (onItemtClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemtClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemtClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return hotTopicsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @Bind(R.id.text_day)
        TextView textViewDay;
        @Bind(R.id.text_topic)
        TextView textViewTopic;
        @Bind(R.id.text_month)
        TextView textViewMonth;
        @Bind(R.id.text_time)
        TextView textViewTime;
        @Bind(R.id.text_seen)
        TextView textViewSeen;
        @Bind(R.id.text_like)
        TextView textViewLike;
        @Bind(R.id.image_topic_pic)
        SimpleDraweeView draweeView;
    }

    public interface OnItemtClickListener{
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemtClickListener onItemtClickListener;

    public void setOnItemtClickListener(OnItemtClickListener onItemtClickListener) {
        this.onItemtClickListener = onItemtClickListener;
    }
}
