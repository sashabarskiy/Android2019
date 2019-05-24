package com.example.myapplication.Contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Contact> contacts;

    public  ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameView.setText(contact.getName());
        holder.phoneView.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, phoneView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            phoneView = view.findViewById(R.id.phone);
        }

    }
}
