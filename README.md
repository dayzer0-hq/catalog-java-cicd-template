# catalog-java-cicd-template

A small **Java + Spring Boot (Maven)** payments service for DayZer0 CI/CD briefs. Your
project repository is provisioned from this template: the service, its fast unit tests, its
deliberately slow integration suite and the Maven wrapper already exist and pass. **The
pipeline around them does not — building it is your project.** There is deliberately no
`.github/workflows` directory here.

## What's here

- `src/main/java/com/dayzero/ledger` — the Spring Boot app: `GET /healthz`,
  `POST /payments`, business logic in `Ledger.java`.
- `ReportsHelper.java` — carries a **seeded handful of Checkstyle violations** against the
  `checkstyle.xml` at the repository root. Checkstyle is **not wired into the build yet**;
  a brief on this repository has you wire it in and drive the violations to zero. Deliberate,
  not an accident.
- `LedgerTest.java` — the fast unit suite (no Spring context), run by `./mvnw test`.
- `LedgerFlowIT.java` — the integration suite, run by `./mvnw verify`. It is **deliberately
  slow** (a simulated settling wait per test); getting that wait out of everyone's way is
  what one of the briefs is about.
- `mvnw` / `mvnw.cmd` — the Maven wrapper; no local Maven install needed.

## Requirements

- A JDK, 17 or newer. The wrapper downloads Maven itself.

## Run it

```bash
./mvnw spring-boot:run
```

Then, in another terminal:

```bash
curl http://localhost:8080/healthz
# {"status":"ok"}
```

## Test it

```bash
./mvnw test      # fast unit tests — seconds
./mvnw verify    # unit tests + the deliberately slow integration suite — minutes
```

## Build the jar

```bash
./mvnw package
java -jar target/catalog-java-cicd-template-1.0.0.jar
```

## What you add

Your tickets have you build the GitHub Actions pipeline: workflows, gates, artefacts or
parallel jobs as your brief asks. Beyond what a ticket names (for example, fixing the seeded
Checkstyle violations), do not add features to the service. One ticket, one branch
(`issue-N-<slug>`), one pull request with `Closes #N`.
