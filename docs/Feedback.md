> 피드백 반영 체크리스트

# step 1-1 ([PR](https://github.com/next-step/spring-roomescape-reservation/pull/69))

- [x] 오버 엔지니어링 경계하기
  - 요구사항의 크기에 맞게 적절한 설계 필요.
    - [x] appender, reader 등의 구현 레이어를 제거하고 단순한 구조로 리팩토링
- [x] 프로덕션에서 리플렉션을 써야할까?
  - [x] 리플렉션 대신 빌더로 id를 주입하도록 변경
    

