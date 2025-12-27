# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the nimbus project (where pom.xml and src are)
COPY nimbus/pom.xml .
COPY nimbus/src ./src
COPY nimbus/.mvn ./.mvn
COPY nimbus/mvnw .

# Build the application
RUN mvn clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Step 3: Verify Your Settings

Make sure these Render settings are **exactly** like this:

- **Root Directory**: `NIMBUS_AIRLINE_BACKEND`
- **Docker Build Context Directory**: `NIMBUS_AIRLINE_BACKEND/.` (or just `.`)
- **Dockerfile Path**: `Dockerfile`

### Step 4: Add Build Filters (Optional but Recommended)

Scroll down to **Build Filters** and add this to **Included Paths**:
```
NIMBUS_AIRLINE_BACKEND/**