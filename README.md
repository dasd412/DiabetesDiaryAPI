# __Diabetes Diary API__
#### _Private portfolio by <u>dasd412</u>_

---

+ 기능 설명

+ Key Summary

+ Folder Structure 소개

+ Backend Architecture

  + Routes/EndPoints 소개
  + Controller, Service, Repository, Store Procedure 관계도
  + DB 스키마
    + 혈당 일지 스키마
     + ![이미지](https://github.com/dasd412/DiabetesDiaryAPI/blob/tempModel/YRTntsoetNdPhGQDd.png?raw=true)

    

+ Frontend Architecture

+ 설치 방법

+ 실행 방법 

+ 향후 추가할 기능들
  + 혈당 일지 작성(진행 중)
  + 월별 혈당 
  + 그래프 추이
  + 식단 태그 기능(테스트 실패. 혈당 일지 작성 완료 후 다시 시도)
  + 로그인 
  
 + 사용 API
   
---
## __설계 계획__ 

1. ~~환경 설정~~
2. ~~DB 스키마 작성~~
3. 백엔드 쪽 만든 후, H2와 POSTMan 활용하여 테스트
4. 프론트엔드 라이브러리 선택 후 활용
5. 스프링 시큐리티 적용하여 로그인 구현
6. DB MySql로 변경
7. 배포 

---
### 이슈 ###

1.@ManyToMany 지우고 중간에 연결 테이블용 엔티티 만든것에 대한 조회 테스트 필요

2.혈당일지 리포지토리에서 findAll()실행 시 같은 select 문이 수행됨. N+1 문제인 것 같음. 수정 필요.

---
### _03_ ###