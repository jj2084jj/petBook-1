# PetBook Back-End Server Side

## Subjects

- 이 모듈 내부에선 프론트 페이지 연동을 위한 기능들을 구현해야 한다.
- 데이터베이스 연동은 한 서버, 한 데이터베이스 연동을 이뤄야 한다.
  - 향후 DB 엔진을 사용한 동기화, 트랜잭션 격리에 대한 이슈도 고려해볼 계획이다.
- Docker, k8s 등을 사용하여 여러 서버 모듈을 작동하더라도, 데이터의 일관성은 유지되어야 한다.
- 연동 중인 데이터베이스에 이상이 생기면, 다른 서버에게 요청을 전달하여 서비스를 계속 이룰 수 있게 구성해야 한다.

## Missions

서버에서 사용되는 영역은 크게 4 가지로 나뉘어 관리하겠다.

1. 사용자 정보
2. 게시글 정보 (펫북)
3. 동물 정보
4. 쪽지 정보

## Modeling

데이터 모델링은 아래 사이트에서 관리 중이며, 일부 개선될 수도 있음을 알립니다.

* [ER Diagram Logical Modeling](https://miro.com/app/board/uXjVO3-jqbI=/)
* ER Diagram Physical Modeling: TODO

## Authors

* 강인성 (tails5555)
* 한승연 (mjk6828)
