FROM node:16-alpine3.15 AS builder

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build


FROM node:16-alpine3.15 AS production

WORKDIR /app
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/package-lock.json ./package-lock.json
COPY --from=builder /app/dist ./dist
RUN npm install

EXPOSE 3001

CMD ["npm", "run", "start:prod"]