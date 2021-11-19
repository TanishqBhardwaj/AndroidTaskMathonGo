package com.example.androidtask_mathongo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<QuesAnsEntity> quesAnsEntityList;
    private OnItemClickListener mListener;
    private int quesPosition;

    public OptionAdapter(List<QuesAnsEntity> quesAnsEntityList, int quesPosition) {
        this.quesAnsEntityList = quesAnsEntityList;
        this.quesPosition = quesPosition;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_option,
                parent, false);
        return new OptionViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        QuesAnsModel.Option option = quesAnsEntityList.get(quesPosition).getOptionList().get(position);
        holder.textViewAlphabet.setText(String.valueOf((char)(65 + position)));
        holder.textViewOption.setText(option.getOptionText());
    }

    @Override
    public int getItemCount() {
        Log.e("TAG", "getItemCount: " + quesAnsEntityList.get(quesPosition).getOptionList().size() );
        return quesAnsEntityList.get(quesPosition).getOptionList().size();
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAlphabet;
        private TextView textViewOption;

        public OptionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewAlphabet = itemView.findViewById(R.id.text_view_option_alphabet);
            textViewOption = itemView.findViewById(R.id.text_view_option);
        }
    }
}
