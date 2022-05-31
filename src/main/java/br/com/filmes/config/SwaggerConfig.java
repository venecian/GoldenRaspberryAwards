package br.com.filmes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build().useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, getRespostasPadrao())
                .globalResponseMessage(RequestMethod.GET, getRespostasPadrao())
                .globalResponseMessage(RequestMethod.PUT, getRespostasPadrao())
                .globalResponseMessage(RequestMethod.DELETE, getRespostasPadrao())
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API Filme do Golden Raspberry Awards",
                "API RESTful para possibilitar a leitura da lista de indicados e vencedores\n" +
                        "da categoria Pior Filme do Golden Raspberry Awards",
                "API 1.0",
                "",
                new Contact("Luthiano Venecian", "https://www.linkedin.com/in/luthiano-venecian/", "venecian@gmail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }

    private List<ResponseMessage> getRespostasPadrao() {
        List<ResponseMessage> config = new ArrayList<>();

        config.add(new ResponseMessageBuilder()
                .code(400)
                .message("Erro nos dados informados!")
                .build());

        config.add(new ResponseMessageBuilder()
                .code(401)
                .message("Não autorizado! Necessário token de autenticação!")
                .build());

        config.add(new ResponseMessageBuilder()
                .code(403)
                .message("Não permitido! O usuário autenticado não tem permissão de acesso!")
                .build());

        config.add(new ResponseMessageBuilder()
                .code(500)
                .message("Erro não esperado no servidor!")
                .build());

        return config;
    }

}
