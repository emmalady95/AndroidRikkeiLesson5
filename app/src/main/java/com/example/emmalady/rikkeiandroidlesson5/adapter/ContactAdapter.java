package com.example.emmalady.rikkeiandroidlesson5.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emmalady.rikkeiandroidlesson5.R;
import com.example.emmalady.rikkeiandroidlesson5.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liz Nguyen on 21/10/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    private List<Contact> userContact = new ArrayList<>();

    public ContactAdapter(List<Contact> userContact){
        this.userContact = userContact;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_main, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Contact contact;
        contact = userContact.get(position);
        holder.tvContactName.setText(contact.getContactName());
        holder.tvContactNumber.setText(String.valueOf(contact.getContactNumber()));
    }

    @Override
    public int getItemCount() {
        return null!=userContact?userContact.size():0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView tvContactName;
        public TextView tvContactNumber;

        public viewHolder(View itemView) {
            super(itemView);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvContactNumber = (TextView) itemView.findViewById(R.id.tvContactNumber);
        }
    }
}
