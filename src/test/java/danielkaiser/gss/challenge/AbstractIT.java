package danielkaiser.gss.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public abstract class AbstractIT {

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @BeforeEach
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert
     * @return the JSON byte array
     */
    @SneakyThrows
    public byte[] convertObjectToJsonBytes(final Object object) {
        return createOrGetObjectMapper().writeValueAsBytes(object);
    }

    @SneakyThrows
    protected <T> T convertJsonToObject(String json, Class<T> valueType) {
        return createOrGetObjectMapper().readValue(json, valueType);
    }

    @SneakyThrows
    protected <T> T convertJsonToObject(String json, TypeReference<T> valueType) {
        return createOrGetObjectMapper().readValue(json, valueType);
    }

    private ObjectMapper createOrGetObjectMapper() {
        if (objectMapper == null) {
            objectMapper = Jackson2ObjectMapperBuilder.json().build();
        }
        return objectMapper;
    }
}
