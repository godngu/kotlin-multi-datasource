
# Kotlin 기반 멀티데이터소스 + Read Replica 설정

## 스택 

- kotlin 1.6.10
- spring boot 2.5.7
- spring data jpa


## 설명

- `Primary`와 `Secondary` DB로 나뉩니다.
- DB에 따라 `Entity`, `Repository` 패키지가 따로 설정 되므로 패키지를 분리 하는 것이 좋습니다.
- 각 DB는 Writer와 Reader가 별도로 설정됩니다.
- `@Transactional(readOnly=true)`에 의해 Read Replica로 라우팅 됩니다.
  - 해당 설정에서는 CUD 쿼리가 발생하지 않습니다.
- Secondary 트랜잭션은 Custom Annotation을 사용합니다.
  - `@TransactionalSecondary`