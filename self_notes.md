Notes:

- To allow spring-data to create indexes, set `spring.data.mongodb.auto-index-creation` to true. Without it indexes won't be created even if `@Indexed` is used. [Reference](https://stackoverflow.com/questions/72259387/why-mongo-ttl-index-is-not-been-creating-via-spring-data) <br><br>

- `DBRef` and `DocumentReference` both create reference between documents. 
   But they differ at the data that actually gets stored in the DB in place of the reference.
   `DBRef` stores references in a special format as `DBRef("<collection-name>","<document-id>")` in the database. 
   `DocumentReference` stores just the `ObjectId` of the referenced document. 
   Thus, the `DocumentReference` functionality for Java is similar to that of `mongoose` ORM's functionality for TypeScript.
   [Reference](https://docs.spring.io/spring-data/mongodb/reference/mongodb/mapping/document-references.html) <br><br>


