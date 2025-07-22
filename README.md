<div align="center">
    <img width="150" height="150" alt="halocare_H" src="https://github.com/user-attachments/assets/dcc3ee29-1640-4ff3-8b86-3460af0ce453" />
    
<h3> Halocare 🧹</h3>

[<img src="https://img.shields.io/badge/release-v1.0.0-yellow?style=flat&logo=google-chrome&logoColor=white" />]()  
[<img src="https://img.shields.io/badge/프로젝트 기간-2025.05.26~2025.07.18-green?style=flat&logo=&logoColor=white" />]()
</div>

## 🔗 배포 링크
- 고객 : https://halocare.site
- 매니저 : https://www.halocare.site/managers/auth/login
- 관리자 : https://www.halocare.site/admin/auth/login

### 🙋 고객 계정  
- **ID**: `010-1111-1111`  
- **PW**: `Password123!`

### 🧹 청소매니저 계정  
- **ID**: `010-3333-0001`  
- **PW**: `Password123!`

### 🛠️ 관리자 계정  
- **ID**: `010-2222-2222`  
- **PW**: `Password123!`

---

## 📝 프로젝트 소개
### **Halo는 청소가 필요한 사람과, 서비스 제공 가능한 매니저를 연결하는 매칭 플랫폼입니다.**

> 누구나 서비스 예약을 진행할 수 있으며, 매니저는 자신의 일정과 조건에 맞춰 업무를 수행할 수 있습니다.  
**고객**은 원하는 날짜와 시간에 맞춰 청소 서비스를 신청할 수 있고,  
**매니저**는 등록된 근무 가능 시간과 지역을 기반으로 매칭 요청을 받을 수 있습니다.  
**관리자**는 각 역할별 사용자 계정 관리와, 문의사항 관리 등을 종합적으로 관리하며, 서비스 품질 유지와 운영의 효율성을 함께 고려합니다. 
>  

---
    
## 🎬 예약 프로세스 
| 고객 화면 #1 - 서비스 옵션/위치 입력 | 고객 화면 #2 - 매니저 선택 및 결제 |
|:--------------------------------------:|:-------------------------------:|
| <img src="https://github.com/user-attachments/assets/e459afe5-11de-4ea4-98e4-b2d7f09fae12" width="450"/> | <img src="https://github.com/user-attachments/assets/15c368d5-0d3d-47ed-8deb-76da8f6e5ff5" width="450"/> |
| 매니저 화면 #1 - 예약 수락 및 체크인, 체크아웃 | 
| <img src="https://github.com/user-attachments/assets/bcc14b12-afd6-4a61-8f3f-3188e4a9d3f8" width="450"/> | 
| 고객 화면 #1 - 리뷰 등록 | 매니저 화면 #2 - 리뷰 작성 |
| <img src="https://github.com/user-attachments/assets/d6c9a10b-a8ef-4f1d-84b4-b1a5590c19d4" width="400"/> |<img src="https://github.com/user-attachments/assets/3e62bfc2-0878-446c-8968-8ba2df8d6272" width="450"/> |

---

### **주요 특징**
- **청소 예약 절차의 간소화**: 수요자는 3단계로 서비스 예약 가능
- **날짜, 시간, 지역 기반 매칭 시스템**: 고객이 원하는 날짜, 시간, 지역을 기반으로 업무 가능한 매니저 추천
- **포인트 기반 결제 프로세스**: 예약 구성 → 매니저 매칭 및 선택 → 포인트 차감 결제 → 매니저 승인순으로 진행
- **예약 흐름의 자동화**: 예약 → 매칭 → 체크인/체크아웃 → 리뷰 → 정산까지 일관된 프로세스 구성
- **역할 기반 UI 구성**: 수요자, 매니저, 관리자 역할별 구분되는 화면 및 기능 제공

### 📍 세부 기능
#### 👤 고객 기능 
- 회원가입 및 소셜 로그인 (Google)
- 서비스 예약 및 예약 가능한 매니저 선택
- 서비스 완료 후 리뷰 작성
- 예약 내역 조회 및 취소
- 리뷰 내역 조회 및 수정
- 1:1 문의 작성 및 답변 확인

