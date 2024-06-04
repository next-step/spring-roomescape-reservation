# 🚀 예약 기능

## API

### 예약 조회
- Request
```
GET /reservations HTTP/1.1
```
- Response

```json
HTTP/1.1 200
Content-Type: application/json

[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-01-01",
    "time": "10:00"
  },
  {
    "id": 2,
    "name": "브라운",
    "date": "2023-01-02",
    "time": "11:00"
  }
]
```

### 예약 추가
- Request
```json
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

- Response
```json
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 예약 취소
- Request
```
DELETE /reservations/1 HTTP/1.1
```
- Response
```
HTTP/1.1 200
```
