
package com.graphql.federation.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Review {
    private int id;
    private int rating;
    private String message;
}
