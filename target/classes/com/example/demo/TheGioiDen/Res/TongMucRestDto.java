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
public class TongMucRestDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tenTongMuc;
    private Integer id;
    private Integer mucDoUuTien;
    private List<ThuMucRestDto> listThuMuc;
}
