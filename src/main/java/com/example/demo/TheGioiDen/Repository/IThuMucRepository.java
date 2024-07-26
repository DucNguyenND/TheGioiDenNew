package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.Res.ThuMucRestDto;
import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
import com.example.demo.TheGioiDen.entity.ThuMucDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IThuMucRepository extends JpaRepository<ThuMucDto, Integer> {
    @Query(value ="select * from public.thu_muc where id=:id ",nativeQuery = true)
    public List<ThuMucDto> getAnhSanPhamByIdSanPham(@Param("id") Integer id);

    @Query(value ="select * from public.thu_muc order by muc_do_uu_tien",nativeQuery = true)
    public List<ThuMucDto> getAllThuMuc();

    @Modifying(clearAutomatically = true)
    @Query(value ="delete from public.thu_muc where id=:id",nativeQuery = true)
    public Integer deleteByIdThuMuc(@Param("id") Integer id);

    @Query(value ="select case when id_max is null then 1 else id_max+1 end from(select max(id) id_max from public.anh_san_pham)",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into public.thu_muc(ten_thu_muc,muc_do_uu_tien,id_tong_muc) values(?1,?2,?3)",nativeQuery = true)
    public void insertItem(@Param("tenThuMuc")String tenThuMuc,
                           @Param("mucDoUuTien") Integer mucDoUuTien,
                           @Param("mucDoUuTien") Integer idTongMuc
                           );

    @Modifying(clearAutomatically = true)
    @Query(value ="update public.thu_muc set muc_do_uu_tien=?1 where id=?2",nativeQuery = true)
    public void mucDoUuTien(
            @Param("mucDoUuTien") Integer mucDoUuTien,
            @Param("id") Integer id
    );

    @Query(value ="select * from public.thu_muc where id_thu_muc=?1 order by MUC_DO_UU_TIEN",nativeQuery = true)
    public List<ThuMucDto> findByIdTongMuc(@Param("idTongMuc")Integer idTongMuc);

}
