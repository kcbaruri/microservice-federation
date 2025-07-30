
package com.graphql.federation.review.resolver;

import java.util.List;
import java.util.Map;

import com.graphql.federation.review.model.Customer;
import com.graphql.federation.review.model.Review;
import com.graphql.federation.review.service.ReviewService;
import com.graphql.federation.review.utility.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;

import lombok.AllArgsConstructor;

@DgsComponent
@AllArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@DgsEntityFetcher(name = DgsConstants.CUSTOMER.TYPE_NAME)
	public Customer customer(Map<String, Object> values) {
		return new Customer((Integer) values.get("id"), null);
	}

	@DgsData(parentType = DgsConstants.CUSTOMER.TYPE_NAME, field = DgsConstants.CUSTOMER.Reviews)
	public List<Review> findUserReviews(DgsDataFetchingEnvironment dataFetchingEnvironment)  {
		Customer customer = dataFetchingEnvironment.getSource();
		return reviewService.findByCustomerId(customer.getId());
	}
}
