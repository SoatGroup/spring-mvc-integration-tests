package fr.soat.java.webservices.test.integration;

import fr.soat.java.dao.IOrderRepository;
import fr.soat.java.model.OrderEntity;
import org.junit.After;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private OrderEntity orderDataset;

    @Autowired
    private IOrderRepository dao;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        orderDataset = new OrderEntity();
        orderDataset = dao.save(orderDataset);
    }

    @After
    public void tearDown() throws Exception {
        dao.delete(orderDataset.get_id());
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
    public void testGetNotFoundOrder() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "/" + "test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testGetOrder() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "/" + orderDataset.get_id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());
    }
}

