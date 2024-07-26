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
@Table(name = "SAN_PHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_SAN_PHAM")
    private String tenSanPham;

    @Column(name = "THUONG_HIEU")
    private String thuongHieu;

    @Column(name = "MA_SP")
    private String maSp;

    @Column(name = "GIA_SP")
    private String giaSp;

    @Column(name = "DIEN_AP")
    private String dienAp;

    @Column(name = "CONG_SUAT")
    private String congSuat;

    @Column(name = "CHI_SO_HOAN_MAU")
    private String chiSoHoanMau;

    @Column(name = "TUOI_THO")
    private String tuoiTho;

    @Column(name = "ANH_SANG")
    private String anhSang;

    @Column(name = "KICH_THUOC")
    private String kichThuoc;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "LINK_ANH_CHINH")
    private String linkAnhChinh;

    @Column(name = "DANH_MUC_SAN_PHAM_ID")
    private Integer danhMucSanPhamId;

    @Column(name = "TOTAL_ELEMENT")
    private Integer totalElement;

    @Column(name = "HIEU_SUAT")
    private String hieuSuat;

    @Column(name = "GOC_CHIEU")
    private String gocChieu;

    @Column(name = "MUC_DO_UU_TIEN")
    private Integer mucDoUuTien;

}
