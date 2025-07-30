package com.graphql.federation.customer.utilities;

public final class DgsConstants {

    public static final String QUERY_TYPE = "Query";
    public static final String MUTATION_TYPE = "Mutation";

    public static final class QUERY {
        public static final String Customers = "customers";
    }

    public static final class MUTATION {
        public static final String TYPE_NAME = "Mutation";
        public static final String AddCustomer = "addCustomer"; // Match field in schema
    }

    private DgsConstants() {
        // Prevent instantiation
    }
}
