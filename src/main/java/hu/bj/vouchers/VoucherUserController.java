package hu.bj.vouchers;

import hu.bj.vouchers.exceptions.VoucherNotFoundException;
import hu.bj.vouchers.exceptions.VoucherRedeemException;
import hu.bj.vouchers.voucher.RedeemVoucher;
import hu.bj.vouchers.voucher.Voucher;
import hu.bj.vouchers.voucher.VoucherDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("vouchers")
public class VoucherUserController {

    private VoucherDAO voucherDao;
    private RedeemVoucher redeemVoucher;

    public VoucherUserController(VoucherDAO voucherDao, RedeemVoucher redeemVoucher) {
        this.voucherDao = voucherDao;
        this.redeemVoucher = redeemVoucher;
    }

    @PutMapping("/redeem/{id}")
    public void redeem(@PathVariable Long id) {
        try {
            Voucher voucherById = voucherDao.findVoucherById(id);
            Voucher redeemed = redeemVoucher.redeem(voucherById);
            voucherDao.saveVoucher(redeemed);
        } catch (VoucherNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Voucher id %d not found", ex.getId()), ex);
        } catch (VoucherRedeemException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("Voucher can\'t redeemed: %s", ex.getResult()), ex);
        }
    }

    @GetMapping()
    public List<Voucher> allRedeemableVoucher() {
        return voucherDao.getAllAvailableVouchers();
    }

}
