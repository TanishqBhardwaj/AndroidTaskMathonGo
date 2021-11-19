package com.example.androidtask_mathongo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidtask_mathongo.R;
import com.example.androidtask_mathongo.local.entity.QuesAnsEntity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuesAnsEntity> quesAnsEntityList;
    private OnItemClickListener mListener;

    public QuestionAdapter(List<QuesAnsEntity> quesAnsEntityList) {
        this.quesAnsEntityList = quesAnsEntityList;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_question,
                parent, false);
        return new QuestionViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuesAnsEntity quesAnsEntity = quesAnsEntityList.get(position);
        holder.textViewQuesNo.setText(quesAnsEntity.getQuesNo());
        holder.textViewQuesTitle.setText(quesAnsEntity.getQuestionText());
        holder.textViewTag.setText(quesAnsEntity.getQuesTag());
    }

    @Override
    public int getItemCount() {
        return quesAnsEntityList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewQuesNo;
        private TextView textViewQuesTitle;
        private TextView textViewTag;

        public QuestionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewQuesNo = itemView.findViewById(R.id.text_view_ques_no);
            textViewQuesTitle = itemView.findViewById(R.id.text_view_ques_title);
            textViewTag = itemView.findViewById(R.id.text_view_tag);

            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onClick(position);
                    }
                }
            });
        }
    }
}
