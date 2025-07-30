package com.graphql.federation.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String firstName;
}