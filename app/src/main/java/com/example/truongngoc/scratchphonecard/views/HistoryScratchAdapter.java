package com.example.truongngoc.scratchphonecard.views;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.truongngoc.scratchphonecard.domain.ScratchCard;

import java.util.List;

/**
 * Created by Truong Ngoc Son on 6/4/2016.
 */
public class HistoryScratchAdapter extends ArrayAdapter<ScratchCard> {
    public static final String TAG = HistoryScratchAdapter.class.getSimpleName(); // tag

    Context context; // context
    List<ScratchCard> listOfRecentCards = null; // data of the adapter

    public HistoryScratchAdapter(Context context, int resource, Context context1) {
        super(context, resource);
        context = context1;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public ScratchCard getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
