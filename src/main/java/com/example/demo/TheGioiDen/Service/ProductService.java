package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.entity.Product;
import com.example.demo.TheGioiDen.entity.SanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private TheGioiDenService theGioiDenService;

    public Product getProductById(int id) {
        SanPham sanPham = theGioiDenService.findById(id);
        if (sanPham == null) {
            return null;
        }
        return convertToProduct(sanPham);
    }

    private Product convertToProduct(SanPham sp) {
        return Product.builder()
                .id(sp.getId())
                .tenSanPham(sp.getTenSanPham())
                .thuongHieu(sp.getThuongHieu())
                .maSp(sp.getMaSp())
                .giaSp(sp.getGiaSp())
                .dienAp(sp.getDienAp())
                .congSuat(sp.getCongSuat())
                .chiSoHoanMau(sp.getChiSoHoanMau())
                .tuoiTho(sp.getTuoiTho())
                .anhSang(sp.getAnhSang())
                .kichThuoc(sp.getKichThuoc())
                .moTa(sp.getMoTa())
                .linkAnhChinh(sp.getLinkAnhChinh())
                .danhMucSanPhamId(sp.getDanhMucSanPhamId())
                .hieuSuat(sp.getHieuSuat())
                .gocChieu(sp.getGocChieu())
                .mucDoUuTien(sp.getMucDoUuTien())
                .build();
    }
} 