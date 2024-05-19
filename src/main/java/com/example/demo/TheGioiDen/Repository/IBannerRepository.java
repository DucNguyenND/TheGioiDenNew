package com.example.demo.TheGioiDen.Repository;

import com.example.demo.TheGioiDen.entity.BannerDto;
import com.example.demo.TheGioiDen.entity.ThuMucDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBannerRepository extends JpaRepository<BannerDto, Integer> {
    @Query(value ="select * from the_gioi_den_95.public.banner",nativeQuery = true)
    public List<BannerDto> getAllBanner();

    @Modifying(clearAutomatically = true)
    @Query(value ="delete from the_gioi_den_95.public.banner where id=:id",nativeQuery = true)
    public Integer deleteByIdBanner(@Param("id") Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value ="insert into the_gioi_den_95.public.banner(anh_banner) values(?1)",nativeQuery = true)
    public void insertItem(@Param("anhBanner")String anhBanner);

}
