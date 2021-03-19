package hu.bj.vouchers.voucher;

import java.util.Date;

public class Voucher {

    private long id;
    private String description;
    private Date dueDate;
    private boolean multi;
    private int availableRedeems;

    public Voucher(long id, String description, Date dueDate, boolean multi, int availableRedeems) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.multi = multi;
        this.availableRedeems = availableRedeems;
    }

    public long getId() {
        return id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isMulti() {
        return multi;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableRedeems() {
        return availableRedeems;
    }

    public void setAvailableRedeems(int availableRedeems) {
        this.availableRedeems = availableRedeems;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", multi=" + multi +
                ", availableRedeems=" + availableRedeems +
                '}';
    }
}
