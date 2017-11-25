package in.apptonic.recyclerviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static android.content.ContentValues.TAG;

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
        MyViewHolder myViewHolder = new MyViewHolder(view, essays);

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
        ImageView book;
        ImageView delete;
        List<Essay> listObject = new ArrayList<>();

        public MyViewHolder(View itemView, final List<Essay> listObject) {
            super(itemView);
            this.listObject = listObject;

            essayTitle = itemView.findViewById(R.id.first_line);
            book = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.imageView2);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Delete icon clicked", Toast.LENGTH_SHORT).show();
                    String essayTask = listObject.get(getAdapterPosition()).getSubject();
                    Log.d(TAG, "Essay Title" + essayTitle);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query appleQuery = reference.orderByChild("subject").equalTo(essayTask);

                    appleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()){

                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Log.e(TAG, "onCancelled", databaseError.toException());

                        }
                    });

                }
            });


        }
    }
}
