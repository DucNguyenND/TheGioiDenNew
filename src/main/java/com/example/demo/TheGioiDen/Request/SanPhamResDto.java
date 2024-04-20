package com.example.demo.TheGioiDen.Request;

import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.SanPham;
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
public class SanPhamResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private SanPham sanPham;

   private List<AnhSanPham>listAnh;


}

