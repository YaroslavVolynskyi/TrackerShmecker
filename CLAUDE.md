# Project Overview
Modern Android app using Jetpack Compose and Material 3.
Stack: Kotlin, Compose, Hilt DI, Coroutines/Flow, Room, and Retrofit.

# Architecture Rules
- Follow Google's [Recommended App Architecture](https://android.com).
- Pattern: MVVM with Unidirectional Data Flow (UDF).
- Layers:
  - UI Layer: Jetpack Compose for views, ViewModels for state management.
  - Domain Layer: Optional UseCases for complex business logic.
  - Data Layer: Repositories as single source of truth; DataSources for API/DB.
- Dependency Injection: Strictly use Hilt. No manual instantiation of ViewModels or Repositories.

# Coding Standards
- Language: 100% Kotlin. No Java unless explicitly required for SDK compatibility.
- Async: Use Coroutines and Kotlin Flow. Avoid LiveData and RxJava.
- UI: Jetpack Compose only (no XML layouts). Follow [Compose Best Practices](https://android.com).
- Formatting: Follow Kotlin style guide (120-char line limit). Use `ktlint` for verification.

# Development Workflows
- Build: `./gradlew assembleDebug`
- Test: `./gradlew test` (Unit), `./gradlew connectedAndroidTest` (UI)
- Lint: `./gradlew lint` or `./gradlew detekt`

# Critical Constraints
- Never put business logic in Compose UI functions.
- Every Repository must have an interface for easy testing.
- Maintain strict modularization: core, data, domain, and feature-specific modules.
