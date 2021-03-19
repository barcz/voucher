package hu.bj.vouchers.voucher;

import java.util.List;

public interface VoucherDAO {

    List<Voucher> getAllVouchers();

    List<Voucher> getAllAvailableVouchers();

    Voucher findVoucherById(long id);

    void createVoucher(VoucherInput v);

    void saveVoucher(Voucher v);

    void deleteVoucher(long id);
}
