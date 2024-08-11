package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.PhanLoaiDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IPhanLoaiRepository extends JpaRepository<PhanLoaiDto, Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into public.phan_loai(ten_phan_loai,group_value,group_code,gia_tien,id_san_pham) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    public void insertItem(
            @Param("tenPhanLoai") String tenPhanLoai,
            @Param("groupValue") String groupValue,
            @Param("groupCode") Integer groupCode,
            @Param("giaTien") BigDecimal giaTien,
            @Param("idSanPham") Integer idSanPham
    );

    @Query(value ="select * from public.phan_loai where id_san_pham=?1 and group_code=1",nativeQuery = true)
    public List<PhanLoaiDto> findCongSuatByIdSanPham(@Param("idSanPham") Integer idSanPham);

    @Query(value ="select * from public.phan_loai where id_san_pham=?1 and group_code=2",nativeQuery = true)
    public List<PhanLoaiDto> findKichThuocByIdSanPham(@Param("idSanPham") Integer idSanPham);

    @Query(value ="select * from public.phan_loai where id_san_pham=?1 and group_code=3",nativeQuery = true)
    public List<PhanLoaiDto> findDynamicByIdSanPham(@Param("idSanPham") Integer idSanPham);
}
