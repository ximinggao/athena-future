package com.athena.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lingfeng on 2015/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AccountApplication.class})
@WebAppConfiguration
public class RestDocTest {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testDoc() throws Exception {
        this.mockMvc.perform(get("/restdoc/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("doc"));
    }

    @Test
    public void testField() throws Exception {
        this.mockMvc.perform(get("/restdoc/obj").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("field", responseFields(
                        fieldWithPath("name").description("The user's name details"),
                        fieldWithPath("pwd").description("The user's password")
                )));
    }

    @Test
    public void testJsonField() throws Exception {
        this.mockMvc.perform(get("/restdoc/objJson").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("jsonField", responseFields(
                        fieldWithPath("name").type(JsonFieldType.STRING)
                                .optional()
                                .description("The user's name details"),
                        fieldWithPath("pwd").type(JsonFieldType.STRING)
                                .optional()
                                .description("The user's password"),
                        fieldWithPath("info.username").type(JsonFieldType.STRING)
                                .optional()
                                .description("The user's username")
                )));
    }

    @Test
    public void testFieldWithCustomizingAttribute() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "user");
        params.put("pwd", "pwd");

        this.mockMvc.perform(post("/restdoc/objParam").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isCreated())
                .andDo(document("fieldAttribute", requestFields(
                        attributes(
                                key("title").value("Fields for user creation")),
                        fieldWithPath("name").description("The user's name details")
                                .attributes(
                                        key("constraints").value("Must not be null or empty")),
                        fieldWithPath("pwd").description("The user's password")
                                .attributes(
                                        key("constraints").value("Must by a valid password"))
                )));
    }

    @Test
    public void testPathParam() throws Exception {
        this.mockMvc.perform(get("/restdoc/{id}/{mobile}", 1L, "15021489117"))
                .andExpect(status().isOk())
                .andDo(document("pathParam", pathParameters(
                        parameterWithName("id").description("The stadium's id"),
                        parameterWithName("mobile").description("The user's mobile")
                )));
    }

    @Test
    public void testHeaders() throws Exception {
        this.mockMvc.perform(get("/restdoc/headerParam").header("Authorization", "Base dXNlcjpzWNyZXQ="))
                .andExpect(status().isOk())
                .andDo(document("headers",
                        requestHeaders(headerWithName("Authorization").description("Basic auth credentials")),
                        responseHeaders(
                                headerWithName("X-RateLimit-Limit")
                                        .description("The total number of requests permitted per period"),
                                headerWithName("X-RateLimit-Remaining")
                                        .description("Remaining requests permitted in current period"),
                                headerWithName("X-RateLimit-Reset")
                                        .description("Time at which the rate limit period will reset")
                        )
                ));
    }
}