#### 🧹 매니저 기능
- 본인 일정 및 예약 현황 확인
- 고객의 서비스 요청 수락 또는 거절
- 서비스 체크인/체크아웃 및 고객 리뷰 작성
- 작성된 고객 리뷰 확인
- 정산 내역 및 정산 통계 조회
- 1:1 문의 작성 및 답변 확인

#### 🛠️ 관리자 기능
- 전체 예약 현황 및 통계 확인
- 문의/신고 게시글 관리 및 답변
- 예약건 정산 처리 및 이력 확인
- 고객 및 매니저 계정 관리 (조회 / 수정 / 정지)
  
---

## 📦 Infra 구성

1. **CI/CD**
    - Github Actions, AWS ECR을 활용한 파이프라인
2. **로깅 및 모니터링**
    - CloudWatch, S3를 통한 모니터링 및 로그 수집

---

## 🔒 보안 및 인증
- JWT 기반 인증 및 역할별 접근 제한
- Spring Security 적용

---

## ⚙ 기술 스택

### Frontend
<div>
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">
<img src="https://img.shields.io/badge/vercel-000000?style=for-the-badge&logo=vercel&logoColor=white">
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">
</div>

### Backend
<div>
<img src="https://img.shields.io/badge/Java 17-007ACC?style=for-the-badge&logoColor=black">
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=black">
<img src="https://img.shields.io/badge/Spring Boot_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=black">
<img src="https://img.shields.io/badge/MySql-4479A1?style=for-the-badge&logo=mysql&logoColor=black">
<img src="https://img.shields.io/badge/JSON_Web_Tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">

</div>

### DevOps & Infra
<div>
<img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS RDS-445CDF?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS S3-4C922B?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS ECR-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS Lambda-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS CloudWatch-EF4044?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/AWS SNS-EF4044?style=for-the-badge&logo=amazon-ec2&logoColor=black">
<img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=black">
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/Cloudflare-F38020?style=for-the-badge&logo=cloudflare&logoColor=white">
</div>

### Tools
<div>
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Github_Copilot-000000?style=for-the-badge&logo=githubcopilot&logoColor=white">
<img src="https://img.shields.io/badge/Claude-D97757?style=for-the-badge&logo=claude&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white">
</div>

---

## 🛠️ 아키텍처

![아키텍처](https://github.com/user-attachments/assets/09e4ab04-60a7-4057-9168-0a28092bfc07)

---

## 🚀 CI / CD 파이프라인

![파이프라인](https://github.com/user-attachments/assets/757aeb9f-bab1-430c-900e-be5bad8ab920)

---

## 📄 기술 문서

- 👉 **API 명세서** : [바로가기](https://api.halocare.site/swagger-ui)
- 👉 **기능 명세서** : [바로가기](https://www.notion.so/1fca3f519ab88001af8ed29685217236?source=copy_link)
- 👉 **ERD** :
  ![ERD](https://github.com/user-attachments/assets/d0209867-e0f5-4531-b611-ac0c78ea2222)

---

## 👥 프로젝트 팀원

| 이름  | 역할           | 담당 업무 요약                                                            |
|-----|--------------|---------------------------------------------------------------------|
| 정수정 | 백엔드 개발자 (팀장) | Spring security/JWT기반 로그인 및 회원가입, 예약 및 정산, 문의사항 리팩토링, 포인트 결제 |
| 이종은 | 백엔드 개발자      | 인프라 구성, CI/CD 환경 구성, Google 소셜 로그인, S3 첨부파일 업로드, 매니저 예약 관리, 관리자 백오피스 |
| 류예원 | 백엔드 개발자      | Spring security, JWT기반 로그인, 회원가입, 매니저 기능 및 페이지 구현 |
| 이혜린 | 백엔드 개발자      | 깃허브 관리, 관리자 기능 및 페이지 구현                    |

---
