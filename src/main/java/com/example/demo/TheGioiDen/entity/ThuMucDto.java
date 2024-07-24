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
@Table(name = "THU_MUC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuMucDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEN_THU_MUC")
    private String tenThuMuc;

    @Column(name = "MUC_DO_UU_TIEN")
    private Integer mucDoUuTien;

}
