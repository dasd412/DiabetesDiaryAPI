# __Diabetes Diary API__
#### _Private portfolio by <u>dasd412</u>_

---

+ 기능 설명

1. 당뇨병 환자의 혈당(공복 혈당, 아침 혈당, 점심 혈당, 저녁 혈당)을 달력형 게시판에 기록할 수 있다.
1. 달력형 게시판에 기록된 혈당 수치를 막대형 그래프로 보여줄 수 있다. (월별, 일별 선택 가능)
1. 구글 로그인 등 OAuth를 활용하여 가입 및 로그인할 수 있다.
1. 식단 태그를 활용하여 중복되는 식사 내용을 태그로 입력할 수 있다. (추가 예정)
1. 영어, 한국어 다국어 처리를 할 수 있다.(추가 예정)

+ Key Summary
 1. Junit을 활용하여 Repository, Controller 유닛 테스트를 진행 하였다.
 1. API 문서화를 Swagger로 하였다.

+ Folder Structure 소개

```
+-- src.main
        +-- java
             +--com.dasd412
                +--configure
                +--controller
                +--domain
                +--configure
                +--security
                +--service
                +--utils
                Applictaion

        +-- resources
             +--i18n
             +--static
             +--templates
                application.properties
                logback.xml
+-- src.test
    +--controller
       +--charts
          ChartsRestControllerTest
       +--diabetesDiary
          DiabetesDiaryRestControllerTest

    +--domain.diary
       DiabetesDiaryRepositoryTest
```
+ Backend Architecture

  + Routes/EndPoints 소개
  + Controller, Service, Repository, Store Procedure 관계도
  + DB 스키마

+ Frontend Architecture

+ 설치 방법

+ 실행 방법 

+ 향후 추가할 기능들
  + ~~로그인~~ (페이스북, 깃 연동 등은 나중에 할 예정)
  + 식단 태그 기능(테스트 실패. 혈당 일지 작성 완료 후 다시 시도)
  + 다국어 처리
  + 에러 처리
  
 + 사용 API
   + Swagger
   + BootStrap
   + sweet alert 
   + JS Chart
   
 + 사용 툴
   + Adobe XD 
   + 제플린
   + ERD Cloud
   + FontAwesome
   + ColorLib
   + Post Man
---
## __설계 계획__

1. 환경 설정
2. DB 스키마 작성
3. 백엔드 쪽 만든 후, H2와 POSTMan 활용하여 테스트
4. 프론트엔드 라이브러리 선택 후 활용
5. 배포

---
~~혈당 일지 작성~~

~~달력 게시판~~

~~메뉴 구현~~

~~로그인 구현~~

~~그래프 구현~~
### _Iteration -  6_ ###

식단 태그 기능
