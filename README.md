## 쇼핑몰

1. #### Stack

   * Hateoas
* Jpa
   * Mysql
   * Springboot
   * H2 (testDB)
   * Lombok
   * SpringRestDocs
   * Vue

   

2. ####  DB

* Account (계정)

| 이름         | 속성             | 의미               |
| ------------ | ---------------- | ------------------ |
| id           | Long             | ID                 |
| birth        | LocalDateTime    | 생년월일           |
| email        | String           | 이메일             |
| password     | String           | 비밀번호           |
| address      | String           | 주소               |
| phone_number | String           | 전화번호           |
| dress_arr    | Set<Dress> (FK)  | 등록한 옷 목록     |
| role         | Set<AccountRole> | 권한               |
| dpage_arr    | Set<Dpage> (FK)  | 등록한 페이지 목록 |



* Dress (옷)

| 이름           | 속성             | 의미        |
| -------------- | ---------------- | ----------- |
| id             | Long             | id          |
| name           | String           | 이름        |
| color          | DressColor       | 색상        |
| article_number | String           | 품번        |
| category       | DressType (enum) | 옷종류      |
| sex            | Sex (enum)       | 성별        |
| price          | Integer          | 판매가격    |
| discount       | Integer          | 할인율      |
| explanation    | String           | 설명        |
| dimage         | DImage (FK)      | 이미지      |
| craeted        | LocalDateTime    | 생성 날짜   |
| dsize          | Set<Dsize>       | 사이즈 정보 |
| material       | String           | 소재        |
| origin         | String           | 원산지      |
| manufacture    | LocalDate        | 제조년월    |





* Dorder (주문)

| 이름       | 속성          | 의미        |
| ---------- | ------------- | ----------- |
| id         | Long          | id          |
| order_time | LocalDateTime | 주문날짜    |
| dress      | Dress (FK)    | 옷          |
| account    | Account (FK)  | 구매자 계정 |
| dsize      | DressSize     | 옷사이즈    |



* Dpage (옷페이지)

| 이름    | 속성         | 의미        |
| ------- | ------------ | ----------- |
| id      | Long         | id          |
| dress   | Dress (FK)   | 옷          |
| views   | Integer      | 조회수      |
| account | Account (FK) | 판매자 계정 |

// Dress에 Account가 포함되어 있어 고민해봐야함

* Dimage(옷이미지)

| 이름         | 속성    | 의미               |
| ------------ | ------- | ------------------ |
| id           | Long    | id                 |
| image_files  | String  | Dress 파일들       |
| image_repIdx | Integer | 대표 이미지 인덱스 |



