package mdnaseemashraf.yapapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import mdnaseemashraf.yapapp.models.YapBusinesses;

/**
 * Created by Naseem Ashraf on 21-09-2017.
 */

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<BusinessRecyclerAdapter.BusinessViewHolder> {

    private List<YapBusinesses.YBusiness> yapBusinesses;

    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBusinessName, tvType, tvCity;
        public RatingBar ratingBar;

        public Context context;

        public BusinessViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            tvBusinessName = (TextView) itemView.findViewById(R.id.tvBusinessName);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();

                    String bname = yapBusinesses.get(itemPosition).getBusinessName();
                    String bneigh = yapBusinesses.get(itemPosition).getNeighbourhood();
                    Double blat = yapBusinesses.get(itemPosition).getLatitude();
                    Double blong = yapBusinesses.get(itemPosition).getLongitude();
                    String bcity = yapBusinesses.get(itemPosition).getCity();
                    String bstate = yapBusinesses.get(itemPosition).getState();
                    String btype = yapBusinesses.get(itemPosition).getType();
                    float brating = yapBusinesses.get(itemPosition).getAvgRating();

                    String bid = yapBusinesses.get(itemPosition).getBusinessId();

                    Intent reviewsIntent = new Intent(context, BusinessItemActivity.class);
                    reviewsIntent.putExtra("bname", bname);
                    reviewsIntent.putExtra("bneigh", bneigh);
                    reviewsIntent.putExtra("blat", blat);
                    reviewsIntent.putExtra("blong", blong);
                    reviewsIntent.putExtra("bcity", bcity);
                    reviewsIntent.putExtra("bstate", bstate);
                    reviewsIntent.putExtra("btype", btype);
                    reviewsIntent.putExtra("brating", brating);

                    reviewsIntent.putExtra("bid", bid);


                    view.getContext().startActivity(reviewsIntent);
                }
            });
        }
    }

    public BusinessRecyclerAdapter(List<YapBusinesses.YBusiness> yapBusinessesIn) {
        this.yapBusinesses = yapBusinessesIn;
    }

    @Override
    public BusinessRecyclerAdapter.BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_business, parent, false);

        return new BusinessViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BusinessRecyclerAdapter.BusinessViewHolder holder, int position) {
        YapBusinesses.YBusiness yapBusiness = yapBusinesses.get(position);
        holder.tvBusinessName.setText(yapBusiness.getBusinessName());
        holder.tvCity.setText(yapBusiness.getCity());
        holder.tvType.setText(yapBusiness.getType().substring(0, 1).toUpperCase() + yapBusiness.getType().substring(1));
        holder.ratingBar.setRating(yapBusiness.getAvgRating());
    }

    @Override
    public int getItemCount() {
        if (yapBusinesses != null) {
            return yapBusinesses.size();
        } else {
            return 0;
        }
    }
}
