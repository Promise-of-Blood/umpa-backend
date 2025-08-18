## 프로젝트 아키텍처 개선 제안

이 문서는 현재 아키텍처를 분석하고, 유지보수성, 테스트 용이성, 확장성을 높이기 위한 실용적인 계층형 아키텍처를 제안합니다.

### 1. 현행 아키텍처 분석

#### 1.1. 현행 구조 (As-Is)

현재 구조는 스프링 프레임워크에서 널리 사용되는 **전통적인 계층형 아키텍처**입니다.

- **구성**: `controller`, `service`, `repository` 패키지를 중심으로 각 컴포넌트의 타입에 따라 분리되어 있습니다.
- **데이터 흐름**: `Controller` -> `Service` -> `Repository` 의 선형적인 단방향 흐름을 가집니다.
- **특징**: 구조가 직관적이고 단순하여 초기 개발 속도가 빠르다는 장점이 있습니다.

#### 1.2. 문제점

전통적인 계층형 구조는 프로젝트가 커짐에 따라 몇 가지 한계점을 드러냅니다.

- **`domain` 패키지의 과도한 책임**: 현재 `domain` 패키지는 순수한 도메인 모델(`entity`, `vo`) 외에, 애플리케이션 서비스(`service`),
  외부 연동 구현체(`strategy`)까지 포함하고 있습니다. 이는 도메인 계층의 순수성을 해치고 다른 계층에 대한 의존성을 갖게 합니다.
- **계층 간 경계 불분명**: `service`가 `domain`에 속해있어, 비즈니스 로직을 다루는 **애플리케이션 계층**과 핵심 규칙을 담는 **도메인 계층**의 경계가
  모호합니다. 이로 인해 비즈니스 로직(`Service`)이 데이터 접근 기술(`Repository`, 즉 JPA)에 강하게 결합되는 경향이 있습니다.
- **모호한 `core` 패키지**: `config`, `exception`, `security` 등 다양한 역할의 클래스들이 `core` 라는 범용적인 패키지에 묶여있어 각
  컴포넌트의 역할이 명확히 드러나지 않습니다.

### 2. 개선 제안

#### 2.1. 설계 철학: 실용주의적 접근

이 제안은 헥사고날/클린 아키텍처로의 **완전한 전환이 아닙니다.** 해당 아키텍처의 모든 규칙을 엄격하게 적용하는 것은 현 단계에서 오버엔지니어링이 될 수 있습니다.

대신, **가장 적은 비용으로 가장 큰 효과(유지보수성, 유연성)를 얻을 수 있는** 핵심 원칙들만 실용적으로 차용한 **'개선된 계층형 아키텍처'**를 목표로 합니다.

##### 차용한 핵심 개념

- **의존성 규칙 (The Dependency Rule)**: 모든 의존성의 방향은 오직 내부(`domain`)를 향합니다. 이를 통해 핵심 비즈니스 로직을 외부의 변화로부터
  보호합니다.
- **명확한 계층 분리**: `api`, `application`, `domain`, `infrastructure`의 4개 계층으로 역할을 명확히 나눕니다.
- **포트와 어댑터 (Ports & Adapters) - 경량화**: `domain`에 Repository 인터페이스(Port)를 두고, `infrastructure`에 JPA
  구현체(Adapter)를 두어 비즈니스 로직과 데이터 기술을 분리합니다.

##### 사용하지 않은/단순화한 개념

- **엄격한 유스케이스(Use Case) 객체**: 기능 단위의 클래스 대신, 관련 기능들을 묶은 `UserService` 형태를 유지하여 복잡도를 낮춥니다.
- **계층별 데이터 모델**: `Entity`와 `DTO` 정도로 데이터 모델을 단순화하여 계층 간 과도한 매핑 비용을 줄입니다.
- **CQRS (Command Query Responsibility Segregation)**: 아키텍처의 복잡성을 고려하여 도입하지 않습니다.

#### 2.2. 새로운 패키지 구조

아래는 개선된 계층형 아키텍처의 패키지 구조입니다. 각 계층의 역할을 명확히 하고, 도메인 모델과 애플리케이션 로직을 분리합니다.

