package com.graphql.federation.review.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.graphql.federation.review.model.Review;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {

	// This can be collected from DB using repository layer.
	private static final Map<Integer, List<Review>> CUSTOMER_REVIEWS_DB = new ConcurrentHashMap<>(Map.of(
		1, List.of(Review.builder().id(1).rating(10).message("Good").build(), 
		Review.builder().id(2).rating(5).message("Not Bad").build()),
		2, List.of(Review.builder().id(3).rating(10).message("Good").build(),
		Review.builder().id(4).rating(10).message("Excellent to work with.").build())
		));


	public List<Review> findByCustomerId(Integer customeId) {
		var reviews = CUSTOMER_REVIEWS_DB.get(customeId);
		log.info("Found {} reviews for customer {}", reviews, customeId);
		return reviews;
	}
}