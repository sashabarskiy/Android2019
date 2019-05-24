package com.example.myapplication.Messgae;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Message> messages;

    public MessagesAdapter(Context context, List<Message> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.mod_timeView.setText(message.getMod_time());
        holder.dv0View.setText(String.format(Locale.ROOT,"%.4f л.",message.getDv0()));
        holder.tempView.setText(String.format(Locale.ROOT,"%.1f C°",message.getTemp()));
        holder.vbatView.setText(String.format(Locale.ROOT,"%.4f V",message.getVbat()));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mod_timeView, dv0View, tempView, vbatView;

        ViewHolder(View view){
            super(view);
            mod_timeView = view.findViewById(R.id.mod_time);
            dv0View = view.findViewById(R.id.dv0);
            tempView = view.findViewById(R.id.temp);
            vbatView = view.findViewById(R.id.vbat);
        }

    }
}
