package com.example.androidtask_mathongo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.local.entity.OptionEntity;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import com.example.androidtask_mathongo.model.QuesAnsModel;
import com.example.androidtask_mathongo.util.Constants;
import com.google.android.material.card.MaterialCardView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<QuesAnsEntity> quesAnsEntityList;
    private List<OptionEntity> optionEntityList;
    private OnItemClickListener mListener;
    private int quesPosition;
    private Context context;
    private String whyUpdate;

    public OptionAdapter(List<QuesAnsEntity> quesAnsEntityList, int quesPosition, Context context,
                         List<OptionEntity> optionEntityList) {
        this.quesAnsEntityList = quesAnsEntityList;
        this.quesPosition = quesPosition;
        this.context = context;
        this.optionEntityList = optionEntityList;
        this.whyUpdate = "";
    }

    public interface OnItemClickListener {
        void onClick(int position, boolean isClickable);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_option,
                parent, false);
        return new OptionViewHolder(view, mListener, true);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        QuesAnsModel.Option option = quesAnsEntityList.get(quesPosition).getOptionList().get(position);
        holder.textViewAlphabet.setText(String.valueOf((char)(65 + position)));
        holder.textViewOption.setText(option.getOptionText());

        //this condition checks if the question's answer is already checked
        if(isChecked(quesAnsEntityList.get(quesPosition))) {
            //setListener is called in order to make cards not clickable
            holder.setListener(mListener, false);

            //this condition shows the right card position
            if (checkAnswer(position)) {
                holder.rightAnswerState(context);
            }

            //this condition checks if card is selected and is wrong then show it
            if(!checkAnswer(position) && isSelected(quesAnsEntityList.get(quesPosition), position)) {
                holder.wrongAnswerState(context);
            }
        }
        else {
            if(isSelected(quesAnsEntityList.get(quesPosition), position)) {
                holder.setOnClickOption(context);
            }
            else {
                holder.setRemoveClickOption(context);
            }
        }
        //this condition checks if the list was updated for check answer, then show the correct option
        if(whyUpdate.equals(Constants.CHECK_ANSWER) && option.isCorrect()) {
            //setListener is called in order to make cards not clickable
            holder.setListener(mListener, false);

            holder.rightAnswerState(context);
        }
    }

    private boolean isSelected(QuesAnsEntity quesAnsEntity, int position) {
        for(OptionEntity optionEntity : optionEntityList) {
            if(optionEntity.getQuesId().equals(quesAnsEntity.getId()) &&
                    optionEntity.getOptionId().equals(quesAnsEntity.getOptionList().get(position).getId())) {
                return true;
            }
        }

        return false;
    }

    private boolean isChecked(QuesAnsEntity quesAnsEntity) {
        for(OptionEntity optionEntity : optionEntityList) {
            if(optionEntity.getQuesId().equals(quesAnsEntity.getId())) {
                return optionEntity.isChecked();
            }
        }

        return false;
    }

    private boolean checkAnswer(int optionPosition) {
        return quesAnsEntityList.get(quesPosition).getOptionList().get(optionPosition).isCorrect();
    }

    //whyUpdate tells the reason of update
    public void updateList(List<QuesAnsEntity> quesAnsEntities, List<OptionEntity> optionEntities, String whyUpdate) {
        quesAnsEntityList = new ArrayList<>(quesAnsEntities);
        optionEntityList = new ArrayList<>(optionEntities);
        this.whyUpdate = whyUpdate;
        notifyDataSetChanged();
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


        public OptionViewHolder(@NonNull View itemView, OnItemClickListener listener, boolean isClickable) {
            super(itemView);
            textViewAlphabet = itemView.findViewById(R.id.text_view_option_alphabet);
            textViewOption = itemView.findViewById(R.id.text_view_option);
            cardViewOption = itemView.findViewById(R.id.card_view_option);
            cardViewAlphabet = itemView.findViewById(R.id.card_view_option_alphabet);
            cardViewAnsType = itemView.findViewById(R.id.card_view_ans_type);
            textViewAnsType = itemView.findViewById(R.id.text_view_ans_type);

            setListener(listener, isClickable);
        }

        private void setListener(OnItemClickListener listener, boolean isClickable) {
            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onClick(position, isClickable);
                    }
                }
            });
        }

        private void setOnClickOption(Context context) {
            cardViewAlphabet.setCardBackgroundColor(context.getResources().getColor(R.color.blue_theme));
            cardViewOption.setStrokeColor(context.getResources().getColor(R.color.blue_theme));
        }

        private void setRemoveClickOption(Context context) {
            cardViewAlphabet.setCardBackgroundColor(context.getResources().getColor(R.color.option_unselected_color));
            cardViewOption.setStrokeColor(context.getResources().getColor(R.color.option_unselected_color));
        }

        private void rightAnswerState(Context context) {
            cardViewAlphabet.setCardBackgroundColor(context.getResources().getColor(
                    R.color.right_ans_color));
            cardViewOption.setStrokeColor(context.getResources().getColor(R.color.right_ans_color));
            cardViewAnsType.setVisibility(View.VISIBLE);
            textViewAnsType.setVisibility(View.VISIBLE);
            cardViewAnsType.setStrokeColor(context.getResources().getColor(
                    R.color.right_ans_color));
            textViewAnsType.setText(R.string.you_marked_correct_answer);
            textViewAnsType.setTextColor(context.getResources().getColor(
                    R.color.right_ans_color));
        }

        private void wrongAnswerState(Context context) {
            cardViewAlphabet.setCardBackgroundColor(context.getResources().getColor(
                    R.color.wrong_ans_color));
            cardViewOption.setStrokeColor(context.getResources().getColor(R.color.wrong_ans_color));
            cardViewAnsType.setVisibility(View.VISIBLE);
            textViewAnsType.setVisibility(View.VISIBLE);
            cardViewAnsType.setStrokeColor(context.getResources().getColor(
                    R.color.wrong_ans_color));
            textViewAnsType.setText(R.string.you_marked_incorrect_answer);
            textViewAnsType.setTextColor(context.getResources().getColor(
                    R.color.wrong_ans_color));
        }
    }
}
