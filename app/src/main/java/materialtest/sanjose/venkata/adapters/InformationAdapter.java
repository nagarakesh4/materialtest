package materialtest.sanjose.venkata.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Information;

/**
 * Created by buddhira on 4/24/2015.
 * this is class is for holding the array of information that can be present in the recycler
 * view of the navigation drawer
 *
 * responsible for rendering the recyclerview in nav
 */
/*this will be in a section format , a header and items in recycler view
* each having different holders- gravatar holder and item holder
*
* */
public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    //Dividing the section into header and item sections
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    //create many information objects
    List<Information> dataInformation = Collections.emptyList();
    private Context context;

    public InformationAdapter(Context context, List<Information> dataInformation) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataInformation = dataInformation;
    }

    public void delete(int position) {
        dataInformation.remove(position);
        notifyItemRemoved(position);
    }
    /*
    * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent
     * an item.
     * This new ViewHolder should be constructed with a new View that can represent the items of
     * the given type. You can either create a new View manually or inflate it from an
      * XML layout file.
      *
      *
      * after writing the possibility of two different view types, we cannot just written the myView
      * Holder, instead a generic holder so change the extension of this class from
      * RecyclerView.Adapter<InformationAdapter.MyViewHolder> to generic RecyclerView.ViewHolder
     * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the xml file inflated

        //inflate the view depending on the view type - see getItemViewType
        // the view type will be obtained from the getitemviewtype
        if(viewType == TYPE_HEADER){
            // inflate the header - gravatar
            View view = inflater.inflate(R.layout.movie_drawer_header, parent, false);
            // give the above view xml file to the created custom viewholder which takes care of layout
            // management, thus we are avoiding findviewby id everytime , we create once and the android
            //system will take care of not repeating findviewby id because of recycler view
            GravatarHolder gravatarHolder = new GravatarHolder(view);
            return gravatarHolder;
        }else{
            View view = inflater.inflate(R.layout.item_row, parent, false);

            //Log.i("Venkata", "On Create View Holder");
            // give the above view xml file to the created custom viewholder which takes care of layout
            // management, thus we are avoiding findviewby id everytime , we create once and the android
            //system will take care of not repeating findviewby id because of recycler view
            ItemHolder itemHolder = new ItemHolder(view);
            return itemHolder;
        }
    }

    //tell adapter that there are two different items/sections and inflate accordingly
    @Override
    public int getItemViewType(int position) {
        // if the position is 0 insert the header if the position is 1 then insert the item
        // containing icon and title - see onCreateViewHolder next
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // here we set the data that should correspond to the current row that should be displayed
        // in recycler view
        // we can cache this easily by avoiding findViewById

        // this method is for displaying the data(holder)
        // at the specified position. This method also
        // updates the contents of the itemview

        //get the current data from the whole array list

        //we need to check with which type of item we trying to work with depending on the holder
        if(holder instanceof GravatarHolder){

        }else {
            // if the holder is item holder then set the text and image
            ItemHolder itemHolder = (ItemHolder) holder;
            Information currentData = dataInformation.get(position);
            Log.i("Venkata", "On Bind View Holder at " + position);
            itemHolder.title.setText(currentData.title);
            itemHolder.icon.setImageResource(currentData.iconId);
        }
    }
    /*
    * this is for returning the number of total items list
    * */
    @Override
    public int getItemCount() {
        return dataInformation.size();
    }

    /*we have to create our custom view holder for setting objects*/
    // this holder holds the items in the recycler view
    class ItemHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
    //this holder holds the gravatar and the person details
    class GravatarHolder extends RecyclerView.ViewHolder{
        public GravatarHolder(View itemView) {
            super(itemView);
        }
    }
}
