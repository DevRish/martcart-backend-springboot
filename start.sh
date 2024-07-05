# Auto re-compile the project when any files within src directory get changed
while true; do
    watch --chgexit "ls -lR ./src | sha1sum" && mvn compile
done > /dev/null 2>&1 &

# Run the application
# The application already uses "spring-boot-devtools" which will restart it when any classes (not source code!) change
# With the bash commands above, the project will get recompiled on source code change and re-generate the classes
# and seeing that change in classes spring-boot-devtools will restart the project
mvn spring-boot:run

# Summary: Now on saving a file after code change, application will restart
# (equivalent to nodemon/ts-node-dev for js/ts)
