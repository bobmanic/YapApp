package mdnaseemashraf.yapapp.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Naseem Ashraf on 21-09-2017.
 */

public class YapReviews implements Serializable {
    @SerializedName("reviews")
    @Expose
    private List<YReview> reviews = null;

    public YapReviews() {
    }

    public YapReviews(List<YReview> reviews) {
        super();
        this.reviews = reviews;
    }

    public List<YReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<YReview> reviews) {
        this.reviews = reviews;
    }

    public static class YReview {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("review")
        @Expose
        private String review;
        @SerializedName("cool")
        @Expose
        private Integer cool;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("useful")
        @Expose
        private Integer useful;
        @SerializedName("funny")
        @Expose
        private Integer funny;

        public YReview() {
        }

        public YReview(String date, String userName, String review, Integer cool, Integer rating, Integer useful, Integer funny) {
            super();
            this.date = date;
            this.userName = userName;
            this.review = review;
            this.cool = cool;
            this.rating = rating;
            this.useful = useful;
            this.funny = funny;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public Integer getCool() {
            return cool;
        }

        public void setCool(Integer cool) {
            this.cool = cool;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getUseful() {
            return useful;
        }

        public void setUseful(Integer useful) {
            this.useful = useful;
        }

        public Integer getFunny() {
            return funny;
        }

        public void setFunny(Integer funny) {
            this.funny = funny;
        }

    }

}