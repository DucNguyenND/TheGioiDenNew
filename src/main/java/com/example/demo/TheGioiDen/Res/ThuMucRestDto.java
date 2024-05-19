package com.example.demo.TheGioiDen.Res;

import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuMucRestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tenThuMuc;
    private Integer id;
    private List<DanhMucSanPham> listDanhMuc;
}
