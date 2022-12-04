![Untitled](https://user-images.githubusercontent.com/72602912/182013095-adf13e66-eccf-494a-a32e-2702973ff0c6.png)

## 👩‍🏫PROJECT 소개


**중고거래**를 더 재밌게! 편리하게! 깔끔하게!
**Bidding 거래 방식**을 통해 색다른 중고거래 서비스를 제공합니다.
현재 중고거래 플랫폼들의 문제점을 파악하고 개선하여 판매자와 구매자의 니즈에 맞춘 거래 플랫폼입니다.

- **Bidit**은 어떤 **문제점을 해결**하고자 했을까요?
    - 같은 물건이라도 사용기간, 상태, 거래 방법이 전부 다름
    → 정해진 입력 형식으로 **직관적인 물건 정보과 거래 방법 제공**
    - 허위 매물, 네고, 광고, 선입금 등 **거래 시 불화를 일으키는 커뮤니케이션**
    → **선 검증 시스템**으로 인증된 제품만 사용자에게 제공
    → 입찰 된 사람만 채팅을 가능하도록 해 불필요한 커뮤니케이션 차단
    - 중고거래 시세를 몰라 **일일이 찾아야하는 가격
    →최저가, 평균가, 최고가**를 표기해 판매자 & 구매자에게 시세 제공

🗓️ **작업기간** : 2022.07 ~ 진행중

👨‍💻 **투입인원** : 안드로이드(1명), 서버(1명), iOS(1명), 기획자(1명), 디자인(1명) - 안드로이드를 담당

📒 **주요업무** 

- Custom View를 활용한 UI 작업
- Clean Architecture +  MVVM  패턴 적용
- Apollo를 활용한 GraphQL 데이터 UI 바인딩
- Navigation, Data Binding, hilt 등 Jetpack 라이브러리 활용

🌱 **사용툴**

`Android Studio` `Figma` `Discord` `Notion` `Github` `SourceTree` `Postman` 

## 🙆🏻‍♂️Client(Android)


👨‍💻 **투입인원** : 1명

👨‍💻 **사용 언어** : Kotlin

📒 **주요기술**

- HTTP Client: Apollo
- Asynscronuous Task Manager: Coroutine
- Dependency Injection: Hilt
- Image Processor: S3/Glide

## 🙆🏻‍♂️Client(iOS)


👨‍💻 **투입인원** : 1명

👨‍💻 **사용 언어 :** Swift

📒 **주요기술**

- HTTP Client: Apollo
- Asynscronuous Task Manager: RxSwift, RxCocoa
- Image Processor: S3/KingFishrer

## 🙆🏻‍♀️Server


👨‍💻 **투입인원** : 1명

👨‍💻 **구현** : JavaScript, Node.js

📒 **주요기술**

- Architecture: MSA + EDA + Serverless
- Interface: AppSync(GraphQL), API Gateway(Websocket)
- Compute & DB: Lambda, Aurora RDS(V3 MySQL), DynamoDB
- CI/DC: AWS CodePipeline
