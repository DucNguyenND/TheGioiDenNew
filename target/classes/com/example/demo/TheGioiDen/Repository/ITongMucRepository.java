package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.ThuMucDto;
import com.example.demo.TheGioiDen.entity.TongMucDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITongMucRepository extends JpaRepository<TongMucDto, Integer> {
    @Query(value ="select * from public.tong_muc where id=:id ",nativeQuery = true)
    public TongMucDto getTongMucById(@Param("id") Integer id);

    @Query(value ="select * from public.tong_muc order by muc_do_uu_tien",nativeQuery = true)
    public List<TongMucDto> getAllTongMuc();

    @Modifying(clearAutomatically = true)
    @Query(value ="delete from public.tong_muc where id=:id",nativeQuery = true)
    public Integer deleteByIdTongMuc(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into public.tong_muc(ten_tong_muc,muc_do_uu_tien) values(?1,?2)",nativeQuery = true)
    public void insertItem(@Param("tenThuMuc")String tenTongMuc,
                           @Param("mucDoUuTien") Integer mucDoUuTien);

    @Modifying(clearAutomatically = true)
    @Query(value ="update public.tong_muc set muc_do_uu_tien=?1 where id=?2",nativeQuery = true)
    public void mucDoUuTien(
            @Param("mucDoUuTien") Integer mucDoUuTien,
            @Param("id") Integer id
    );

}
