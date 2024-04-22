package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnhSanPhamRepository extends JpaRepository<AnhSanPham, Integer> {
    @Query(value ="select * from the_gioi_den_95.public.anh_san_pham where san_pham_id=:id",nativeQuery = true)
    public List<AnhSanPham> getAnhSanPhamByIdSanPham(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value ="delete from the_gioi_den_95.public.anh_san_pham where san_pham_id=:id",nativeQuery = true)
    public Integer deleteByIdSanPham(@Param("id") Integer id);

    @Query(value ="select case when id_max is null then 1 else id_max+1 end from(select max(id) id_max from the_gioi_den_95.public.anh_san_pham)",nativeQuery = true)
    public Integer findByIdMax();

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into the_gioi_den_95.public.anh_san_pham(link_anh,san_pham_id) values(?1,?2)",nativeQuery = true)
    public void insertItem(@Param("linkAnhSanPham")String linkAnh,@Param("sanPhamId") Integer sanPhamId);

}
