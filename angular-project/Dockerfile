
FROM node:18.17.1-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# Etapa finală pentru servirea aplicației Angular
FROM nginx:stable
COPY --from=build /app/dist/angular-project/ /usr/share/nginx/html
EXPOSE 80