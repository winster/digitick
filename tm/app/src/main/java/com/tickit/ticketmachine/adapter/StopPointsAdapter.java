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
public class StopPointsAdapter extends RecyclerView.Adapter<StopPointsAdapter.ViewHolder> {

    private static final String TAG = "StopPointsAdapter";

    private List<StopDetails> mDataSet;

    OnStopItemClickListener mItemClickListener;

    StopDetails lastSelected = new StopDetails(0, null);

    View lastSelectedButton;

    private Context ctx;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public ViewHolder(View v) {
            super(v);
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element clicked.");
                }
            });*/
            button = (Button) v.findViewById(R.id.stopBtn);
        }

        public Button getStopButton() {
            return button;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public StopPointsAdapter(Context ctx, List<StopDetails> dataSet) {
        this.ctx = ctx;
        mDataSet = new ArrayList<StopDetails>(dataSet);
    }

    public void update(List<StopDetails> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        this.notifyDataSetChanged();
        lastSelectedButton = null;
        lastSelected = new StopDetails(0, null);
    }

    @Override
    public StopPointsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_stop, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.getStopButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, (StopDetails) v.getTag());
                if(lastSelectedButton!=null)
                    lastSelectedButton.setActivated(false);
                lastSelectedButton = v;
                lastSelected = (StopDetails)v.getTag();
                v.setActivated(true);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StopPointsAdapter.ViewHolder viewHolder, final int position) {
        //Log.d(TAG, "Element " + position + " set.");
        viewHolder.getStopButton().setText(String.valueOf(mDataSet.get(position).getStop()));
        viewHolder.getStopButton().setTag(mDataSet.get(position));
        if(lastSelectedButton==null && position==0)
            lastSelectedButton = viewHolder.getStopButton();
        if(lastSelected!=null && lastSelected.getStop()==position)
            viewHolder.getStopButton().setActivated(true);
        else
            viewHolder.getStopButton().setActivated(false);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnStopItemClickListener {
        public void onItemClick(View view , StopDetails position);
    }

    public void setOnStopItemClickListener(final OnStopItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
