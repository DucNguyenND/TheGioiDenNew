package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDanhMucSanPhamRepository extends JpaRepository<DanhMucSanPham, Integer> {
    @Query(value ="select case when id_max is null then 1 else id_max+1 end from(select max(id) id_max from public.danh_muc_san_pham)",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into public.danh_muc_san_pham(ten_danh_muc,anh_danh_muc,id_thu_muc) values(?1,?2,?3)",nativeQuery = true)
    public void insertItem(@Param("tenDanhMuc")String tenDanhMuc, @Param("anhDanhMuc") String anhDanhMuc,@Param("idThuMuc") Integer id);

    @Query(value ="select * from public.danh_muc_san_pham where id_thu_muc=?1 order by MUC_DO_UU_TIEN",nativeQuery = true)
    public List<DanhMucSanPham> findByIdThuMuc(@Param("idThuMuc")Integer idDanhMuc);
}
