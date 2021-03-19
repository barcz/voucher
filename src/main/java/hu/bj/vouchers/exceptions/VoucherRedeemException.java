package hu.bj.vouchers.exceptions;

import hu.bj.vouchers.voucher.RedeemVoucher;

public class VoucherRedeemException extends RuntimeException {

    private RedeemVoucher.RESULT result;

    public VoucherRedeemException(RedeemVoucher.RESULT result) {
        this.result = result;
    }

    public RedeemVoucher.RESULT getResult() {
        return result;
    }
}
