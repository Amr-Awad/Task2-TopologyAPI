# Task3-TopologyAPI API documentation

This repository contains the documentation for [Task3-TopologyAPI] API.

#### Contents

- [Overview](#1-overview)
- [Resources](#2-resources)
  - [readJSON](#2.1.-readJSON)
  - [writeJSON](#2.2.-writeJSON)
  - [deleteTopology](#2.3-deleteTopology)
  - [queryTopologies](#2.4-queryTopologies)
  - [queryDevices](#2.5-queryDevices)
  - [queryDevices](#2.6-queryDevicesWithNetlistNode)

## 1. Overview

TopologyAPI’s API is a Restful-based API. All requests are made to endpoints beginning:
`http://localhost:8080`


## 2. Resources

The API is RESTful and arranged around resources. All reques.

### 2.1. readJSON

#### Getting the Topology from the body JSON and add it to memory.
Returns true if the add is successful or false if it is not.

```
POST http://localhost:8080/readJSON/{{top}}
```

Example JSON body:

```
{
    "id": "top2",
    "components": [
        {
            "type": "resistor",
            "id": "res2"
            }
        },
        {
            "type": "nmos",
            "id": "m2"
            }
        }
    ]
}
```

The response is true or false.

Example response:

```
true
```

Where a Topology object is:

| Field      | Type              | Description                                     |
| -----------|-------------------|-------------------------------------------------|
| id         | string            | A unique identifier for the topology.           |
| Components | ToologyComponent  | The user’s name on Medium.                      |



### 2.2. WriteJSON

#### Getting the Topology from memory and Publish it as JSON

Returns a JSON body. with all the information about the topology



```
GET http://localhost:8080/writeJSON/{{TopologyID}}
```
Where ToplogyID is the user id of the Topology.


The response is a JSON body with all the information about the topology.

Example response:
```
{
    "id": "top1",
    "components": [
        {
            "type": "resistor",
            "id": "res1",
            "resistance": {
                "min": 10.0,
                "max": 1000.0,
                "default": 100.0
            },
            "netList": {
                "t1": "vdd ",
                "t2": "n1"
            }
        },
        {
            "type": "nmos",
            "id": "m1",
            "m": {
                "min": 1.0,
                "max": 2.0,
                "default": 1.5
            },
            "netList": {
                "drain": "n1",
                "gate": "vin",
                "source": "vss"
            }
        }
    ]
}
```

Where a Input Parameters are:

| Field       | Type   | Description                                                 |
| ------------|--------|-------------------------------------------------------------|
| id          | string | A unique identifier for the Topology to get it with.        |
                     


### 2.3. deleteTopology

#### Takes a Topology ID and deletes it from memory
returns true if deleted successfully or false otherwise.

```
GET http://localhost:8080/deleteTopology/{{TopologyID}}
```
Where ToplogyID is the user id of the Topology.


Where a Input Parameters are:

| Field       | Type   | Description                                                 |
| ------------|--------|-------------------------------------------------------------|
| id          | string | A unique identifier for the Topology to get it with.        |

The response is true or false.

Example Response:
```
true
```

### 2.4. queryTopologies

#### Gets all the Topologies in memory.
returns JSON body of the topologies.

```
GET http://localhost:8080/queryTopologies
```


The response is JSON body with all the information about all the topologies in memory.

Example Response:
```
[
    {
        "id": "top1",
        "components": [
            {
                "type": "resistor",
                "id": "res1",
                "resistance": {
                    "min": 10.0,
                    "max": 1000.0,
                    "default": 100.0
                },
                "netList": {
                    "t1": "vdd ",
                    "t2": "n1"
                }
            },
            {
                "type": "nmos",
                "id": "m1",
                "m": {
                    "min": 1.0,
                    "max": 2.0,
                    "default": 1.5
                },
                "netList": {
                    "drain": "n1",
                    "gate": "vin",
                    "source": "vss"
                }
            }
        ]
    },
    {
        "id": "top2",
        "components": []
    }
]
```

### 2.5. queryDevices

#### Gets all the Components in specific Topology from memory.
returns JSON body of the Components.

```
GET http://localhost:8080/queryDevices/{{TopologyID}}
```
Where ToplogyID is the user id of the Topology.

Input Parameters are:

| Field       | Type   | Description                                                 |
| ------------|--------|-------------------------------------------------------------|
| id          | string | A unique identifier for the Topology to get it with.        |

The response is JSON body with all the information about all the components in the topology in memory.

Example Response:
```
[
    {
        "type": "resistor",
        "id": "res1",
        "resistance": {
            "min": 10.0,
            "max": 1000.0,
            "default": 100.0
        },
        "netList": {
            "t1": "vdd ",
            "t2": "n1"
        }
    },
    {
        "type": "nmos",
        "id": "m1",
        "m": {
            "min": 1.0,
            "max": 2.0,
            "default": 1.5
        },
        "netList": {
            "drain": "n1",
            "gate": "vin",
            "source": "vss"
        }
    }
]
```

### 2.5. queryDevicesWithNetlistNode

#### Gets the Components in specific Topology from memory which contains a specific node.
returns JSON body of the Components.

```
GET http://localhost:8080/queryDevicesWithNetlistNode/{{TopologyID}}/{{NetListNodeName}}
```
Where ToplogyID is the id of the Topology and NetListNodeName is the name of the node .

Input Parameters are:

| Field       | Type   | Description                                                 |
| ------------|--------|-------------------------------------------------------------|
| id          | string | A unique identifier for the Topology to get it with.        |
| netlistNode | String | Name of the Node that will be searched for                  | 

The response is JSON body with all the information about all the components in the topology from memory which contains a specific node.

Example Response:
```
[
    {
        "type": "resistor",
        "id": "res1",
        "resistance": {
            "min": 10.0,
            "max": 1000.0,
            "default": 100.0
        },
        "netList": {
            "t1": "vdd ",
            "t2": "n1"
        }
    },
    {
        "type": "nmos",
        "id": "m1",
        "m": {
            "min": 1.0,
            "max": 2.0,
            "default": 1.5
        },
        "netList": {
            "drain": "n1",
            "gate": "vin",
            "source": "vss"
        }
    }
]
```
