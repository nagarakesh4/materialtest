package materialtest.sanjose.venkata.materialtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by buddhira on 4/24/2015.
 * this is class is for holding the array of information that can be present in the recycler
 * view
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    //create many information objects
    List<InformationRow> dataInformation = Collections.emptyList();

    public InformationAdapter(Context context, List<InformationRow> dataInformation) {
        inflater = LayoutInflater.from(context);
        this.dataInformation = dataInformation;
    }
    /*
    * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent
     * an item.
     * This new ViewHolder should be constructed with a new View that can represent the items of
     * the given type. You can either create a new View manually or inflate it from an
      * XML layout file.
     * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the xml file inflated
        View view = inflater.inflate(R.layout.item_row, parent, false);
        // give the above view xml file to the created custom viewholder which takes care of layout
        // management, thus we are avoiding findviewby id everytime , we create once and the android
        //system will take care of not repeating findviewby id because of recycler view
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // here we set the data that should correspond to the current row that should be displayed
        // in recycler view
        // we can cache this easily by avoiding findViewById

        // this method is for displaying the data(holder)
        // at the specified position. This method also
        // updates the contents of the itemview

        //get the current data from the whole array list
        InformationRow currentData = dataInformation.get(position);
        holder.title.setText(currentData.title);
        holder.icon.setImageResource(currentData.iconId);
    }
    /*
    * this is for returning the number of total items list
    * */
    @Override
    public int getItemCount() {
        return dataInformation.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
