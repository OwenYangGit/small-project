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
