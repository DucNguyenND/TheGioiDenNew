package com.example.demo.TheGioiDen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeySearchSanPhamReq {
    private String tenSanPham;
    private Integer idDanhMuc;
    private Integer page;
    private Integer size;
}
