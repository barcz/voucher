package hu.bj.vouchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bj.vouchers.voucher.Voucher;
import hu.bj.vouchers.voucher.VoucherInput;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class VoucherAdminControllerTest {

    private final Date TOMORROW = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    private final List<VoucherInput> testInput = IntStream.range(1,4).mapToObj(i -> new VoucherInput("description"+i,  TOMORROW, false, 2)).collect(Collectors.toList());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void shouldReturnEmptyList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/admin/vouchers"))
                .andExpect(status().isOk()).andReturn();
        Voucher[] vouchers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Voucher[].class);
        assertEquals(0, vouchers.length);
    }

    @Test
    @Order(2)
    public void shouldReturnThreeVouchers() throws Exception {
        this.mockMvc.perform(post("/admin/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testInput)))
                .andExpect(status().isOk());
        MvcResult mvcResult = this.mockMvc.perform(get("/admin/vouchers"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Voucher[] vouchers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Voucher[].class);
        assertEquals(3, vouchers.length);
    }

    @Test
    @Order(3)
    public void shouldDeleteAndReturnTwoVouchers() throws Exception {
        this.mockMvc.perform(delete("/admin/vouchers/1"))
                .andExpect(status().isOk());
        MvcResult mvcResult = this.mockMvc.perform(get("/admin/vouchers"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        Voucher[] vouchers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Voucher[].class);
        assertEquals(2, vouchers.length);
    }

    @Test
    @Order(4)
    public void shouldReturn404() throws Exception {
        this.mockMvc.perform(delete("/admin/vouchers/11"))
                .andExpect(status().isNotFound());
    }


}
