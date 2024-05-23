FROM node:latest as builder

RUN mkdir -p /app

WORKDIR /app

COPY . .

RUN npm install
RUN npm run build --prod

CMD ["npm", "start"]

FROM nginx:alpine

COPY /dist/crudtuto-Front /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf