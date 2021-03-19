package hu.bj.vouchers.voucher;

import hu.bj.vouchers.exceptions.VoucherRedeemException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RedeemVoucherTest {

    private final Date YESTERDAY = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
    private final Date TOMORROW = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

    private Voucher cantRedeemDueDate = new Voucher(1, "test_desc", YESTERDAY, false, 3);
    private Voucher cantRedeem0Redeems = new Voucher(1, "test_desc", TOMORROW, false, 0);
    private Voucher canRedeemMultipleTimes = new Voucher(1, "test_desc", TOMORROW, true, 0);
    private Voucher canRedeem10Times = new Voucher(1, "test_desc", TOMORROW, false, 10);

    private RedeemVoucher underTest = new RedeemVoucher();

    @Test
    public void shouldNotRedeemDueDate() {
        VoucherRedeemException exception = assertThrows(VoucherRedeemException.class, () -> {
            underTest.redeem(cantRedeemDueDate);
        });
        assertEquals(RedeemVoucher.RESULT.DUE_DATE, exception.getResult());
    }

    @Test
    public void shouldNotRedeem0RedeemsAvailable() {
        VoucherRedeemException exception = assertThrows(VoucherRedeemException.class, () -> {
            underTest.redeem(cantRedeem0Redeems);
        });
        assertEquals(RedeemVoucher.RESULT.ALREADY_REDEEMED, exception.getResult());
    }

    @Test
    public void shouldRedeemMultipleTimes() {
        Voucher test = canRedeemMultipleTimes;
        for (int i = 0; i < 100; i++) {
            test = underTest.redeem(test);
            assertNotNull(test);
        }
    }

    @Test
    public void shouldRedeem10Times() {
        Voucher test = canRedeem10Times;
        for (int i = 0; i < 10; i++) {
            test = underTest.redeem(test);
            assertNotNull(test);
        }
        Voucher finalTest = test;
        VoucherRedeemException exception = assertThrows(VoucherRedeemException.class, () -> {
            underTest.redeem(finalTest);
        });
        assertEquals(RedeemVoucher.RESULT.ALREADY_REDEEMED, exception.getResult());
    }

}
