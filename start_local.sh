export $(cat .env | xargs)
export DATABASE_URL=mongodb://localhost:27017/martcart

#while true; do
#    watch --chgexit "ls -lR ./src | sha1sum" && mvn compile
#done > /dev/null 2>&1 &

#mvn clean # sometimes may be required, specially when renaming classes
mvn compile
mvn spring-boot:run
