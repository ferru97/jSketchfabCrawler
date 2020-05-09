# jSketchfabCrawler

jSketchfabCrawler is a java for the automatic crawling of model's information from sketchfab.com using its public API.  The information are related to: 
- Model name
- Model url
- Number of views, like, comments, vertices and faces
- Comments
- Tags
- Publication date

### Requirements
- Java 13
- SQL Database. Create the database using the **db_schema.sql** file

### How To
Run the jar file specifying the DB credentials with the following format:
`java -jar jSketchfabCrawler.jar db_address/db_name/db_user/db_password/db_port`


#### Note
This first version only download the data in descending order of views of the models 
