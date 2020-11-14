package com.example.musicapp.wave_tuner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.musicapp.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expListTitle;
    private HashMap<String, List<String>> expListDetail;

    public ExpandableListAdapter(Context context, List<String> expListTitle,
                                 HashMap<String, List<String>> expListDetail) {
        this.context = context;
        this.expListTitle = expListTitle;
        this.expListDetail = expListDetail;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expListDetail.get(
                expListTitle.get(groupPosition)
        ).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String expListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ex_list_item_sound_effect, null);
        }
        CheckBox expListCheckBox = (CheckBox) convertView.findViewById(R.id.checkBoxExpListWaveTuner);
        expListCheckBox.setText(expListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expListDetail.get(expListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expListTitle.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return expListTitle.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ex_list_group_sound_effect, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.textViewGroupSoundEffect);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
