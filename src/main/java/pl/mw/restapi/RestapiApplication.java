package pl.mw.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
API REST
1.  System logowanie JWT z H2: https://www.bezkoder.com/spring-boot-security-login-jwt/
    Width MySql: https://www.bezkoder.com/spring-boot-login-example-mysql/

    SpringData i problem n+1: https://www.youtube.com/watch?v=WIMCuNYfdE4
    Stronicowanie i sortowanie: https://www.youtube.com/watch?v=W-mkcziTuNQ
    Encje (tworzenie i usuwanie): https://www.youtube.com/watch?v=Xz1ZbIaJiWg
    JWT Token Old: https://www.youtube.com/watch?v=and2DR_N6tE&t=973s
    Liquibase: https://www.youtube.com/watch?v=guF3zORY1jI&t=1020s
    Cache - EHCashe: https://www.youtube.com/watch?v=lWv3uBLO2LU

    Wszystko o REST API: https://www.youtube.com/watch?v=C1bC134GvQ8
    Łączenie się z zewnętrznym API: https://www.youtube.com/watch?v=DPFYyjyeuVA
    Mapowanie obiektów w Spring DTO: https://www.youtube.com/watch?v=yyZtxniWWGM

    Intellij Tips&Tricks: https://www.youtube.com/watch?v=Rply-mi_J7o

    https://www.bezkoder.com/spring-boot-security-login-jwt/
    https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/
    https://www.githubcode.com/spring-boot-angular-example-github/
    https://www.bezkoder.com/integrate-angular-spring-boot/

    REPOSITORY BEZKODER:
    https://github.com/bezkoder?page=2&tab=repositories

    2. Połaczenie do API w OAuth na przykladzie API Spotify
    https://www.youtube.com/watch?v=qNvT99L5Uc4


 */
@SpringBootApplication
@EnableAutoConfiguration
public class RestapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestapiApplication.class, args);
    }

}
