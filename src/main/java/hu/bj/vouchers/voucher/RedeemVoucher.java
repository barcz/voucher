package hu.bj.vouchers.voucher;

import hu.bj.vouchers.exceptions.VoucherRedeemException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedeemVoucher {

    public enum RESULT {
        REDEEMABLE(0), DUE_DATE(1), ALREADY_REDEEMED(2);

        private int code;

        RESULT(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private RESULT validateDueDate(Voucher v) {
        if (new Date().before(v.getDueDate())) {
            return RESULT.REDEEMABLE;
        } else return RESULT.DUE_DATE;
    }

    private RESULT numberOfRedeems(Voucher v) {
        if (v.isMulti() || v.getAvailableRedeems() > 0) {
            return RESULT.REDEEMABLE;
        } else return RESULT.ALREADY_REDEEMED;
    }

    public RESULT canRedeem(Voucher v) {
        List<RESULT> allErrors = Stream.of(numberOfRedeems(v), validateDueDate(v))
                .filter(r -> r != RESULT.REDEEMABLE).collect(Collectors.toList());
        if (allErrors.size() > 0) {
            return allErrors.get(0);
        } else return RESULT.REDEEMABLE;

    }

    public Voucher redeem(Voucher v) {
        RESULT checkResult = canRedeem(v);
        if (checkResult == RESULT.REDEEMABLE) {
            if (!v.isMulti()) {
                v.setAvailableRedeems(v.getAvailableRedeems() - 1);
            }
            return v;
        } else {
            throw new VoucherRedeemException(checkResult);
        }
    }
}
