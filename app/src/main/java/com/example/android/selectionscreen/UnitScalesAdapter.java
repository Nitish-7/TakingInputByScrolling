package com.example.android.selectionscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnitScalesAdapter extends RecyclerView.Adapter<UnitScalesAdapter.ScaleViewHolder> {

    Litsener litsener;
    int middleUnit;
    Context context;

    public UnitScalesAdapter(int middleUnit, Litsener litsener, Context context) {
        this.middleUnit = middleUnit;
        this.litsener = litsener;
        this.context = context;
    }

    @NonNull
    @Override
    public ScaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScaleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cm_scale_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScaleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                litsener.onScaleUnitClicked(position);
            }
        });
        if (position == middleUnit) {
            holder.getHalfCm().setVisibility(View.INVISIBLE);
            holder.getFullMm().setVisibility(View.INVISIBLE);
            holder.getFullCm().setVisibility(View.VISIBLE);
            holder.getFullCm().getBackground().setTint(context.getResources().getColor(R.color.theme_color));
        } else if (position % 5 == 0 && position % 10 != 0) {
            holder.getHalfCm().setVisibility(View.VISIBLE);
            holder.getFullMm().setVisibility(View.INVISIBLE);
            holder.getFullCm().setVisibility(View.INVISIBLE);
        } else if (position % 10 == 0) {
            holder.getHalfCm().setVisibility(View.INVISIBLE);
            holder.getFullMm().setVisibility(View.INVISIBLE);
            holder.getFullCm().setVisibility(View.VISIBLE);
            holder.getFullCm().getBackground().setTint(context.getResources().getColor(R.color.black));
        } else {
            holder.getHalfCm().setVisibility(View.INVISIBLE);
            holder.getFullMm().setVisibility(View.VISIBLE);
            holder.getFullCm().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 2600;
    }


    class ScaleViewHolder extends RecyclerView.ViewHolder {

        View fullCm;
        View halfCm;
        View fullMm;
        TextView scaleNumericCmValue;

        public ScaleViewHolder(@NonNull View itemView) {
            super(itemView);
            scaleNumericCmValue = itemView.findViewById(R.id.tv_scale_value);
            fullCm = itemView.findViewById(R.id.full_cm);
            halfCm = itemView.findViewById(R.id.half_cm);
            fullMm = itemView.findViewById(R.id.full_mm);
        }

        public TextView getScaleNumericCmValue() {
            return scaleNumericCmValue;
        }

        public View getFullCm() {
            return fullCm;
        }

        public View getHalfCm() {
            return halfCm;
        }

        public View getFullMm() {
            return fullMm;
        }
    }
}

interface Litsener {
    void onScaleUnitClicked(int sacleValue);
}
