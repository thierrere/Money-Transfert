# Money-Transfert
#### Get project source


```shell
$ git clone https://github.com/thierrere/Money-Transfert.git
```

#### Build project using maven

```shell
$ cd Money-Transfert/
$ mvn install
$ mvn test
```

#### Run account api example (datastore in memory (Hashmap))

```shell
$ java -jar target\Money-Transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```
List all the accounts
```shell
$ curl -X GET localhost:7000/accounts
```
Get an account
```shell
$ curl -X GET localhost:7000/accounts/account01@mail.com
```
Create an account
```shell
$ curl -X PUT localhost:7000/accounts/account01@mail.com/100.0
```
Make a deposit to an account
```shell
$ curl -X POST localhost:7000/accounts/account01@mail.com/100.0
```
Transfert from an account to another account
```shell
$ curl -X POST localhost:7000/accounts/account01@mail.com/100.0/account02@mail.com
```

#### Stuff used to make this:

 * [Kotlin](https://kotlinlang.org/) : Statically typed programming language
 * [Javalin](https://javalin.io/) : Simple REST APIs for Java and Kotlin
 * [KotlinTest](https://github.com/kotlintest/kotlintest) : Flexible and comprehensive testing tool for Kotlin
 * [RestAssured](http://rest-assured.io/) : Testing and validating REST services  with simplicity
 * [Hamcrest](http://hamcrest.org/JavaHamcrest/) : Matchers that can be combined to create flexible expressions of intent
 * [Jersey](https://jersey.github.io/) :  Use for test in the main function
