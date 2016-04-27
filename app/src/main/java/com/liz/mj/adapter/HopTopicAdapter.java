package com.liz.mj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
public class HopTopicAdapter extends BaseAdapter {
    private List<HotTopic> hotTopicsList;
    private Context context;

    public HopTopicAdapter(List<HotTopic> hotTopicsList, Context context) {
        this.hotTopicsList = hotTopicsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hotTopicsList.size();
    }

    @Override
    public Object getItem(int i) {
        return hotTopicsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_topic, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HotTopic topic = hotTopicsList.get(position);
        String date = topic.getCreatedAt();
        holder.textViewDay.setText(date.substring(8, 10));
        holder.textViewMonth.setText(date.substring(5, 7) + "月");
        holder.textViewTime.setText(date.substring(11, 16));
        holder.textViewTopic.setText(topic.getTopic());
        holder.textViewSeen.setText(topic.getSeen() + "");
        holder.textViewLike.setText(topic.getLike() + "");
        return convertView;
    }

    class ViewHolder {
        public ViewHolder(View view) {
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
    }
}
