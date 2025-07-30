const { ApolloServer, gql } = require('apollo-server');
const {ApolloGateway} = require('@apollo/gateway')
// const { endpoints } = require("./config");

const gateway = new ApolloGateway({
  serviceList: [
    { name: 'customer-service', url: 'http://localhost:8081/graphql' },
    { name: 'review-service', url: 'http://localhost:8082/graphql' }
  ],
  experimental_pollInterval: 10000 
});

// console.log("Configured endpoints: ", endpoints)

const server = new ApolloServer({ gateway, subscriptions:false });
server.listen();