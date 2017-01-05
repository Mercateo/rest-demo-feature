# rest-demo-jersey [![Build Status](https://travis-ci.org/Mercateo/rest-demo-feature.svg?branch=major-refactoring)](https://travis-ci.org/Mercateo/rest-demo-feature) [![Coverage Status](https://coveralls.io/repos/github/Mercateo/rest-demo-feature/badge.svg?branch=major-refactoring)](https://coveralls.io/github/Mercateo/rest-demo-feature?branch=major-refactoring)
Spring-boot and Jersey based REST service showing a lot of things, we discovered in the last time. 
## startup
The Main is in com.mercateo.demo.OrderServer
## entry point
The entry point for the HATEOAS based API is a GET to http://localhost:9090  
### get base info
GET: http://localhost:9090
There are 3 links
* orders: the little order service
* orders-linking: demo of the jersey built in way to do HATEOAS
* returns: the orders, which are sent back this link is toggled by a feature
    

# The integration of [rest-schemagen](http://github.com/Mercateo/rest-schemagen) and Feature Toggles
To play a little bit with the Feature around, manipulate com.mercateo.demo.feature.SimpleFeatureChecker. Feature 5 toggles the ability to send an order back. Feature 6 toggles the prefered carrier attribute of the sending back object. There are a lot of things we want to show here:
* Feature toggling is just the toggling of a link (which is a uniform approach). The concrete reason, why the link isn't there, is not in the concern of the client. So the server doesn't have to expose the logic and can therefore change it.
* Feature Toggling and Link generation just work with annotations in our link-building framework. One only has to write com.mercateo.demo.feature.FeatureFieldChecker and com.mercateo.demo.feature.FeatureMethodChecker. Having all the advantages of declarative programming.
**Note:** The com.mercateo.demo.feature.Feature and the feature checkers are only simple "mocks", of course one can bring here own Annotations and frameworks like [togglz]{https://www.togglz.org/}. 

# The resource way to do things 
After starting the server, the resource http://localhost:9090/orders/2 looks like (omitting some lines):
```javascript
   {
        "href": "http://localhost:9090/orders/2/send-back",
        "schema": {
          "type": "object",
          "properties": {
            "message": {
              "type": "string"
            }
          },
          "required": [
            "message"
          ]
        },
        "method": "PUT",
        "rel": "send-back",
        "relType": "inherited",
        "mediaType": "application/json",
        "target": "_parent"
      },
      {
        "href": "http://localhost:9090/returns",
        "schema": {
          "type": "object",
          "properties": {
            "message": {
              "type": "string"
            },
            "orderId": {
              "type": "string",
              "enum": [
                "2"
              ]
            }
          },
          "required": [
            "message",
            "orderId"
          ]
        },
        "method": "PUT",
        "rel": "send-back-noun",
        "relType": "inherited",
        "mediaType": "application/json",
        "target": "_parent"
      }
```

The first link shows the object oriented programming style. There is an order object and one calls a send-back method on it. According to this [article](https://www.thoughtworks.com/de/insights/blog/rest-api-design-resource-modeling) this is not the best way of modeling a REST-API. Advantages are the less effort for documentations (orderId is not in the schema and therefore does nor have to be documented) and the more "natural" way to do things. Albeit one programs in fact SOAPlike-APIS.

The second link shows the resource way to do it. There is a second "returns" resource. The schema shows, that the orderId can only be filled with the current order id at the moment. If one uses a schema-less link format (like e.g. HAL), this information has to be transported via documentation to the client programmer. 
If one does the modeling in that way, the whole server interaction is uniform. Meaning no other verbs than the HTTP-verbs are involved. 
The semantic meaning is only transported via the link relation. But there is a great danger, ending up in simple CRUD-APIs, spreading the whole domain knowledge over all clients. Leading to the prejudice that one can not model status transformations with REST.
To emphasis here: if you do the resource way and do not have links, you miss the whole semantic! So one ends up in having all state informations in the documentation and in the client code. Leading to unmodifiable APIs. 

# templates or not needing them
In the past, we used templates to get URLs for resource, from which we know the id beforehand. This is replaced by search parameters, like in http://localhost:9090/orders
```javascript
{
        "href": "http://localhost:9090/orders?offset=0&limit=100",
        "schema": {
          "type": "object",
          "properties": {
            "id": {
              "type": "object",
              "properties": {
                "id": {
                  "type": "string"
                }
              }
            },
            "limit": {
              "type": "integer"
            },
            "offset": {
              "type": "integer"
            }
          }
        },
        "targetSchema": {
          "type": "object",
          "properties": {
            "limit": {
              "type": "integer"
            },
            "members": {
              "type": "array",
              "items": {
                "type": "object",
                "properties": {
                  "id": {
                    "type": "string"
                  },
                  "state": {
                    "type": "string",
                    "enum": [
                      "SHIPPED",
                      "PROCESSING",
                      "OPEN",
                      "CANCELED",
                      "RETURNED"
                    ]
                  },
                  "total": {
                    "type": "number"
                  }
                }
              }
            },
            "offset": {
              "type": "integer"
            },
            "total": {
              "type": "integer"
            }
          }
        },
        "rel": "self",
        "mediaType": "application/json",
        "method": "GET"
      }
``` 
There is a query parameter "id", where you can fill in the known id and get (hopefully :-)) a collection with one member.

As one can see in the "returns" resource, there is no id query parameter. That's because the link to the specific return resource in the response of the "create" requests. So one can save the whole link description object, like mentioned [here](http://blog.ploeh.dk/2016/12/07/domain-modelling-with-rest/). So there is no need to guess the link for a known id from a template.

# jersey linking
There is also a simple demo of jerseys build in mechanisms for link building. See com.mercateo.demo.resources.jersey.linking.OrdersLinkingResource

# disclaimer
For data, spring.data.jpa with in-memory db is used, because it leads to a fast way to store some things. The persistence is completely out of scope of this demo. The only thing, one should notice is the CQRS-principle for the returns, which is intentional :-)


