## Define envfile "myenv"
```
vim myenv
```
### put maria root's password
```
MYSQL_ROOT_PASSWORD=mydemo
```
## Init demo db
```
docker exec -i dev-maria sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD"' < ./project/demo.sql
```

## How to check execute success or failed
```
docker exec -it dev-maria bash
mysql -uroot -p
use todo;
show tables;
show columns from tasks;
```
### Reference link
[Dockerhub - mariadb](https://hub.docker.com/_/mariadb)

### Lab Link
[參考 mariadb demo](https://hackernoon.com/how-to-connect-mariadb-docker-containers-with-java-spring-and-jdbc-ex243urb)