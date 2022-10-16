## 기능구현목록
### 1단계 - 웹 요청 / 응답 처리
- [x] 웹 요청 / 응답 처리로 입출력 추가
  - [x] 예약 생성
    - [x] 날짜와 시간이 똑같은 예약이 이미 있는 경우, 예외 처리
  - [x] 예약 목록 조회
  - [x] 예약 삭제

### 2단계 - 데이터베이스 적용
- [x] 메모리 대신 데이터베이스 적용

### 3단계 - 테마 확장 기능
- [x] 테마 관리 기능 추가
  - [x] 테마 생성
  - [x] 테마 목록 조회
  - [x] 테마 삭제
- [x] 테마별 스케줄 관리 기능 추가
  - [x] 스케줄 생성
  - [x] 스케줄 목록 조회
  - [x] 스케줄 삭제
- [ ] 추가된 테마와 스케줄에 따라 기존 구현된 예약관리기능 리팩터링
- [ ] 예외처리
  - [ ] 예약생성 시, 예약하려는 날짜와 시간에 이미 예약이 있다면 예약 불가
  - [ ] 예약조회 시, 예약이 없는 경우에도 빈 응답값을 응답
  - [ ] 예약삭제 시, 예약이 존재하지 않는 경우, 삭제 불가
  - [ ] 테마/스케줄 삭제 시, 예약이 존재하는 경우, 삭제 불가
