# Patient Log — Monorepo

This repository contains two related projects for a simple patient logging application:

- `patient-backend/` — Java Spring Boot REST API (Maven)
- `patient-frontend/` — React single-page application (npm)

This README consolidates essential information for both projects: structure, how to build/run locally, configuration hints, useful endpoints, troubleshooting, and contribution notes.

## Repository layout

Top-level folders:

- `patient-backend/` — Spring Boot backend
  - `pom.xml`, `mvnw`, `mvnw.cmd`
  - `src/main/java/org/patientlog/api/` — main Java packages (config, controller, model, repository, service, security, exception)
  - `src/main/resources/application.properties` — runtime configuration
  - `src/test/` — tests

- `patient-frontend/` — React frontend
  - `package.json`
  - `src/` — React source (components, API wrapper, styles)
  - `public/` — static index.html and manifest

## Goals / Quick summary

- Backend: provide REST endpoints to manage Patients and authentication.
- Frontend: React app that consumes backend API endpoints and provides a UI for login and patient CRUD.

## Backend (patient-backend)

### Tech stack

- Java (8/11/17 — check `pom.xml` for exact compatibility)
- Spring Boot (web, data, security, etc.)
- Maven wrapper included (`mvnw`, `mvnw.cmd`)

### Useful files & locations

- Main app: `src/main/java/org/patientlog/api/PatientBackendApplication.java`
- Controllers: `src/main/java/org/patientlog/api/controller/` (`AuthController.java`, `PatientController.java`)
- Security: `src/main/java/org/patientlog/api/config/SecurityConfig.java` and `security/CustomUserDetailsService.java`
- Data initializer: `src/main/java/org/patientlog/api/config/DataInitializer.java` (if present, seeds sample data)
- Repositories: `src/main/java/org/patientlog/api/repository/`
- Models/DTOs: `src/main/java/org/patientlog/api/model/` and `dto/`

### How to build

Open a terminal in `patient-backend/` and run (Windows PowerShell example):

```powershell
# Linux/macOS: ./mvnw clean package
# Windows (PowerShell or CMD):
cd patient-backend
mvnw.cmd clean package
```

Or run directly with the wrapper without packaging:

```powershell
cd patient-backend
mvnw.cmd spring-boot:run
```

If you prefer a specific installed Maven, use `mvn clean package` from the same folder.

### Configuration

- `src/main/resources/application.properties` contains runtime configuration (server port, datasource, security settings, etc.).
- If the app uses an external database, set the datasource URL/credentials in `application.properties` or via environment variables.

Default server port is typically 8080 (check `application.properties` or `PatientBackendApplication` logs on startup).

### Notable endpoints

Based on the controllers present:

- Authentication endpoints: implemented in `AuthController.java`. Likely paths: `/auth/login`, `/auth/register` (inspect the controller for exact routes).
- Patient endpoints: implemented in `PatientController.java`. Common routes: `/api/patients`, `/api/patients/{id}` (GET, POST, PUT, DELETE).

Example curl (adjust exact paths and payloads according to your controller's API):

```powershell
# List patients (replace path if different):
curl http://localhost:8080/api/patients

# Create a patient (JSON body):
curl -X POST http://localhost:8080/api/patients -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe"}'
```

### Tests

Run backend tests with Maven:

```powershell
cd patient-backend
mvnw.cmd test
```

## Frontend (patient-frontend)

### Tech stack

- React (Create React App or similar)
- Axios for HTTP requests (see `src/api/axiosConfig.js`)
- Standard npm scripts (`start`, `build`, `test`) are available in `package.json`.

### How to run (dev)

Open a terminal in `patient-frontend/` and run:

```powershell
cd patient-frontend
npm install
npm start
```

This typically starts a dev server at `http://localhost:3000` (or another port if configured). The app will call the backend API — ensure the backend is running.

### Build for production

```powershell
cd patient-frontend
npm run build
```

### Configuration

- Axios configuration is in `src/api/axiosConfig.js`. Adjust the `baseURL` to point to your backend (default: `http://localhost:8080`).
- If CORS errors occur, either enable CORS in the backend config or proxy API requests (see `package.json` proxy setting) during development.

## Running the apps together (recommended local flow)

1. Start the backend first so API and auth are available:

```powershell
cd patient-backend
mvnw.cmd spring-boot:run
```

2. Then start the frontend dev server:

```powershell
cd patient-frontend
npm install
npm start
```

3. Open the frontend URL (typically `http://localhost:3000`).

Notes:
- If your frontend fails to reach the API, confirm the backend port and set the axios `baseURL` accordingly.
- If you prefer a single command to run both, you can add a small script using `concurrently` or use Docker Compose — this repo does not currently include those files.

## Troubleshooting

- Port conflicts: ensure 8080 (backend) and 3000 (frontend) are free or change ports in config files.
- CORS: enable or configure Cross-Origin Resource Sharing in the backend (`WebConfig` or Security config).
- Authentication: if requests are rejected, verify headers/token handling in the frontend (see `AuthController` and `axiosConfig`).
- Build failures (backend): check Java version compatibility in `pom.xml` and your installed JDK.


