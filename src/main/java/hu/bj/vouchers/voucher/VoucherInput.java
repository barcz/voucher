package hu.bj.vouchers.voucher;

import java.util.Date;

public class VoucherInput {
    private String description;
    private Date dueDate;
    private boolean multi;
    private int availableRedeems;

    public VoucherInput(String description, Date dueDate, boolean multi, int availableRedeems) {
        this.description = description;
        this.dueDate = dueDate;
        this.multi = multi;
        this.availableRedeems = availableRedeems;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isMulti() {
        return multi;
    }

    public int getAvailableRedeems() {
        return availableRedeems;
    }

    public Voucher createVoucher(long id) {
        return new Voucher(id, this.description, this.dueDate, this.multi, this.availableRedeems);
    }

    @Override
    public String toString() {
        return "VoucherInput{" +
                "description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", multi=" + multi +
                ", availableRedeems=" + availableRedeems +
                '}';
    }
}
