package com.tickit.ticketmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tickit.ticketmachine.R;
import com.tickit.ticketmachine.vo.StopDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjose on 23/03/2016.
 */
public class DestinationPointsAdapter extends RecyclerView.Adapter<DestinationPointsAdapter.ViewHolder> {

    private static final String TAG = "DestinationPointsAdapter";

    private List<StopDetails> mDataSet;

    OnStopItemClickListener mItemClickListener;

    StopDetails lastSelected=null;

    View lastSelectedButton;

    private Context ctx;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public ViewHolder(View v) {
            super(v);
            button = (Button) v.findViewById(R.id.destBtn);
        }

        public Button getDestButton() {
            return button;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public DestinationPointsAdapter(Context ctx, List<StopDetails> dataSet) {
        this.ctx = ctx;
        mDataSet = new ArrayList<StopDetails>(dataSet);
    }

    public void update(List<StopDetails> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        this.notifyDataSetChanged();
        lastSelectedButton = null;
        lastSelected = null;
    }

    @Override
    public DestinationPointsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dest, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.getDestButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastSelectedButton!=null)
                    lastSelectedButton.setActivated(false);
                lastSelected = (StopDetails)v.getTag();
                lastSelectedButton = v;
                v.setActivated(true);
                mItemClickListener.onItemClick(v, (StopDetails) v.getTag());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DestinationPointsAdapter.ViewHolder viewHolder, final int position) {
        //Log.d(TAG, "Element " + position + " set.");
        viewHolder.getDestButton().setText(String.valueOf(mDataSet.get(position).getStop()));
        viewHolder.getDestButton().setTag(mDataSet.get(position));
        if(lastSelected!=null && lastSelected.getStop()==position)
            viewHolder.getDestButton().setActivated(true);
        else
            viewHolder.getDestButton().setActivated(false);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnStopItemClickListener {
        public void onItemClick(View view, StopDetails stop);
    }

    public void setOnStopItemClickListener(final OnStopItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void revertLastSelection(){
        lastSelected = null;
        if(lastSelectedButton!=null)
            lastSelectedButton.setActivated(false);
        lastSelectedButton = null;
    }
}