```
src/main/java/promiseofblood/umpabackend/
├── api/                              # Presentation Layer: 외부 노출 API
│   ├── controller/                   # 1. API 엔드포인트 컨트롤러
│   ├── advice/                       # 2. 글로벌 예외 처리 (@RestControllerAdvice)
│   └── dto/                          # 3. API 전용 데이터 객체
│       ├── request/                  #    - 요청(Request) DTO
│       └── response/                 #    - 응답(Response) DTO
├── application/                      # Application Layer: 응용 로직
│   ├── service/                      # 1. 유스케이스 구현 서비스
│   ├── dto/                          # 2. 서비스-컨트롤러 간 데이터 전달용 DTO
│   └── exception/                    # 3. 애플리케이션 서비스 레벨의 예외
├── domain/                           # Domain Layer: 핵심 비즈니스 로직
│   ├── entity/                       # 1. 도메인 엔티티
│   ├── vo/                           # 2. 값 객체 (Value Objects)
│   ├── repository/                   # 3. Repository 인터페이스 (Port)
│   └── exception/                    # 4. 도메인 비즈니스 규칙 관련 예외
└── infrastructure/                   # Infrastructure Layer: 외부 시스템 연동
    ├── config/                       # 1. 각종 설정 (DB, Security 등)
    ├── persistence/                  # 2. 데이터 영속성 구현 (Adapter)
    │   └── repository/               #    - Repository 구현체
    ├── security/                     # 3. 인증/인가 구현 (JWT, Security Filter 등)
    ├── oauth/                        # 4. OAuth 연동 구현체
    └── storage/                      # 5. 파일 스토리지 등 외부 시스템 연동 구현
```

#### 2.3. 각 계층의 역할

- **`api` (Presentation Layer)**: 외부(클라이언트)와의 상호작용. (HTTP 요청/응답 처리)
- **`application` (Application Layer)**: 사용자의 유스케이스 구현. (서비스 로직, 트랜잭션 관리)
- **`domain` (Domain Layer)**: 순수한 비즈니스 규칙과 데이터의 집합. (다른 계층에 의존성 없음)
- **`infrastructure` (Infrastructure Layer)**: 외부 시스템과의 연동 구현. (DB, 외부 API, JWT 등)

#### 2.4. 도메인 예외와 애플리케이션 예외의 구분

예외 처리 구조를 명확히 하기 위해 도메인 예외와 애플리케이션 예외를 구분하고, 각각의 역할과 책임을 정의합니다.

##### 2.4.1. 기본 원칙

**도메인 예외 (Domain Exception)**

- **정의**: 비즈니스 규칙, 불변식(invariants), 도메인 로직의 제약 위반 시 발생하는 예외
- **특징**: 도메인 모델 자체에서 생성되며, 외부 인프라(DB, 네트워크 등)에 의존하지 않음
- **예시**:
    - `InvalidOrderStatusTransitionException` (주문 상태가 '배송 중'에서 '결제 대기'로 변경 불가)
    - `NegativeBalanceException` (계좌 잔액이 음수가 될 수 없음)

**애플리케이션 예외 (Application Exception)**

- **정의**: 특정 유스케이스 실행 실패나 기술적 문제로 인한 예외
- **특징**: 도메인 외부 요소(DB, 외부 API 등)와의 상호작용 또는 애플리케이션 흐름 제어와 관련됨
- **예시**:
    - `UserNotFoundException` (특정 ID의 사용자를 찾을 수 없음)
    - `PaymentProcessingFailedException` (결제 처리 중 외부 API 오류)

##### 2.4.2. 구현 전략

**예외 클래스 네이밍 패턴**

예외의 성격을 명확히 드러내는 일관된 네이밍 패턴을 사용합니다.

- 도메인 예외: `Invalid[도메인개체][속성]Exception`, `[도메인규칙]ViolationException`
- 애플리케이션 예외: `[도메인개체]NotFoundException`, `[작업]FailedException`

**애매모호한 상황에서의 결정 가이드라인**

예외 분류가 애매한 경우, 다음 질문들을 고려합니다.

1. 이 예외가 발생하는 이유가 비즈니스 규칙 때문인가? (yes: 도메인 예외)
2. 이 예외는 인프라(DB, 외부 API 등)와 무관하게 발생할 수 있는가? (yes: 도메인 예외)
3. 이 예외가 도메인 전문가와의 대화에서 자연스럽게 언급될 수 있는가? (yes: 도메인 예외)
4. 이 예외는 다른 기술 스택으로 전환해도 여전히 의미가 있는가? (yes: 도메인 예외)

##### 2.4.3. 예외 처리 흐름

1. **예외 발생**: 도메인 또는 애플리케이션 계층에서 적절한 예외 발생
2. **예외 변환 (선택적)**: 필요시 도메인 예외를 애플리케이션 예외로 감싸 더 풍부한 컨텍스트 제공
   ```java
   try {
       order.addItem(item, quantity);
   } catch (MinimumOrderAmountViolationException e) {
       throw new OrderProcessingFailedException("최소 주문 금액 미달로 주문 처리 실패", e);
   }
   ```
3. **예외 처리**: `api/advice`의 글로벌 예외 핸들러가 모든 예외를 적절한 HTTP 응답으로 변환

##### 2.4.4. 분류 예시

**도메인 예외로 분류될 예외들**:

- `Oauth2UserAlreadyExistsException`: 사용자 고유성 규칙 위반
- `InvalidPasswordFormatException`: 비밀번호 형식 규칙 위반

**애플리케이션 예외로 분류될 예외들**:

- `UserNotFoundException`: 리소스 조회 실패
- `InvalidJwtException`: 인증 메커니즘 관련 오류
- `PaymentServiceUnavailableException`: 외부 서비스 연동 실패
