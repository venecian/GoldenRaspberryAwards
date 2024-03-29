package br.com.filmes;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class ConfigTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void prepare() throws Exception {
        this.mockMvc = webAppContextSetup(this.context).build();
    }


}
