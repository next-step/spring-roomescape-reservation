# spring-roomesacpe-reservation
## 1단계 - 웹 요청 / 응답 처리
### 기능 요구 사항
- [x] 웹 요청/응답 처리로 입출력 추가
  - [x] 예약 생성
  - [x] 예약 목록 조회
  - [x] 예약 삭제
- [x] 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 프로그래밍 요구사항
- 기존 콘솔 애플리케이션은 그대로 잘 동작해야 한다. 
- 콘솔 애플리케이션과 웹 애플리케이션의 비즈니스 로직 중복을 최소화 한다.
- Scanner와 System.out을 이용한 입출력 대신 Spring MVC에서 제공하는 웹 요청과 응답을 통해 입출력을 구현한다.

### API 설계
#### 예약 생성 
```http request
POST /reservations HTTP/1.1
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name"
}

```
```http request
HTTP/1.1 201 Created
Location: /reservations/1
```

#### 예약 조회 
```http request
GET /reservations?date=2022-08-11 HTTP/1.1
```
```http request
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "date": "2022-08-11",
        "time": "13:00",
        "name": "name"
    }
]
```
#### 예약 삭제
```http request
DELETE /reservations?date=2022-08-11&time=13:00 HTTP/1.1
```
```http request
HTTP/1.1 204
```

## 2단계 - 데이터베이스 적용
### 기능 요구사항
- [x] 메모리 대신 데이터베이스 적용

## 3단계 - 테마 확장 기능
### 기능 요구사항
- [ ] 테마를 관리 기능 추가 
- [ ] 테마별 스케줄 관리 기능 추가 
- [ ] 추가된 테마와 스케줄에 따라 기존에 구현된 예약을 리팩터링 
- [ ] 예약과 관련된 예외 상황을 처리 
  - [ ] 예약 등록을 하려는 날짜와 시간에 이미 등록되어있으면 예약을 등록을 실패한다. 
  - [ ] 예약이 없는 경우에도 빈 응답 값을 응답한다. 
  - [ ] 예약이 존재하지 않는 경우 예약 삭제를 실패한다.
### 프로그래밍 요구사항
- [ ] 예약 테마 등 도메인 설계를 자류롭게 한다. 
- [ ] 도메인 설계로 인해 생성되는 객체와 데이터베이스의 테이블 스키마는 일치하지 않아도 된다.
- [ ] 계층을 고려한 리팩터링을 진행한다.
### API 설계
#### 테마 생성 
```http request
POST /themes HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "name": "테마이름",
    "desc": "테마설명",
    "price": 22000
}
```
```http request
HTTP/1.1 201 Created
Location: /themes/1
```

#### 테마 목록 조회
```http request
GET /themes HTTP/1.1
```
```http request
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "테마이름",
        "desc": "테마설명",
        "price": 22000
    }
]
```

#### 테마 삭제
```http request
DELETE /themes/1 HTTP/1.1
```
```http request
HTTP/1.1 204
```

#### 스케쥴 생성
```http request
POST /schedules HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "themeId": 1,
    "date": "2022-08-11",
    "time": "13:00"
}
```
```http request
HTTP/1.1 201 Created
Location: /schedules/1
```

#### 스케쥴 목록 조회
```http request
GET /schedules?themeId=1&date=2022-08-11 HTTP/1.1
```
```http request
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "theme": {
            "id": 1,
            "name": "테마이름",
            "desc": "테마설명",
            "price": 22000
        },
        "date": "2022-08-11",
        "time": "13:00:00"
    }
]
```

#### 스케쥴 삭제
```http request
DELETE /schedules/1 HTTP/1.1
```
```http request
HTTP/1.1 204
```

### 요구사항 설명
#### 테마 관리 
- 테마 관리 시 필요한 생성, 조회, 목록조회, 수정, 삭제 기능을 구현한다.
- 테마는 이름과 설명, 가격 정보를 가지고 있다.

#### 테마별 스케쥴 관리
- 테마는 플레이 시간이 다르기 때문에 스케줄을 다르게 설정할 수 있어야 한다
- 스케줄은 테마, 날짜, 시간의 정보를 가지고 있다.

#### 기존 기능 수정
- 테마에 대한 스펙이 변경되었기 때문에 기존 기능인 예약 관리와 플레이 이력 관리 기능의 로직 수정이 필요할 수 있다.
- 예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제가 불가능하다.

### 계층화 리팩토링
- 하나의 객체 혹은 계층이 너무 많은 역할과 책임을 가지지 않게 리팩터링 한다.
- 웹 요청과 응답을 처리하는 계층과 데이터를베이스 접근을 처리하는 계층을 분리한다.