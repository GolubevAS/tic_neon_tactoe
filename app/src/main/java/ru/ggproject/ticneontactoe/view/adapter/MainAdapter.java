package ru.ggproject.ticneontactoe.view.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.ggproject.ticneontactoe.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.ggproject.ticneontactoe.model.PlayingChessModel;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecordViewHolder> {

    Context mContext;
    List<PlayingChessModel> playingChessModels;

    public MainAdapter(Context mContext, List<PlayingChessModel> playingChessModels){
        this.mContext = mContext;
        this.playingChessModels = playingChessModels;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecordViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_record, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        PlayingChessModel playingChessModel = playingChessModels.get(position);
        holder.tvPlayer1.setText(playingChessModel.getPlayer1());
        holder.tvPlayer2.setText(playingChessModel.getPlayer2());
        if(playingChessModel.getPlayer1WinNumber() > playingChessModel.getPlayer2WinNumber()){
            holder.tvPlayer1WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_win));
            holder.tvPlayer2WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_fail));
        }else if(playingChessModel.getPlayer1WinNumber() < playingChessModel.getPlayer2WinNumber()){
            holder.tvPlayer2WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_win));
            holder.tvPlayer1WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_fail));
        }else {
            holder.tvPlayer1WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_draw));
            holder.tvPlayer2WinNumber.setTextColor(mContext.getResources().getColor(R.color.num_draw));
        }
        holder.tvPlayer1WinNumber.setText(playingChessModel.getPlayer1WinNumber()+"");
        holder.tvPlayer2WinNumber.setText(playingChessModel.getPlayer2WinNumber()+"");
        holder.tvDrawNumber.setText("Ничья:"+playingChessModel.getDraw());
        StringBuffer sb = new StringBuffer();
        long time = (System.currentTimeMillis() - playingChessModel.getTime())/1000;
        if(time < 60){
            sb.append(time+"  Недавно");
        }else if(time < 60 * 60) {
            sb.append(time/60+"м");
            sb.append(time%60+"  Недавно");
        }else if(time < 60 * 60 * 24){
            sb.append(time/60/60+"ч");
            sb.append((time%(60*60))/60+"  Недавно");
        }else {
            sb.append(time/60/60/24+"д");
            sb.append((time%(60*60*24))/(60*60)+"  Недавно");
        }
        holder.tvTime.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return playingChessModels == null ? 0 : playingChessModels.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayer1, tvPlayer2, tvPlayer1WinNumber, tvPlayer2WinNumber, tvDrawNumber, tvTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayer1 = itemView.findViewById(R.id.tv_player1);
            tvPlayer2 = itemView.findViewById(R.id.tv_player2);
            tvPlayer1WinNumber = itemView.findViewById(R.id.tv_player1_win_number);
            tvPlayer2WinNumber = itemView.findViewById(R.id.tv_player2_win_number);
            tvDrawNumber = itemView.findViewById(R.id.tv_draw);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

}


