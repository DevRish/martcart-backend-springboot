FROM maven
WORKDIR /app
COPY pom.xml ./
COPY start.sh ./
CMD ["./start.sh"]
