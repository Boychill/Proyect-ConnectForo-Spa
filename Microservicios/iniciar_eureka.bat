@echo off
echo Iniciando microservicios ConnectForo...

REM === EUREKA SERVER ===
start cmd /k "cd C:\Users\Administrator\Documents\GitHub\Mi-Proyecto-ConnectForo-SPA\Proyect-ConnectForo-Spa\Microservicios\EurekaServer && mvn clean install && mvn spring-boot:run"
