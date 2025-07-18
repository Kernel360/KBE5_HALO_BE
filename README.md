<div align="center">
### 프로젝트명 - Halocare

[<img src="https://img.shields.io/badge/release-v1.0.0-yellow?style=flat&logo=google-chrome&logoColor=white" />]()  
[<img src="https://img.shields.io/badge/프로젝트 기간-2025.05.26~2025.07.18-green?style=flat&logo=&logoColor=white" />]()

</div>

## 🎬 시연 영상
![시연 영상](https://github.com/user-attachments/assets/시연영상.gif)

## 🔗 배포 링크
https://halocare.site

---

## 📝 프로젝트 소개
### **Halo는**

**청소가 필요한 사람과, 서비스 제공 가능한 매니저를 연결하는 매칭 플랫폼입니다.**

> 누구나 서비스 예약을 진행할 수 있으며, 매니저는 자신의 일정과 조건에 맞춰 업무를 수행할 수 있습니다.
**고객**은 원하는 날짜와 시간에 맞춰 청소 서비스를 신청할 수 있고,
**매니저**는 등록된 근무 가능 시간과 지역을 기반으로 매칭 요청을 받을 수 있습니다.
**관리자**는 각 역할별 사용자 계정 관리와, 문의사항 관리 등을 종합적으로 관리하며, 서비스 품질 유지와 운영의 효율성을 함께 고려합니다.
>

---

### **주요 특징**
- **청소 예약 절차의 간소화**: 수요자는 3단계로 서비스 예약 가능
- **실시간 매칭 시스템**: 매니저에게 예약 요청 알림 자동 발송 및 응답 처리
- **포인트 기반 결제 프로세스**: 예약 구성 → 매니저 매칭 및 선택 → 매니저 승인 → 포인트 차감 결제 순으로 진행
- **예약 흐름의 자동화**: 예약 → 매칭 → 체크인/체크아웃 → 리뷰 → 정산까지 일관된 프로세스 구성
- **역할 기반 UI 구성**: 수요자, 매니저, 관리자 역할별 구분되는 화면 및 기능 제공

### 📍 세부 기능
#### 👤 수요자 기능 (고객)
- 회원가입 및 소셜 로그인 (Google, Apple 등)
- 예약 가능한 가사 도우미(매니저) 목록 조회 및 서비스 예약
- 예약 내역 조회 및 취소
- 서비스 완료 후 리뷰 작성
- 알림(예약 확정, 일정 변경 등) 확인
- 1:1 문의 작성 및 답변 확인

#### 🧹 매니저 기능 (가사 도우미)
- 본인 일정 및 예약 현황 확인
- 고객의 서비스 요청 수락 또는 거절
- 서비스 수행 후 완료 처리
- 고객 리뷰 확인
- 정산 내역 및 근무 통계 조회
- 1:1 문의 작성 및 답변 확인

#### 🛠️ 관리자 기능
- 고객 및 매니저 계정 관리 (조회 / 수정 / 정지)
- 전체 예약 현황 및 통계 확인
- 서비스 카테고리 및 요금 관리
- 문의/신고 게시글 관리 및 답변
- 매니저 정산 처리 및 이력 확인

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
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=claude&logoColor=white">

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
  ![ERD](https://github.com/user-attachments/assets/40372f13-838d-4f3d-a2de-a59fa231d5cb)

---

## 👥 프로젝트 팀원

| 이름  | 역할           | 담당 업무 요약                                                            |
|-----|--------------|---------------------------------------------------------------------|
| 정수정 | 백엔드 개발자 (팀장) | Spring security, JWT기반 로그인 및 회원가입, 수요자 기능 및 페이지 구현, 예약 및 매칭 알고리즘 구현 |
| 이종은 | 백엔드 개발자      | 인프라 구성, CI/CD 환경 구성, 관리자 기능 및 페이지 구현, 예약 및 매칭 알고리즘 구현               |
| 류예원 | 백엔드 개발자      | Spring security, JWT기반 로그인, 회원가입, 매니저 기능 및 페이지 구현, 예약 및 매칭 알고리즘 구현  |
| 이혜린 | 백엔드 개발자      | 깃허브 관리, 관리자 기능 및 페이지 구현, 예약 및 매칭 알고리즘 구현                            |

---
