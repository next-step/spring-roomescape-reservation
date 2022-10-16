package nextstep;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
public class ApiDocument {

  @Autowired
  protected MockMvc mockMvc;

  protected static RestDocumentationResultHandler toDocument(String title) {
    return document(
        title,
        preprocessRequest(modifyUris()
                .scheme("http")
                .host("localhost")
                .removePort(),
            prettyPrint()
        ),
        preprocessResponse(prettyPrint()));
  }

  protected static String toJson(Object target) {
    var objectMapper = new ObjectMapper();
    try {
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      return objectMapper.writeValueAsString(target);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("직렬화 오류");
    }
  }

}
