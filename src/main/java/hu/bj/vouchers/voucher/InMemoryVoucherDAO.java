package hu.bj.vouchers.voucher;

import hu.bj.vouchers.exceptions.VoucherNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryVoucherDAO implements VoucherDAO {

    private Map<Long, Voucher> vouchers;
    private RedeemVoucher redeemVoucher;

    public InMemoryVoucherDAO(RedeemVoucher redeemVoucher) {
        this.redeemVoucher = redeemVoucher;
        vouchers = new HashMap<>();
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return vouchers.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Voucher> getAllAvailableVouchers() {
        return getAllVouchers().stream().filter(v -> redeemVoucher.canRedeem(v) == RedeemVoucher.RESULT.REDEEMABLE).collect(Collectors.toList());
    }

    @Override
    public Voucher findVoucherById(long id) {
        Voucher voucher = vouchers.get(id);
        if( voucher != null) return voucher; else throw new VoucherNotFoundException(id);
    }

    private long nextId() {
        if(vouchers.keySet().isEmpty()) {
            return 1L;
        } else {
            Optional<Long> max = vouchers.keySet().stream().max(Comparator.naturalOrder());
            return max.get() + 1L;
        }
    }

    @Override
    public void createVoucher(VoucherInput v) {
        Voucher voucher = v.createVoucher(nextId());
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public void saveVoucher(Voucher v) {
        vouchers.put(v.getId(), v);
    }

    @Override
    public void deleteVoucher(long id) {
        if(vouchers.containsKey(id)) {
            vouchers.remove(id);
        } else throw new VoucherNotFoundException(id);
    }
}
