package fr.soat.java.webservices.test.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/applicationContext.xml")
public class OrderWebServiceTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String SERVICE_URI = "/order";

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSaveOrder() throws Exception {
        String payload = "{ \"products\": [{ \"name\": \"Mon produit\" }]}";
        MockHttpServletRequestBuilder req = post(SERVICE_URI).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(payload);
        this.mockMvc.perform(req).andExpect(status().isOk());
    }

    @Test
    public void testGetOrder() throws Exception {
        MockHttpServletRequestBuilder req = get(SERVICE_URI + "/" + "test").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        this.mockMvc.perform(req)
                .andExpect(status().isNotFound());
    }
}

