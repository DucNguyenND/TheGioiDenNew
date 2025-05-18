package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    @Query(value = "SELECT * FROM public.SAN_PHAM ORDER BY MUC_DO_UU_TIEN LIMIT :size OFFSET :page", nativeQuery = true)
    List<Product> getAllProducts(@Param("size") Integer size, @Param("page") Integer page);

    @Query(value = "SELECT * FROM public.SAN_PHAM WHERE danh_muc_san_pham_id = :id ORDER BY MUC_DO_UU_TIEN LIMIT :size OFFSET :page", nativeQuery = true)
    List<Product> getAllProductsByCategory(@Param("id") Integer id, @Param("size") Integer size, @Param("page") Integer page);

    @Query(value = "SELECT * FROM public.SAN_PHAM WHERE id = :id", nativeQuery = true)
    Product getProductById(@Param("id") Integer id);

    @Query(value = "SELECT MAX(id) id_max FROM public.SAN_PHAM", nativeQuery = true)
    Integer findMaxId();

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO public.SAN_PHAM(id,ten_san_pham,thuong_hieu,ma_sp,gia_sp,dien_ap,cong_suat,chi_so_hoan_mau,tuoi_tho,anh_sang,kich_thuoc,mo_ta,link_anh_chinh,danh_muc_san_pham_id,hieu_suat,goc_chieu,muc_do_uu_tien) VALUES(nextval('seq_san_pham'),?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16)", nativeQuery = true)
    void insertProduct(
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
            @Param("gocChieu") String gocChieu,
            @Param("mucDoUuTien") Integer mucDoUuTien
    );

    @Query(value = "SELECT * FROM public.SAN_PHAM WHERE UPPER(ten_san_pham) LIKE UPPER(CONCAT('%',:tenSanPham,'%')) AND (:idDanhMuc IS NULL OR danh_muc_san_pham_id = :idDanhMuc) ORDER BY MUC_DO_UU_TIEN LIMIT :size OFFSET :page", nativeQuery = true)
    List<Product> searchProducts(@Param("tenSanPham") String tenSanPham, @Param("idDanhMuc") Integer idDanhMuc, @Param("size") Integer size, @Param("page") Integer page);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.SAN_PHAM SET ten_san_pham=?1,thuong_hieu=?2,ma_sp=?3,gia_sp=?4,dien_ap=?5,cong_suat=?6,chi_so_hoan_mau=?7,tuoi_tho=?8,anh_sang=?9,kich_thuoc=?10,mo_ta=?11,link_anh_chinh=?12,danh_muc_san_pham_id=?13,hieu_suat=?15,goc_chieu=?16,muc_do_uu_tien=?17 WHERE id=?14", nativeQuery = true)
    void updateProduct(
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
            @Param("gocChieu") String gocChieu,
            @Param("mucDoUuTien") Integer mucDoUuTien
    );

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM public.SAN_PHAM WHERE id = :id", nativeQuery = true)
    void deleteProductById(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.SAN_PHAM SET muc_do_uu_tien = ?1 WHERE id = ?2", nativeQuery = true)
    void updatePriority(
            @Param("mucDoUuTien") Integer mucDoUuTien,
            @Param("id") Integer id
    );

    @Query(value = "SELECT * FROM public.SAN_PHAM WHERE danh_muc_san_pham_id = :idDanhMuc ORDER BY MUC_DO_UU_TIEN", nativeQuery = true)
    List<Product> getAllProductsByCategory(@Param("idDanhMuc") Integer idDanhMuc);
} 