package com.example.demo.TheGioiDen.TheGioiDenController;

import com.example.demo.TheGioiDen.Request.SanPhamResDto;
import com.example.demo.TheGioiDen.Service.TheGioiDenService;
import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
import com.example.demo.TheGioiDen.entity.KeySearchSanPhamReq;
import com.example.demo.TheGioiDen.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class TheGioiDenRestController {
    @Autowired
    private TheGioiDenService service;
// API của sản phẩm

    //API get all(có phân trang-mặc định 10)
    @GetMapping("/get-all-sp")
    public List<SanPham> getAllSanPham(@RequestParam Integer page,@RequestParam Integer size) {
        return this.service.getAllSanPham(page,size);
    }

    //API get all
    @GetMapping("/get-all-sp-by-danh-muc")
    public List<SanPham> getAllSanPhamByDanhMuc(@RequestParam Integer page,@RequestParam Integer idDanhMuc,@RequestParam Integer size) {
        return this.service.getAllSanPhamByIdDanhMuc(page,idDanhMuc,size);
    }

    // API lấy chi tiết sản phẩm theo id
    @GetMapping("/get-sp-by-id")
    public List<AnhSanPham> getSanPhamById(@RequestParam Integer id) {
        return this.service.getSanPhamById(id);
    }

    //API thêm mới sản phẩm
    @PostMapping("/them-moi-sp")
    public Boolean themMoi(@RequestBody SanPhamResDto sanPham) {
        return this.service.themSanPham(sanPham);
    }

    //API xóa sản phẩm theo ID(Xóa cả ảnh)
    @DeleteMapping("/xoa-san-pham")
    public Integer xoaSanPhamById(@RequestParam Integer id) {
        return this.service.xoaSanPham(id);
    }

    //API xóa từng ảnh theo id của ảnh
    @DeleteMapping("/xoa-anh")
    public boolean xoaAnhById(@RequestParam Integer id) {
        return this.service.xoaAnh(id);
    }

    //API thêm mới ảnh theo id của Sản Phẩm
    @PostMapping("/them-moi-anh-by-id")
    public AnhSanPham themMoiAnhByIdSanPham(@RequestBody AnhSanPham anhSanPham,@RequestParam Integer id) {
        return this.service.themMoiAnhByIdSanPham(id,anhSanPham);
    }

    // API thêm mới danh mục
    @PostMapping("/them-moi-danh-muc")
    public Boolean themMoiDanhMuc(@RequestBody DanhMucSanPham danhMucSanPham) {
        return this.service.themDanhMuc(danhMucSanPham);
    }

    // API sửa danh mục
    @PutMapping("/sua-danh-muc")
    public DanhMucSanPham suaDanhMuc(@RequestBody DanhMucSanPham danhMucSanPham) {
        return this.service.suaDanhMuc(danhMucSanPham);
    }

    // API xóa danh mục
    @DeleteMapping("/xoa-danh-muc")
    public Boolean XoaDanhMucById(@RequestBody Integer id) {
        return this.service.xoaDanhMuc(id);
    }

    // API get all danh mục
    @GetMapping("/get-all-danh-muc")
    public List<DanhMucSanPham> getAllDanhMuc(){
        return this.service.getAllDanhMuc();
    }

    //API search theo tên sản phẩm và danh mục
    @GetMapping("/search")
    public List<SanPham> search(@ModelAttribute KeySearchSanPhamReq keySearchSanPhamReq) {
        return this.service.search(keySearchSanPhamReq);
    }
}
