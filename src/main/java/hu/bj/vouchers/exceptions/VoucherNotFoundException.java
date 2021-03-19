package hu.bj.vouchers.exceptions;

public class VoucherNotFoundException extends RuntimeException {

    private long id;

    public VoucherNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
