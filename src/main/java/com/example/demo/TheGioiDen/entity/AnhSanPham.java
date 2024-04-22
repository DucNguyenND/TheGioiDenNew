package com.example.demo.TheGioiDen.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ANH_SAN_PHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnhSanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LINK_ANH")
    private String linkAnh;

    @Column(name = "san_pham_id")
    private Integer sanPhamId;

//    @ManyToOne
//    private SanPham sanPham;

}
