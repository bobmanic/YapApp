package mdnaseemashraf.yapapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Naseem Ashraf on 21-09-2017.
 */

public class YapBusinesses {
    @SerializedName("businesses")
    @Expose
    private List<YBusiness> businesses = null;

    public List<YBusiness> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<YBusiness> businesses) {
        this.businesses = businesses;
    }

    public int getBusinessesCount() {
        return businesses.size();
    }

    public static class YBusiness {
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("avg.rating")
        @Expose
        private float avgRating;
        @SerializedName("neighbourhood")
        @Expose
        private String neighbourhood;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("longitude")
        @Expose
        private Double longitude;

        public YBusiness() {
        }

        public YBusiness(String businessName, String city, float avgRating, String neighbourhood, Double latitude, String state, String type, String businessId, Double longitude) {
            super();
            this.businessName = businessName;
            this.city = city;
            this.avgRating = avgRating;
            this.neighbourhood = neighbourhood;
            this.latitude = latitude;
            this.state = state;
            this.type = type;
            this.businessId = businessId;
            this.longitude = longitude;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public float getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(float avgRating) {
            this.avgRating = avgRating;
        }

        public String getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
}
