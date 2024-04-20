package com.example.demo.TheGioiDen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DANH_MUC_SAN_PHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMucSanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_DANH_MUC")
    private String tenDanhMuc;

    @Column(name = "ANH_DANH_MUC")
    private String anhDanhMuc;
}
