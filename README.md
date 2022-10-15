# 기능 요구사항

- [ ] 웹 요청 / 응답 처리로 입출력 추가
- [ ] 예약 생성
  - [ ] 날짜와 시간을 입력받는다.
    - [ ] 날짜를 yyyy-MM-dd 형식으로 입력받는다.
    - [ ] 시간은 hh:mm 형식으로 입력받는다. (초는 자동으로 0초로 입력된다)
    - [ ] [예외] 날짜 및 시간이 형식에 맞지 않으면 등록되지 않는다.
    - [ ] [예외] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
  - [ ] 예약자 이름을 입력받는다.
    - [ ] [예외] 이름은 공란일 수 없다.
  - [ ] 성공 시 "예약이 등록되었습니다" 메시지 처리
- [ ] 예약 목록 조회
  - [ ] 예약 조회할 날짜를 입력받는다.
    - [ ] 날짜는 yyyy-MM-dd 형식이다.
    - [ ] [예외] 날짜 형식이 알맞지 않으면 목록을 응답하지 않는다.
  - [ ] 해당 일자에 예약된 목록이 조회된다.
    - [ ] 예약 목록은 시간 순서의 List이다. 
- [ ] 예약 삭제
  - [ ] 예약 날짜와 시간을 따로 입력받는다.
    - [ ] 날짜는 yyyy-MM-dd 형식이다.
    - [ ] 시간은 hh:mm 형식이다.
    - [ ] [예외] 날짜 및 시간이 형식에 맞지 않으면 취소되지 않는다.
  - [ ] 입력받은 날짜와 시간이 동일한 예약을 찾아 취소시킨다.
- [ ] 예외 처리
  - [ ] 예외 발생시 400 응답을 내려주고, 메시지를 추가한다.
- [ ] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
