#!/bin/bash
while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8080/records)" != "200" ]]; do sleep 5; done