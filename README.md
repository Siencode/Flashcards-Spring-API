# Flashcard REST API
----
**Run the Application**:
----
Clone project:
``` 
git clone https://github.com/Siencode/Flashcards-Spring-API
```
Run:
```
./mvnw spring-boot:run
```
----
**REST API implementation**:
----
[https://github.com/marmichno/flashcards](https://github.com/marmichno/flashcards)

----
**Tech**:
----
* Spring boot
* Spring MVC
* Spring Security
* Spring data JPA
* Hibernate
* H2 Database
* Junit 5
* Mockito


----
**Explore Rest APIs**:
----
* #### User
| Method | URI | Description | Request body format|
| ------ | ------ | ------ | ------ |
| GET | /api/userinfo | Get information about the logged user |
| POST | /api/register | Register a new user | JSON |
----
### With basic authorization:

* #### Flashcard
| Method | URI | Description | Request body format|
| ------ | ------ | ------ | ------ |
| GET | /api/flashcards | Get all user flashcards |
| GET | /api/flascards/{id} | Get flashcard information by ID |
| POST | /api/flashcard | Add a new flashcard | JSON |
| PUT | /api/flascard/{id} | Edit flashcard by ID |  JSON |
| DELETE | /api/flashcard/{id} | Delete flashard by ID |

*  #### Flachcard category
| Method | URI | Description | Request body format|
| ------ | ------ | ------ | ------ |
| GET | /api/categories | Get all user flashcard categories |
| POST | /api/category | Add a new flashcard category |
| PUT | /api/category/{id} | Edit flashcard category by ID |
| DELETE | /api/category/{id} | Delete flashard category by ID |

* #### Learning
| Method | URI | Description | Request body format|
| ------ | ------ | ------ | ------ |
| GET | /api/learn/new | Create new learning |
| GET | /api/learn/last | get last learning information |
| GET | /api/learn/flashcard/next | Get next flashcard |
| GET | /api/learn/flashcard/current | Get current flashcard  |


----
**Request body example**:
----
- POST /api/register:
```
{
    "username": "SampleUsername",
    "password": "SamplePassword"
}
```

- POST /api/flashcard:
```
{
   "firstSentence": "Sentence",
   "secondSentence": "Sentence2",
   "flashcardCategoryId": 1
}
```
- PUT /api/flashcard:
```
{
   "firstSentence": "Sentence",
   "secondSentence": "Sentence2",
   "flashcardCategoryId": 1
}
```
- POST /api/category/{id} :
```
"New category name"
```
- PUT /api/category/{id}
```
"Category name"
```
