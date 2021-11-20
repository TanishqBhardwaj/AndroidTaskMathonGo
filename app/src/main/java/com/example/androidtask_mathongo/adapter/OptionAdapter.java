package com.example.androidtask_mathongo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.google.android.material.card.MaterialCardView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<QuesAnsEntity> quesAnsEntityList;
    private List<OptionEntity> optionEntityList;
    private OnItemClickListener mListener;
    private int quesPosition;
    private Context context;

    public OptionAdapter(List<QuesAnsEntity> quesAnsEntityList, int quesPosition, Context context,
                         List<OptionEntity> optionEntityList) {
        this.quesAnsEntityList = quesAnsEntityList;
        this.quesPosition = quesPosition;
        this.context = context;
        this.optionEntityList = optionEntityList;
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
        return new OptionViewHolder(view, mListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        QuesAnsModel.Option option = quesAnsEntityList.get(quesPosition).getOptionList().get(position);
        holder.textViewAlphabet.setText(String.valueOf((char)(65 + position)));
        holder.textViewOption.setText(option.getOptionText());
        if(isSelected(quesAnsEntityList.get(quesPosition), position)) {
            holder.setOnClickOption(context);
        }
    }

    private boolean isSelected(QuesAnsEntity quesAnsEntity, int position) {
        for(OptionEntity optionEntity : optionEntityList) {
            if(optionEntity.getQuesId().equals(quesAnsEntity.getId()) &&
                    optionEntity.getOptionId().equals(quesAnsEntity.getOptionList().get(position).getId())) {
                return optionEntity.isChecked();
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return quesAnsEntityList.get(quesPosition).getOptionList().size();
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAlphabet;
        private TextView textViewOption;
        private MaterialCardView cardViewOption;
        private MaterialCardView cardViewAlphabet;
        private MaterialCardView cardViewAnsType;
        private TextView textViewAnsType;


        public OptionViewHolder(@NonNull View itemView, OnItemClickListener listener, Context context) {
            super(itemView);
            textViewAlphabet = itemView.findViewById(R.id.text_view_option_alphabet);
            textViewOption = itemView.findViewById(R.id.text_view_option);
            cardViewOption = itemView.findViewById(R.id.card_view_option);
            cardViewAlphabet = itemView.findViewById(R.id.card_view_option_alphabet);
            cardViewAnsType = itemView.findViewById(R.id.card_view_ans_type);
            textViewAnsType = itemView.findViewById(R.id.text_view_ans_type);

            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onClick(position);
                    }
                    setOnClickOption(context);
                }
            });
        }

        private void setOnClickOption(Context context) {
            cardViewAlphabet.setCardBackgroundColor(context.getResources().getColor(R.color.blue_theme));
            cardViewOption.setStrokeColor(context.getResources().getColor(R.color.blue_theme));
        }
    }
}
