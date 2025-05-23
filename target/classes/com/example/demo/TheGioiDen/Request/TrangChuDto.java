package com.example.demo.TheGioiDen.Request;

import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
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
public class TrangChuDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private DanhMucSanPham danhMucSanPham;
    private List<SanPham> listSanPham;
}
