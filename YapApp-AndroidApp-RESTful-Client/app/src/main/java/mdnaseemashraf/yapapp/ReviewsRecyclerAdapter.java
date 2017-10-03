package mdnaseemashraf.yapapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import mdnaseemashraf.yapapp.models.YapReviews;

/**
 * Created by Naseem Ashraf on 21-09-2017.
 */

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ReviewViewHolder> {

    private List<YapReviews.YReview> yapReviews;

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvDate, tvCool, tvFunny, tvUseful, tvReview;
        public RatingBar ratingBar;

        public Context context;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvCool = (TextView) itemView.findViewById(R.id.tvCool);
            tvFunny = (TextView) itemView.findViewById(R.id.tvFunny);
            tvUseful = (TextView) itemView.findViewById(R.id.tvUseful);
            tvReview = (TextView) itemView.findViewById(R.id.tvReview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }

    public ReviewsRecyclerAdapter(List<YapReviews.YReview> yapReviewsIn) {
        this.yapReviews = yapReviewsIn;
    }

    @Override
    public ReviewsRecyclerAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);

        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewsRecyclerAdapter.ReviewViewHolder holder, int position) {
        YapReviews.YReview yapReview = yapReviews.get(position);
        holder.tvUserName.setText(yapReview.getUserName());
        holder.tvDate.setText(yapReview.getDate());
        holder.tvCool.setText("Cool:" + String.valueOf(yapReview.getCool()));
        holder.tvFunny.setText("Funny:" + String.valueOf(yapReview.getFunny()));
        holder.tvUseful.setText("Useful:" + String.valueOf(yapReview.getUseful()));
        holder.tvReview.setText(yapReview.getReview());
        holder.ratingBar.setRating(yapReview.getRating());
    }

    @Override
    public int getItemCount() {
        if (yapReviews != null) {
            return yapReviews.size();
        } else {
            return 0;
        }
    }
}
