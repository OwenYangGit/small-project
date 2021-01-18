#!/bin/bash
echo "start to deploy my app service"
# login ecr , aws cli is old version in v1 (if v2 , plz use new command refer to offical documents)
$(aws ecr get-login --no-include-email --region ap-northeast-1)
if [ "$(docker ps -aq -f name=internal-tool)" ]; then
    # stop service and remove it
    docker stop internal-tool
    docker rm internal-tool
    # run your container
    docker rmi 145556684742.dkr.ecr.ap-northeast-1.amazonaws.com/devops/internal-tool:latest
    docker run -d --network codedeploy-network -p 8080:8080 --name internal-tool 145556684742.dkr.ecr.ap-northeast-1.amazonaws.com/devops/internal-tool:latest
    exit 0
fi
docker run -d --network codedeploy-network -p 8080:8080 --name internal-tool 145556684742.dkr.ecr.ap-northeast-1.amazonaws.com/devops/internal-tool:latest
docker system prune -f
exit 0