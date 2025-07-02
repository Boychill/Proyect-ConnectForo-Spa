REM Este script inicia los microservicios de la aplicación en diferentes ventanas de consola.

start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\APIGateway && mvn clean install && mvn spring-boot:run"
REM === AUTH SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\AuthService && mvn clean install && mvn spring-boot:run"
REM === CATEGORY SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\CategoryService && mvn clean install && mvn spring-boot:run"

REM === FORUM SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\ForumService && mvn clean install && mvn spring-boot:run"

REM === PUBLICATION SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\PublicationService && mvn clean install && mvn spring-boot:run"

REM === COMMENT SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\CommentService && mvn clean install && mvn spring-boot:run"

REM === REPUTATION SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\ReputationService && mvn clean install && mvn spring-boot:run"

REM === MEDIA SERVICE ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\MediaService && mvn clean install && mvn spring-boot:run"
