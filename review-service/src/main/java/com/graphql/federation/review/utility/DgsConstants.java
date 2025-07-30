package com.graphql.federation.review.utility;

public final class DgsConstants {

    public static final class CUSTOMER {
        public static final String TYPE_NAME = "Customer";
        public static final String Reviews = "reviews";
        public static final String Id = "id";
        public static final String Name = "name";
    }

    public static final class REVIEW {
        public static final String TYPE_NAME = "Review";
        public static final String Id = "id";
        public static final String Rating = "rating";
        public static final String Message = "message";
    }

    private DgsConstants() {
        // Prevent instantiation
    }
}

