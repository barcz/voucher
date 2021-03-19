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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class VoucherUserControllerTest {


    private final Date TOMORROW = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    private final List<VoucherInput> testInput = IntStream.range(1,4).mapToObj(i -> new VoucherInput("description"+i,  TOMORROW, false, 1)).collect(Collectors.toList());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void shouldRedeemFirstVoucher() throws Exception {
        this.mockMvc.perform(post("/admin/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testInput)))
                .andExpect(status().isOk());

        assertEquals(3, availableVouchers());

        this.mockMvc.perform(put("/vouchers/redeem/1")).andExpect(status().isOk());

        assertEquals(2, availableVouchers());

    }

    @Order(2)
    @Test
    public void shouldReturn404() throws Exception {
        this.mockMvc.perform(put("/vouchers/redeem/11")).andExpect(status().isNotFound());
    }

    @Order(3)
    @Test
    public void shouldReturn422() throws Exception {
        this.mockMvc.perform(put("/vouchers/redeem/1")).andExpect(status().isUnprocessableEntity());
    }

    private int availableVouchers() throws Exception {
        MvcResult getAvailableVouchers = this.mockMvc.perform(get("/vouchers")).andExpect(status().isOk()).andReturn();
        Voucher[] vouchers = objectMapper.readValue(getAvailableVouchers.getResponse().getContentAsString(), Voucher[].class);
        return vouchers.length;
    }

}
