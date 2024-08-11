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
@Table(name = "PHAN_LOAI")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanLoaiDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_PHAN_LOAI")
    private String tenPhanLoai;

    @Column(name = "GROUP_VALUE")
    private String groupValue;

    @Column(name = "GROUP_CODE")
    private Integer groupCode;

    @Column(name = "GROUP_NAME")
    private String groupName;

    @Column(name = "ID_SAN_PHAM")
    private Integer idSanPham;
}
