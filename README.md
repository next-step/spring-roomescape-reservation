# 학습 테스트로 배우는 Spring 1기

## 3단계 - 테마 확장 기능

### 1. 확장 기능
- 테마를 관리
- 테마별 스케줄 관리
- 조건 추가
  - 이미 지난 날짜의 스케줄에 예약 할 수 없다.
  - 진행 중인 스케줄이 존재하는 테마는 제거 할 수 없다. (이미 지난 스케줄만 있는 경우 제거 가능하다.)
  - 테마는 제거되어도 DB 상에선 확인 할 수 있어야 한다. (소프트 삭제로 제거를 처리한다.)
  - 이미 예약이 진행된 스케줄은 제거할 수 없다.

### 2. 리팩터링과 예외 처리
- 추가된 테마와 스케줄에 따라 기존에 구현된 예약을 리팩터링
- 예약과 관련된 예외 상황을 처리
    - 예약 등록을 하려는 스케줄에 이미 예약이 있는 경우 예약을 등록을 실패한다.
    - 예약이 없는 경우에도 빈 응답 값을 응답한다.
    - 예약이 존재하지 않는 경우 예약 삭제를 실패한다.

### 3. API 설계

#### 테마 생성
요청
```
POST /themeRepository HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "name": "테마이름",
    "desc": "테마설명",
    "price": 22000
}
```

응답
```
HTTP/1.1 201 Created
Location: /themeRepository/1
```

#### 테마 목록 조회

요청
```
GET /themeRepository HTTP/1.1
```

응답
```
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

요청
```
DELETE /themeRepository/1 HTTP/1.1
```

응답
```
HTTP/1.1 204
```

#### 스케줄 생성

요청
```
POST /scheduleRepository HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "themeId": 1,
    "date": "2022-08-11",
    "time": "13:00"
}
```

응답
```
HTTP/1.1 201 Created
Location: /scheduleRepository/1
```

#### 스케줄 목록 조회

요청

```
GET /scheduleRepository?themeId=1&date=2022-08-11 HTTP/1.1
```

응답
```
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

#### 스케줄 삭제

요청
```
DELETE /scheduleRepository/1 HTTP/1.1
```

응답
```
HTTP/1.1 204
```

## 2단계 - 데이터베이스 적용

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
POST /reservationRepository HTTP/1.1
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
Location: /reservationRepository/1
```

#### 예약 조회

요청
```
GET /reservationRepository?date=2022-08-11 HTTP/1.1
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
DELETE /reservationRepository?date=2022-08-11&time=13:00 HTTP/1.1
```

응답
```
HTTP/1.1 204 
```
