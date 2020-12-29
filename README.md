## Define envfile "myenv" , following with template
```
MYSQL_ROOT_PASSWORD=dontputeasypasswordlikeP@ssw0rd
MYSQL_DATABASE=TEST
MYSQL_USER=myuser # change it !
MYSQL_PASSWORD=mypass # change it !
TZ=Asia/Taipei
```

### Spring Guide Practice , `practice` folder
- Accessing Relational Data using JDBC with Spring
- Building a RESTful Web Service
- Accessing Data with JPA
- Accessing Data with MySQL

### internal-tools
用來記錄版本的小工具 API 主要提供
- 公司欄位
- 服務名稱
- 版本號
- 前版本號(會自動抓取上一版塞入 , 若是第一次更新則為 NULL)
- 當前版本佈署時間
- 前次版本佈署時間(會自動抓取上次當前版本佈署時間塞入 , 若是第一次更新回 Null)

### internal-tools API 使用

#### `GET /records/` 取得所有 record , 返回 List []
##### 返回一個 json array

#### `GET /record/{id}` 透過 id 取得單一 record 物件
```
curl http://url/record/3
```
##### 返回該物件的 json

#### `GET /record/id` 取得單一 record 的 id , 透過 company 和 service 參數查找
```
curl -d "company=mycom&service=demo-service" http://url/record/id
``` 
##### 返回該 id 或 null

#### `POST /record` 存入新 record 
```
curl -H "Content-Type: application/json" -X POST -d {\"company\":\"mycom\",\"service\":\"my-service-name\",\"version\":\"dev-1.0.0\"} http://url/record
```
##### 返回 success 或 error

#### `PUT /record/{id}` 更新已存在的 record 物件
```
curl -X PUT -d version="dev-new-1.0.0" http://url/record/3
```
##### 返回 success 或 null 或 error