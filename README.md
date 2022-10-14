# 학습 테스트로 배우는 Spring 1기

## 1단계 - 웹 요청 / 응답 처리

### 1. 웹 요청 / 응답 처리로 입출력 추가
- 예약 생성   
  - 예약 목록 조회
  - 예약 삭제
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 2. API 설계 

#### 예약 생성

요청
```
POST /reservations HTTP/1.1
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name"
}
```

응답
```
HTTP/1.1 201 Created
Location: /reservations/1
```

#### 예약 조회

요청
```
GET /reservations?date=2022-08-11 HTTP/1.1
```

응답
```
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

요청 
```
DELETE /reservations?date=2022-08-11&time=13:00 HTTP/1.1
```

응답
```
HTTP/1.1 204 
```
