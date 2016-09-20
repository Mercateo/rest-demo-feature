# rest-demo-jersey [![Build Status](https://travis-ci.org/Mercateo/rest-demo-feature.svg?branch=master)](https://travis-ci.org/Mercateo/rest-demo-feature) [![Coverage Status](https://coveralls.io/repos/github/Mercateo/rest-demo-feature/badge.svg?branch=master)](https://coveralls.io/github/Mercateo/rest-demo-feature?branch=master)
Spring-boot and Jersey based REST service showing the integration of [rest-schemagen](http://github.com/Mercateo/rest-schemagen) and Feature Toggles.

There is also a simple demo of jerseys build in mechanisms for link building. See com.mercateo.demo.resources.jersey.linking.OrdersLinkingResource

To play a little bit with the Feature around, manipulate com.mercateo.demo.feature.SimpleFeatureChecker.

## Example usage

### get base info
GET: http://localhost:9090

* orders: GET /orders
    
