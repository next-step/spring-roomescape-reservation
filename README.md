# 요구사항

## 기능 요구사항

- [x] 웹 요청 / 응답 처리로 입출력 추가
  - [x] 예약 생성
  - [x] 예약 목록 조회
  - [x] 예약 삭제
- [x] 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

## 프로그래밍 요구사항

- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 비즈니스 로직 중복을 최소화 한다.

## API 설계

### 예약 생성

``` http request
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

### 예약 조회

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

### 예약 삭제

```http request
DELETE /reservations?date=2022-08-11&time=13:00 HTTP/1.1
```

```http request
HTTP/1.1 204
 ```

