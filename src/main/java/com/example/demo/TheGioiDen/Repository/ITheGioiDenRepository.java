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
    @Query(value ="with tmp as(select count(*) as TOTAL_ELEMENT from SAN_PHAM)select tmp.*,sp.* from the_gioi_den_95.public.SAN_PHAM sp join tmp on 1=1 order by id LIMIT :size OFFSET :page",nativeQuery = true)
    public List<SanPham> getAllSanPham(@Param("size") Integer size,@Param("page") Integer page);

    @Query(value ="with tmp as(select count(*) as TOTAL_ELEMENT from SAN_PHAM where danh_muc_san_pham_id=?1)select tmp.*,sp.* from the_gioi_den_95.public.SAN_PHAM sp join tmp on 1=1 and sp.danh_muc_san_pham_id=?1 order by id LIMIT ?2 OFFSET ?3 ",nativeQuery = true)
    public List<SanPham> getAllSanPhamByIdDanhMuc(@Param("id") Integer id,@Param("size") Integer size,@Param("page") Integer page);

    @Query(value ="select *,1 as TOTAL_ELEMENT from the_gioi_den_95.public.SAN_PHAM where id=:id",nativeQuery = true)
    public SanPham getSanPhamById(@Param("id") Integer id);


    @Query(value ="select  max(id) id_max from the_gioi_den_95.public.SAN_PHAM",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into the_gioi_den_95.public.SAN_PHAM(id,ten_san_pham,thuong_hieu,ma_sp,gia_sp,dien_ap,cong_suat,chi_so_hoan_mau,tuoi_tho,anh_sang,kich_thuoc,mo_ta,link_anh_chinh,danh_muc_san_pham_id,hieu_suat,goc_chieu) values(nextval('seq_san_pham'),?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15)",nativeQuery = true)
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
            @Param("danhMucSanPhamId") Integer danhMucSanPhamId,
            @Param("hieuSuat") String hieuSuat,
            @Param("gocChieu") String gocChieu
            );


    @Query(value ="with tmp as(select count(*) as TOTAL_ELEMENT from SAN_PHAM  where upper(ten_san_pham) like upper(concat('%',concat(:tenSanPham,'%'))) and(:idDanhMuc is null or danh_muc_san_pham_id=:idDanhMuc))select tmp.*,sp.* from the_gioi_den_95.public.SAN_PHAM sp join tmp on 1=1 where upper(sp.ten_san_pham) like upper(concat('%',concat(:tenSanPham,'%'))) and(:idDanhMuc is null or sp.danh_muc_san_pham_id=:idDanhMuc) LIMIT :size OFFSET :page ",nativeQuery = true)
    public List<SanPham> search(@Param("tenSanPham") String tenSanPham,@Param("idDanhMuc") Integer idDanhMuc,@Param("size") Integer size,@Param("page") Integer page);

    @Modifying(clearAutomatically = true)
    @Query(value ="update the_gioi_den_95.public.SAN_PHAM set ten_san_pham=?1,thuong_hieu=?2,ma_sp=?3,gia_sp=?4,dien_ap=?5,cong_suat=?6,chi_so_hoan_mau=?7,tuoi_tho=?8,anh_sang=?9,kich_thuoc=?10,mo_ta=?11,link_anh_chinh=?12,danh_muc_san_pham_id=?13,hieu_suat=?15,goc_chieu=?16 where id=?14",nativeQuery = true)
    public void updateItem(
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
            @Param("danhMucSanPhamId") Integer danhMucSanPhamId,
            @Param("id") Integer id,
            @Param("hieuSuat") String hieuSuat,
            @Param("gocChieu") String gocChieu
    );
    @Modifying(clearAutomatically = true)
    @Query(value ="delete from the_gioi_den_95.public.SAN_PHAM where id=:id",nativeQuery = true)
    public Integer deleteByIdSanPham(@Param("id") Integer id);
}
