package in.apptonic.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lalitkumarsonawane on 24/11/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Essay> essays;
    protected Context ctx;

    public MyAdapter(){}

    public MyAdapter(Context ctx, List<Essay> essayList) {
        this.essays = essayList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.essayTitle.setText(essays.get(position).getSubject());

    }

    @Override
    public int getItemCount() {

        return essays.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView essayTitle;


        public MyViewHolder(View itemView) {
            super(itemView);

            essayTitle = itemView.findViewById(R.id.first_line);

        }
    }
}
