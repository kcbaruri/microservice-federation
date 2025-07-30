const dotenv = require("dotenv");
dotenv.config();

module.exports = {
  endpoints: parseEndpoints(process.env.GRAPHQL_ENDPOINTS)
};

function parseEndpoints(conf) {
  const endpoints = conf.split(",");
  return endpoints.map(endpoint => {
    const delimiterIndex = endpoint.indexOf(":");
    const name = endpoint.slice(0, delimiterIndex);
    const url = endpoint.slice(delimiterIndex + 1);
    return { name, url };
  });
}

