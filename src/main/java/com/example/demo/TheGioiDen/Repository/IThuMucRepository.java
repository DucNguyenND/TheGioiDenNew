package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.Res.ThuMucRestDto;
import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.ThuMucDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IThuMucRepository extends JpaRepository<ThuMucDto, Integer> {
    @Query(value ="select * from the_gioi_den_95.public.thu_muc where id=:id",nativeQuery = true)
    public List<ThuMucDto> getAnhSanPhamByIdSanPham(@Param("id") Integer id);

    @Query(value ="select * from the_gioi_den_95.public.thu_muc order by id",nativeQuery = true)
    public List<ThuMucDto> getAllThuMuc();

    @Modifying(clearAutomatically = true)
    @Query(value ="delete from the_gioi_den_95.public.thu_muc where id=:id",nativeQuery = true)
    public Integer deleteByIdThuMuc(@Param("id") Integer id);

    @Query(value ="select case when id_max is null then 1 else id_max+1 end from(select max(id) id_max from the_gioi_den_95.public.anh_san_pham)",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into the_gioi_den_95.public.thu_muc(ten_thu_muc) values(?1)",nativeQuery = true)
    public void insertItem(@Param("tenThuMuc")String tenThuMuc);

}
