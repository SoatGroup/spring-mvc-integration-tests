package fr.soat.java.webservices.test.integration;

import com.jayway.jsonpath.JsonPath;
import fr.soat.java.dao.IOrderRepository;
import fr.soat.java.enums.Status;
import fr.soat.java.model.OrderEntity;
import fr.soat.java.payload.Order;
import fr.soat.java.payload.wrappers.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/applicationContext.xml")
@TestExecutionListeners(listeners = OrderWebServiceTest.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class OrderWebServiceTest extends AbstractTestExecutionListener {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static final String SERVICE_URI = "/order";

	private static OrderEntity getOrderDataset;

	private static OrderEntity updateOrderDataset;

	private static OrderEntity deleteOrderDataset;

	@Autowired
	private IOrderRepository dao;

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		dao = testContext.getApplicationContext().getBean(IOrderRepository.class);
		getOrderDataset = new OrderEntity();
		getOrderDataset = dao.save(getOrderDataset);
		updateOrderDataset = new OrderEntity();
		updateOrderDataset = dao.save(updateOrderDataset);
		deleteOrderDataset = new OrderEntity();
		deleteOrderDataset = dao.save(deleteOrderDataset);
	}

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		dao.delete(getOrderDataset.get_id());
		dao.delete(updateOrderDataset.get_id());
	}

	@Test
	@Rollback(true)
	@Commit
	@Transactional
	public void testSaveOrder() throws Exception {
		String payload = "{ \"products\": [{ \"name\": \"Mon produit\" }]}";
		String jsonResponse = this.mockMvc
				.perform(post(SERVICE_URI).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8).content(payload))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Order responseOrder = JsonPath.parse(jsonResponse).read("$.data", Order.class);
		Assert.assertNotNull(responseOrder.getId());
		Assert.assertFalse(responseOrder.getId().isEmpty());
	}

	@Test
	public void testGetNotFoundOrder() throws Exception {
		String jsonResponse = this.mockMvc
				.perform(get(SERVICE_URI + "/" + "test").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		ResponseWrapper response = JsonPath.parse(jsonResponse).read("$", ResponseWrapper.class);
		Assert.assertTrue(response.getStatus() == Status.ERROR);

	}

	@Test
	public void testGetOrder() throws Exception {
		String jsonResponse = this.mockMvc.perform(get(SERVICE_URI + "/" + getOrderDataset.get_id()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Order responseOrder = JsonPath.parse(jsonResponse).read("$.data", Order.class);
		Assert.assertNotNull(responseOrder.getId());
		Assert.assertFalse(responseOrder.getId().isEmpty());
	}

	@Test
	public void testUpdateOrder() throws Exception {
		String maj = "Mon produit maj";
		String payload = "{ \"products\": [{ \"name\": \"" + maj + "\" }]}";
		String jsonResponse = this.mockMvc.perform(
				put(SERVICE_URI + "/" + updateOrderDataset.get_id()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8)
						.content(payload)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Order responseOrder = JsonPath.parse(jsonResponse).read("$.data", Order.class);
		Assert.assertTrue(maj.equals(responseOrder.getProducts().get(0).getName()));
	}

	@Test
	public void testDeleteOrder() throws Exception {
		String id = deleteOrderDataset.get_id();
		this.mockMvc.perform(delete(SERVICE_URI + "/" + id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		Assert.assertNull(dao.findOne(id));
	}
}

