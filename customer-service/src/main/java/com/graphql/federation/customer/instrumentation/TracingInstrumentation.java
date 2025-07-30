package com.graphql.federation.customer.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.*;
import graphql.execution.instrumentation.parameters.*;
import graphql.schema.*;
import graphql.schema.DataFetcher;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;

public class TracingInstrumentation implements Instrumentation {

    private static final Logger log = LoggerFactory.getLogger(TracingInstrumentation.class);

    @Override
    public InstrumentationState createState(InstrumentationCreateStateParameters parameters) {
        return new CustomTracingState();
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(
            InstrumentationExecutionParameters parameters,
            InstrumentationState state) {
        CustomTracingState tracingState = (CustomTracingState) state;
        tracingState.startTime = System.currentTimeMillis();
        return new SimpleInstrumentationContext<>();
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(
            DataFetcher<?> dataFetcher,
            InstrumentationFieldFetchParameters parameters,
            InstrumentationState state) {
        if (parameters.isTrivialDataFetcher()) {
            return dataFetcher;
        }

        return environment -> {
            long startTime = System.currentTimeMillis();
            Object result = dataFetcher.get(environment);
            if (result instanceof CompletableFuture) {
                ((CompletableFuture<?>) result).whenComplete((r, ex) -> {
                    long totalTime = System.currentTimeMillis() - startTime;
                    log.info("Async datafetcher {} took {}ms", findDatafetcherTag(parameters), totalTime);
                });
            } else {
                long totalTime = System.currentTimeMillis() - startTime;
                log.info("Datafetcher {} took {}ms", findDatafetcherTag(parameters), totalTime);
            }
            return result;
        };
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(
            ExecutionResult executionResult,
            InstrumentationExecutionParameters parameters,
            InstrumentationState state) {
        CustomTracingState tracingState = (CustomTracingState) state;
        long totalTime = System.currentTimeMillis() - tracingState.startTime;
        log.info("Total execution time: {}ms", totalTime);
        return CompletableFuture.completedFuture(executionResult);
    }

    private String findDatafetcherTag(InstrumentationFieldFetchParameters parameters) {
        GraphQLOutputType type = parameters.getExecutionStepInfo().getParent().getType();
        GraphQLObjectType parent;
        if (type instanceof GraphQLNonNull) {
            parent = (GraphQLObjectType) ((GraphQLNonNull) type).getWrappedType();
        } else {
            parent = (GraphQLObjectType) type;
        }

        return parent.getName() + "." + parameters.getExecutionStepInfo().getPath().getSegmentName();
    }

    static class CustomTracingState implements InstrumentationState {
        long startTime;
    }
}
