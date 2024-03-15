<h2>Task</h2>


Please create a library application.
This application should provide a REST API that satisfies the following requirements.

a) returns all users who have actually borrowed at least one book
b) returns all non-terminated users who have not currently borrowed anything
c) returns all users who have borrowed a book on a given date
d) returns all books borrowed by a given user in a given date range
e) returns all available (not borrowed) books

as input you will get three csv files containing all users, books and who borrowed what and when

Please do not use more than 3 working hours for this task.
We are aware that it is impossible to complete the task within this time frame.


<h2>Solution notes</h2>
This application exposes REST APIs for library data. 

It uses in-memory h2 database which is filled on each application startup - just a simple solution which is enough for this task.

Please access swagger by following link: http://localhost:8080/swagger-ui/index.html#/

I aimed to achieve all the functional requirements within the given timeframe.
This is why I didn't finish with tests and currently there are just simple test cases for books are implemented. 

TODOs:
- improve test coverage
- improve error handling
- add more details to swagger docs
- add endpoints for CRUD operations with data