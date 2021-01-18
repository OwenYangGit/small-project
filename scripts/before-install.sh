#!/bin/bash
echo "ensure docker network [codedeploy-network] exists"
docker network create codedeploy-network || true