package com.example.teka.icim_rahat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by teka on 18.7.2017.
 */

public class adapter extends BaseExpandableListAdapter {
    public List<String> list_parent;
    public HashMap<String, List<String>> list_child;
    public Context context;
   public TextView txt;
    public TextView txt_child;
    public LayoutInflater inflater;
    public adapter(List<String> list_parent,Context context,HashMap<String ,List<String >>list_child) {
        this.context=context;
        this.list_parent = list_parent;
        this.list_child=list_child;}
    @Override
    public int getGroupCount() {
        return list_parent.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return list_child.get(list_parent.get(groupPosition)).size();}
    @Override
    public Object getGroup(int groupPosition) {
        return list_parent.get(groupPosition);}
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_child.get(list_parent.get(groupPosition)).get(childPosition);}
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;}
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return  childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title_name = (String)getGroup(groupPosition);
        if(convertView == null) {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent,null);}
        txt = (TextView)convertView.findViewById(R.id.il);
        txt.setText(title_name);
        return convertView;}
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // kaçıncı pozisyonda ise başlığımızın elemanı onun ismini alarak string e atıyoruz
        String txt_child_name = (String)getChild(groupPosition, childPosition);
        if(convertView==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child, null);
            // fonksiyon adından da anlaşılacağı gibi parent a bağlı elemanlarının layoutunu inflate ediyoruz böylece o görüntüye ulaşmış oluyoruz
        }

        txt_child = (TextView)convertView.findViewById(R.id.ilce);
        txt_child.setText(txt_child_name);
        return convertView;
    }





    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {


        return true;
    }

}