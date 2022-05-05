# Paw Index Advisor
Paw Index Advisor is a automatic and intelligent cost-based index recommendation tool for MySQL and PostgrSQL. Its target users include database application developer and database administors.
Paw Index Advisor is integrated into most popular IDEs, such as IntelliJ IDEA , PhpStorm, PyCharm , WebStorm and Eclipse, Spring Tool Suite.  For application developers, they can optimize the application performance by One Click.
More information at https://indexadvisor.pawsql.com/.
# Features
- Combinnation of cost based and heuristic algorithm based
- Database objects/queries can be retrieved online or offline；
- Take mybatis xml files as input and permutate all possible queries；
- Support MySQL and PostgreSQL and derivatives, such as Opengauss；
- Support index recomendation covering matching, screening, sort-avoiding,covering；
- Deduplicate with existing indice
- Supprt query rewrite, support sattc, vew folding, and ordinal rewrite；
- Client-based, non-invasion to production system；
- Support what-if analysis，make sure indice recommended be used in later query execution；
- Integrate with most popular IDEs，such as eclipse, IDEA,  optimize by One Click.

# Comparison
![图片](https://user-images.githubusercontent.com/103090727/166870000-76cb30a0-c408-4559-baf5-84d7b985dde2.png)

# IntelliJ Plugin
Plugin home page https://plugins.jetbrains.com/plugin/19003-paw-index-advisor/
## Steps to start:
Search for "Paw Index Advisor" in martketplace and click install or 
Configure the query type and database information on the configuration page
Right click any folder which contains the sql statments/mybatis xml files, and click "Run Index Advisor"
Wait for a minute and the indice are recommended to accelerate your database queries.
## Screenshots
Configuration
![图片](https://user-images.githubusercontent.com/103090727/166870162-6104ad45-542b-4118-81a2-d7e379b0b4ff.png)
Run Index Advisor
![图片](https://user-images.githubusercontent.com/103090727/166870366-91bb9c9c-973d-4f18-a34e-ae27ac8b2d36.png)
Result
![图片](https://user-images.githubusercontent.com/103090727/166870206-54db9355-f1a8-4a7b-9143-6ee10e229c9e.png)

