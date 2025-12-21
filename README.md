# Agentic AI Travel Planner

## CWD (Coordinator, Worker, Delegator) 모델 구조

### 개요
Chapter_06.ipynb의 CrewAI 기반 CWD 패턴을 Java + LangChain4j로 구현한 여행 계획 시스템입니다.

### 아키텍처

#### 파일 구조
```
src/main/java/com/example/
├── coordinator/
│   ├── TravelCoordinator.java           # Coordinator 인터페이스
│   └── CoordinatorService.java          # Coordinator 서비스 구현
├── worker/
│   ├── FlightWorker.java               # 항공편 Worker 인터페이스
│   ├── FlightWorkerService.java        # 항공편 Worker 구현
│   ├── HotelWorker.java                # 호텔 Worker 인터페이스
│   ├── HotelWorkerService.java         # 호텔 Worker 구현
│   ├── ActivityWorker.java             # 액티비티 Worker 인터페이스
│   └── TransportWorker.java            # 교통편 Worker 인터페이스
├── delegator/
│   ├── TravelDelegator.java            # Delegator 인터페이스
│   └── DelegatorService.java           # Delegator 서비스 구현
├── model/
│   ├── TravelRequest.java              # 여행 요청 모델
│   ├── TravelPlan.java                 # 여행 계획 모델
│   └── WorkerResult.java               # Worker 결과 모델
├── tool/
│   ├── FlightSearchTool.java           # 항공편 검색 도구
│   ├── HotelSearchTool.java            # 호텔 검색 도구
│   ├── ActivitySearchTool.java         # 액티비티 검색 도구
│   └── TransportSearchTool.java        # 교통편 검색 도구
└── service/
    └── CWDTravelService.java           # 메인 CWD 오케스트레이션 서비스
```

### 컴포넌트 역할

#### **Coordinator (조정자)**
- 사용자 요청을 분석하여 전체 여행 계획 수립
- 예산, 일정, 선호도를 고려한 고수준 계획 생성
- LangChain4j의 `AiServices`로 구현

#### **Workers (작업자들)**
- 각각 특정 도메인 전문가 역할
- `FlightWorker`: 항공편 검색 및 추천
- `HotelWorker`: 숙박 검색 및 추천  
- `ActivityWorker`: 액티비티 검색 및 추천
- `TransportWorker`: 교통편 검색 및 추천
- 각각 독립적인 `AiServices` 인스턴스

#### **Delegator (위임자)**
- Coordinator의 계획을 받아 적절한 Worker들에게 작업 분배
- Worker들의 결과를 통합하여 최종 여행 일정 생성
- 작업 순서 및 의존성 관리

### 처리 흐름
```
사용자 요청 → Coordinator (계획 수립) → Delegator (작업 분배) 
→ Workers (병렬 실행) → Delegator (결과 통합) → 최종 여행 일정
```

### 주요 변경사항

1. **역할 분리**: 기존 단일 `LangChain4jService`를 CWD 패턴으로 분리
2. **전문화**: 각 Worker가 특정 도메인에 특화된 프롬프트와 도구 사용
3. **병렬 처리**: Worker들이 `CompletableFuture`로 독립적으로 작업하여 성능 향상
4. **유연성**: 새로운 Worker 추가나 기존 Worker 수정이 용이
5. **재사용성**: 각 컴포넌트를 독립적으로 테스트 및 재사용 가능

### LangChain4j 활용

- **기존**: 하나의 `AiServices` 인스턴스에 모든 도구 등록
- **변경 후**: 
  - Coordinator용 `AiServices` (계획 수립 전용)
  - 각 Worker별 `AiServices` (도메인별 도구 포함)
  - Delegator용 `AiServices` (통합 및 조율 전용)

## 기술 스택

- Java 17
- Spring Boot 3.2.0
- LangChain4j 0.25.0
- OpenAI GPT-4o