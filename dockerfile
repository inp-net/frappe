#
# Spring boot Api
#
FROM maven:latest AS api_builder
WORKDIR /app
COPY apps/api .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine AS api
WORKDIR /app
COPY --from=api_builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#
# Svelte frontend
#

FROM node:24-slim AS app_base

ENV PNPM_HOME="/pnpm"
ENV PATH="$PNPM_HOME:$PATH"
RUN corepack enable

WORKDIR /app
COPY pnpm-workspace.yaml /app
COPY pnpm-lock.yaml /app
COPY package.json /app
COPY schema.json /app


FROM app_base AS app_builder


COPY apps/app /app/apps/app
RUN pnpm install --filter @frappe/app --frozen-lockfile


RUN pnpm --filter @frappe/app run generate
RUN pnpm --filter @frappe/app run build

# Restricts deps to production only
RUN pnpm prune --prod

FROM app_base AS app
COPY --from=app_builder /app/apps/app/node_modules /app/node_modules
COPY --from=app_builder /app/apps/app/build /app/build

EXPOSE 3000
CMD [ "node", "/app/build" ]

