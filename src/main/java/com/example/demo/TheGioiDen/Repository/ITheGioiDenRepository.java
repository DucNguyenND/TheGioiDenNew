package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITheGioiDenRepository  extends JpaRepository<SanPham, Integer> {
    @Query(value ="select *,count(*) as TOTAL_ELEMENT from the_gioi_den_95.public.SAN_PHAM group by id LIMIT :size OFFSET :page",nativeQuery = true)
    public List<SanPham> getAllSanPham(@Param("size") Integer size,@Param("page") Integer page);

    @Query(value ="select * from the_gioi_den_95.public.SAN_PHAM where danh_muc_san_pham_id=:id LIMIT :size OFFSET :page",nativeQuery = true)
    public List<SanPham> getAllSanPhamByIdDanhMuc(@Param("page") Integer page,@Param("id") Integer id,@Param("size") Integer size);

    @Query(value ="select * from the_gioi_den_95.public.SAN_PHAM where id=:id",nativeQuery = true)
    public SanPham getSanPhamById(@Param("id") Integer id);


    @Query(value ="select  max(id) id_max from the_gioi_den_95.public.SAN_PHAM",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into the_gioi_den_95.public.SAN_PHAM(id,ten_san_pham,thuong_hieu,ma_sp,gia_sp,dien_ap,cong_suat,chi_so_hoan_mau,tuoi_tho,anh_sang,kich_thuoc,mo_ta,link_anh_chinh,danh_muc_san_pham_id) values(nextval('seq_san_pham'),?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13)",nativeQuery = true)
    public void insertItem(
            @Param("tenSanPham") String tenSanPham,
            @Param("thuongHieu") String thuongHieu,
            @Param("maSp") String maSp,
            @Param("giaSp") String giaSp,
            @Param("dienAp") String dienAp,
            @Param("congSuat") String congSuat,
            @Param("chiSoHoanMau") String chiSoHoanMau,
            @Param("tuoiTho") String tuoiTho,
            @Param("anhSang") String anhSang,
            @Param("kichThuoc") String kichThuoc,
            @Param("moTa") String moTa,
            @Param("linkAnhChinh") String linkAnhChinh,
            @Param("danhMucSanPhamId") Integer danhMucSanPhamId
            );


    @Query(value ="select  *,count(*) as TOTAL_ELEMENT from the_gioi_den_95.public.SAN_PHAM where upper(ten_san_pham) like upper(concat('%',concat(:tenSanPham,'%'))) and(:idDanhMuc is null or danh_muc_san_pham_id=:idDanhMuc) group by id LIMIT :size OFFSET :page ",nativeQuery = true)
    public List<SanPham> search(@Param("tenSanPham") String tenSanPham,@Param("idDanhMuc") Integer idDanhMuc,@Param("size") Integer size,@Param("page") Integer page);
}
