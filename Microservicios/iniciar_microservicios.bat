REM Este script inicia los microservicios de la aplicación en diferentes ventanas de consola.

start cmd /k "cd C:\Users\Administrator\Documents\GitHub\ConnectForo-SPA\Microservicios\ReputationService && mvn clean install && mvn spring-boot:run" 
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\ConnectForo-SPA\Microservicios\MediaService && mvn clean install && mvn spring-boot:run"
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\ConnectForo-SPA\Microservicios\CategoryService && mvn clean install && mvn spring-boot:run"
    