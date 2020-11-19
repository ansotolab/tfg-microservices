#!/bin/sh
url="http://localhost:8762/api/customers"
until curl -sf "$url"; do echo -n "."; sleep 1; done
