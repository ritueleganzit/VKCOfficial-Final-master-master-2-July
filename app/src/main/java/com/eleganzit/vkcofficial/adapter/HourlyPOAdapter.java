package com.eleganzit.vkcofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.vkcofficial.R;
import com.eleganzit.vkcofficial.model.griddata.GridData;

import java.util.List;

public class HourlyPOAdapter extends RecyclerView.Adapter<HourlyPOAdapter.MyViewHolder> {

    List<GridData> campaigns;
    Context context;
    Activity activity;

    public HourlyPOAdapter(List<GridData> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_hourlypo,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
GridData gridData=campaigns.get(i);
if (gridData.getGridValue()!=null && !(gridData.getGridValue().isEmpty()))
{
    holder.grid_value.setText(""+gridData.getGridValue());

}
else
{
    holder.grid_value.setText("-");

}

if (gridData.getScheduledQuantity()!=null && !(gridData.getScheduledQuantity().isEmpty()))
{
    holder.scheduled_quantity.setText(""+gridData.getScheduledQuantity());

}
else
{
    holder.scheduled_quantity.setText("-");

}
if (gridData.getQualityProduced()!=null && !(gridData.getQualityProduced().isEmpty()))
{
    holder.quality_produced.setText(""+gridData.getQualityProduced());

}
else
{
    holder.quality_produced.setText("-");

}

if (gridData.getStartTime()!=null && !(gridData.getStartTime().isEmpty()))
{
    holder.start_time.setText(""+gridData.getStartTime());

}
else
{
    holder.start_time.setText("-");

}
if (gridData.getEndTime()!=null && !(gridData.getEndTime().isEmpty()))
{
    holder.end_time.setText(""+gridData.getEndTime());

}
else
{
    holder.end_time.setText("-");

}

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView grid_value,scheduled_quantity,quality_produced,start_time,end_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_value=itemView.findViewById(R.id.grid_value);
            scheduled_quantity=itemView.findViewById(R.id.scheduled_quantity);
            quality_produced=itemView.findViewById(R.id.quality_produced);
            start_time=itemView.findViewById(R.id.start_time);
            end_time=itemView.findViewById(R.id.end_time);

        }
    }
}
