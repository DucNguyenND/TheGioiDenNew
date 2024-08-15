package com.example.demo.TheGioiDen.Request;

import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.PhanLoaiDto;
import com.example.demo.TheGioiDen.entity.SanPham;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SanPhamResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private SanPham sanPham;
    private Integer idDanhMuc;
    private String tenDanhMuc;
    private Integer idThuMuc;
    private String tenThuMuc;
    private Integer idTongMuc;
    private String tenTongMuc;

   private List<AnhSanPham>listAnh;

    private List<PhanLoaiDto> listCongSuat;
    private List<PhanLoaiDto> listKichThuoc;
    private List<PhanLoaiDto> listDynamic;

}

