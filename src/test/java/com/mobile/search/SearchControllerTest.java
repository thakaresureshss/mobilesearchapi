package com.mobile.search;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mobile.search.client.MobileApiClient;
import com.mobile.search.utils.JsonUtils;
import com.mobile.search.utils.TestData;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class SearchControllerTest {

  private MockMvc mockMvc;

  @MockBean
  private MobileApiClient mobileApiClient;

  @Autowired
  JsonUtils jsonUtils;


  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
    when(mobileApiClient.getMobiles()).thenReturn(TestData.getMobiles());
  }



  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenNoParameters_thenReturnAllResult() throws Exception {
    this.mockMvc.perform(get("/mobile/search").contentType(MediaType.APPLICATION_JSON)).andExpect(
        status().isOk()).andDo(
            print())
        .andExpect(
            jsonPath("$.length()",
                is(3)));
  }


  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenPriceParameters_thenReturnAllMatchingResults() throws Exception {
    this.mockMvc.perform(get("/mobile/search").param("priceEur",
        "200").contentType(MediaType.APPLICATION_JSON)).andExpect(
            status().isOk()).andDo(
                print())
        .andExpect(
            jsonPath("$.length()",
                is(2)))
        .andExpect(
            jsonPath("$.[0].release.priceEur",
                is(200)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenSimParameter_thenReturnAllMatchingResult() throws Exception {
    this.mockMvc.perform(get("/mobile/search").param("sim",
        "esim").contentType(MediaType.APPLICATION_JSON)).andExpect(
            status().isOk()).andDo(
                print())
        .andExpect(
            jsonPath("$.length()",
                is(2)));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenAnnounceDateParameter_thenReturnAllMatchingResults() throws Exception {
    this.mockMvc.perform(get("/mobile/search").param("announceDate", "1999").contentType(
        MediaType.APPLICATION_JSON)).andExpect(
            status().isOk()).andDo(
                print())
        .andExpect(
            jsonPath("$.length()",
                is(1)))
        .andExpect(
            jsonPath("$.[0].release.announceDate",
                containsString("1999")));
  }


  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenAnouceDateAndPriceParameters_thenAllMatchingResults() throws Exception {
    this.mockMvc.perform(get("/mobile/search").param("announceDate", "1999").param("priceEur",
        "200").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(
            print())
        .andExpect(
            jsonPath("$.length()", is(1)))
        .andExpect(
            jsonPath("$.[0].release.priceEur",
                is(200)))
        .andExpect(
            jsonPath("$.[0].release.announceDate",
                containsString("1999")));
  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenAllParameter_thenReturnAllMatchingResult() throws Exception {
    this.mockMvc.perform(get("/mobile/search")
        .param("announceDate", "1999").param("priceEur", "200")
        .param("brand", "Apple").param("phone", "Apple iPad Pro 12.9 (2018)")
        .param("sim", "eSIM").param("audioJack", "No")
        .param("gps", "Yes").param("battery", "Li-Po 9720 mAh battery (36.71 Wh)")

        // This is not valid search but adding here just to confirmed this is also considerred.
        .param("picture", "https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg")
        .param("resolution", "2048 x 2732 pixels")
        .contentType(
            MediaType.APPLICATION_JSON)).andExpect(
                status().isOk()).andDo(
                    print())
        .andExpect(
            jsonPath("$.length()",
                is(1)))
        .andExpect(
            jsonPath("$.[0].release.announceDate",
                containsString("1999")))
        .andExpect(
            jsonPath("$.[0].release.priceEur",
                is(200)));

  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenAllParameter_thenReturnZeroMatchingResult() throws Exception {
    this.mockMvc.perform(get("/mobile/search")
        .param("announceDate", "1999").param("priceEur", "200")
        .param("brand", "Apple").param("phone", "Apple iPad Pro 12.9 (2018)")
        .param("sim", "eSIM").param("audioJack", "Apple")
        .param("gps", "Yes").param("battery", "Li-Po 9720 mAh battery (36.71 Wh)")

        // This is not valid search but adding here just to confirmed this is also considerred.
        .param("picture", "https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg")
        .param("resolution", " ABC")
        .contentType(
            MediaType.APPLICATION_JSON)).andExpect(
                status().isOk()).andDo(
                    print())
        .andExpect(
            jsonPath("$.length()",
                is(0)));

  }

  @Test
  @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
  public void whenWrongParameter_thenReturns400() throws Exception {
    this.mockMvc.perform(get("/mobile/search").param("test", "1999").contentType(
        MediaType.APPLICATION_JSON)).andExpect(
            status().isBadRequest()).andDo(
                print());
  }

}
