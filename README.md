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

+ Frontend Architecture

+ 설치 방법

+ 실행 방법 

+ 향후 추가할 기능들
  + ~~혈당 일지 작성~~
  + 달력 게시판(월별 혈당 한눈에 볼 수 있음. 평균 값도 제시)
  + 그래프 추이(평가 시스템도 도입 예정. '참 잘했어요' 도장도 재밌을 듯)
  + 식단 태그 기능(테스트 실패. 혈당 일지 작성 완료 후 다시 시도)
  + 로그인
  + 메뉴 구현
  
 + 사용 API
   + Swagger
   + BootStrap
   
 + 사용 툴
   + Adobe XD 
   + 제플린
   + ERD Cloud
   
---
## __설계 계획__

1. 환경 설정
2. DB 스키마 작성
3. 백엔드 쪽 만든 후, H2와 POSTMan 활용하여 테스트
4. 프론트엔드 라이브러리 선택 후 활용
5. DB MySql로 변경
6. 배포 

---
### 화면 ui 설계 방법 ###
1.AdobeXD로 스케치

2.스케치한 것 제플린으로 export

3.제플린으로 css 코드 참고하며 직접 화면 작성 (부트스트랩도 첨가)

---
~~혈당 일지 작성~~

### _Iteration -  2_ ###

달력 게시판