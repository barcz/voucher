package hu.bj.vouchers;

import hu.bj.vouchers.voucher.InMemoryVoucherDAO;
import hu.bj.vouchers.voucher.RedeemVoucher;
import hu.bj.vouchers.voucher.VoucherDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherConfig {

    @Bean
    public VoucherDAO inMemoryRepo(RedeemVoucher redeemVoucher) {
        return new InMemoryVoucherDAO(redeemVoucher);
    }

    @Bean
    public RedeemVoucher redeemVoucher() {
        return new RedeemVoucher();
    }

}
