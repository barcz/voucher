package hu.bj.vouchers;

import hu.bj.vouchers.voucher.Voucher;
import hu.bj.vouchers.exceptions.VoucherNotFoundException;
import hu.bj.vouchers.voucher.VoucherInput;
import hu.bj.vouchers.voucher.VoucherDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("admin")
public class VoucherAdminController {

    private VoucherDAO repo;

    public VoucherAdminController(VoucherDAO r) {
        this.repo = r;
    }

    @PostMapping("/vouchers")
    public void createVouchers(@RequestBody List<VoucherInput> vs) {
        vs.forEach(v -> repo.createVoucher(v));
    }

    @GetMapping(value = "/vouchers", produces = "application/json")
    public List<Voucher> getAllVouchers() {
        return repo.getAllVouchers();
    }

    @DeleteMapping("/vouchers/{id}")
    public void deleteVoucher(@PathVariable Long id) {
        try {
            repo.deleteVoucher(id);
        } catch (VoucherNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Voucher id %d not found", ex.getId()), ex);
        }
    }
}
