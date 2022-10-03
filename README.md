# Tinypim

This is a very simple API to provide basic CRUD operations for a Product Imformation Management micro-system. It's just a coding demo and it is not production-ready

# Setup

This application is written using Spring Tools Suite on Eclipse (STS), so you can open the project using STS and execute it as any other Spring Boot application

The project uses SpringBoot with Maven, so you can also build a fat jar by executing `maven clean install` from within the [api directory](api) and execute it via the usual `java -jar`. After the application starts:

- The applicaton starts an in-memory H2 database instance, and the console is available at http://localhost:8080/h2/ . Login with:
  - url=jdbc:h2:mem:testdb
  - user=sa
  - password is empty
- The API REST documentation is available at http://localhost:8080/swagger-ui/index.html

# Other Info

- Code: https://github.com/nagarciah/tinypim
- Project Board: https://github.com/users/nagarciah/projects/1
- Pull requests: https://github.com/nagarciah/tinypim/pulls?q=is%3Apr
