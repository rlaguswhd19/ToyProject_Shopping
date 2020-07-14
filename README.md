## 쇼핑몰

1. #### Stack

   * Hateoas

   * Jpa
   * Mysql
   * Springboot
   * H2 (testDB)
   * Lombok
   * SpringRestDocs

   

2. ####  DB

* Account (계정)

| 이름         | 속성             | 의미     |
| ------------ | ---------------- | -------- |
| id           | Long             | ID       |
| age          | Integer          | 나이     |
| email        | String           | 이메일   |
| password     | String           | 비밀번호 |
| address      | String           | 주소     |
| phone_number | String           | 전화번호 |
| dress_arr    | Set<Dress> (FK)  | 찜목록   |
| role         | Set<AccountRole> | 권한     |



* Dress (옷)

| 이름           | 속성             | 의미        |
| -------------- | ---------------- | ----------- |
| id             | Long             | id          |
| brand          | String           | 브랜드      |
| article_number | String           | 품번        |
| dress_type     | DressType (enum) | 옷종류      |
| sex            | Sex (enum)       | 성별        |
| price          | Integer          | 판매가격    |
| discount       | Integer          | 할인율      |
| explanation    | String           | 설명        |
| image_path     | String           | 이미지 경로 |
| craeted_date   | LocalDateTime    | 생성 날짜   |



* Dorder (주문)

| 이름       | 속성          | 의미        |
| ---------- | ------------- | ----------- |
| id         | Long          | id          |
| order_time | LocalDateTime | 주문날짜    |
| count      | Integer       | 갯수        |
| dress      | Dress (FK)    | 옷          |
| account    | Account (FK)  | 구매자 계정 |
| color      | String        | 색상        |
| dsize      | DressSize     | 옷사이즈    |
| name       | String        | 이름        |



* Dpage (옷페이지)

| 이름      | 속성                       | 의미                |
| --------- | -------------------------- | ------------------- |
| id        | Long                       | id                  |
| dress     | Dress (FK)                 | 옷                  |
| views     | Integer                    | 조회수              |
| page_info | HashMap(String, ColorInfo) | 색상당 사이즈, 수량 |
| Account   | Account (FK)               | 판매자 계정         |



