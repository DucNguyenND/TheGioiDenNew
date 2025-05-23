package com.example.demo.TheGioiDen.TheGioiDenController;

import com.example.demo.TheGioiDen.Request.SanPhamResDto;
import com.example.demo.TheGioiDen.Request.TrangChuDto;
import com.example.demo.TheGioiDen.Res.ThuMucRestDto;
import com.example.demo.TheGioiDen.Res.TongMucRestDto;
import com.example.demo.TheGioiDen.Service.TheGioiDenService;
import com.example.demo.TheGioiDen.entity.*;
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

    // API lấy ảnh sản phẩm theo id
    @GetMapping("/get-sp-by-id")
    public List<AnhSanPham> getSanPhamById(@RequestParam Integer id) {
        return this.service.getSanPhamById(id);
    }

    //API lấy thông tin chi tiết
    @GetMapping("/detail")
    public SanPhamResDto detail(@RequestParam Integer id) {
        return this.service.findByID(id);
    }

    //API thêm mới sản phẩm
    @PostMapping("/them-moi-sp")
    public Boolean themMoi(@RequestBody SanPhamResDto sanPham) {
        return this.service.themSanPham(sanPham);
    }

    //API thêm mới list sản phẩm
    @PostMapping("/them-moi-list-sp")
    public Boolean themMoi(@RequestBody List<SanPhamResDto> sanPham) {
        return this.service.themListSp(sanPham);
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

    // API sửa sản phẩm
    @PutMapping("/sua-san-pham")
    public void suaSanPham(@RequestBody SanPhamResDto sanPham) {
        this.service.suaSanPham(sanPham);
    }

//API danh mục

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
    public Boolean XoaDanhMucById(@RequestParam Integer id) {
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

// API thư mục lớn

    // API get all danh mục by thư mục
    @GetMapping("/get-all-danh-muc-by-thu-muc")
    public List<ThuMucRestDto> getAllDanhMucByThuMuc() {
        return this.service.getAllThuMucDanhMuc();
    }

    // API thêm thư mục
    @PostMapping("/them-thu-muc")
    public void themThuMuc(@RequestBody ThuMucDto thuMucDto) {
        this.service.themThuMuc(thuMucDto.getTenThuMuc(),thuMucDto.getMucDoUuTien(),thuMucDto.getIdTongMuc());
    }

    // API sửa thư mục
    @PutMapping("/sua-thu-muc")
    public void suaThuMuc(@RequestBody ThuMucDto thuMucDto) {
        this.service.suaThuMuc(thuMucDto);
    }

    // API xóa thư mục
    @DeleteMapping("/xoa-thu-muc")
    public void xoaThuMuc(@RequestParam Integer id) {
        this.service.xoaThuMuc(id);
    }

    // API get all thư mục
    @GetMapping("/get-all-thu-muc")
    public List<ThuMucDto> getAllThuMuc() {
       return this.service.getAllThuMuc();
    }

// API ảnh khuyến mại

    // API thêm mới banner(ảnh khuyến mại)
    @PostMapping("/them-moi-banner")
    public void themMoiBanner(@RequestBody List<String> listAnhBanner){
        this.service.themMoiBanner(listAnhBanner);
    }

    // API get all banner
    @GetMapping("/get-all-banner")
    public List<BannerDto> getAllBanner() {
        return this.service.getAllBanner();
    }

    // API xóa banner
    @DeleteMapping("/xoa-banner")
    public Integer xoaBanner(@RequestParam Integer id) {
        return this.service.xoaBanner(id);
    }

// API sắp xếp thứ tự ưu tiên

    //API sắp xếp thứ tự ưu tiên của sản phẩm
    @PutMapping("/uu-tien/san-pham")
    public void uuTienSanPham(@RequestBody List<SanPham> sanPham){
        this.service.mucDoUuTienSanPham(sanPham);
    }

    @PutMapping("/uu-tien/danh-muc")
    public void uuTienDanhMuc(@RequestBody List<DanhMucSanPham> danhMucSanPham){
        this.service.mucDoUuTienDanhMucSanPham(danhMucSanPham);
    }

    @PutMapping("/uu-tien/thu-muc")
    public void uuTienThuMuc(@RequestBody List<ThuMucDto> thuMucDtos){
        this.service.mucDoUuTienThuMuc(thuMucDtos);
    }

    @PutMapping("/uu-tien/tong-muc")
    public void uuTienTongMuc(@RequestBody List<TongMucDto> tongMucDto){
        this.service.mucDoUuTienTongMuc(tongMucDto);
    }

// API Tổng mục

    // API get all Tổng mục
    @GetMapping("/get-all-tong-muc")
    public List<TongMucDto> getAllTongMuc() {
        return this.service.getAllTongMuc();
    }

    // API get all Tổng mục và các thư mục con
    @GetMapping("/get-all-tong-muc-child")
    public List<TongMucRestDto> getAllTongMucChild() {
        return this.service.getAllTonngMucChild();
    }

    // API thêm Tổng mục
    @PostMapping("/them-tong-muc")
    public void themTongMuc(@RequestBody TongMucDto tongMucDto) {
        this.service.themTongMuc(tongMucDto.getTenTongMuc(),tongMucDto.getMucDoUuTien());
    }

    // API sửa Tổng mục
    @PutMapping("/sua-tong-muc")
    public void suaTongMuc(@RequestBody TongMucDto tongMucDto) {
        this.service.suaTongMuc(tongMucDto);
    }

    // API xóa Tổng mục
    @DeleteMapping("/xoa-tong-muc")
    public void xoaTonguMuc(@RequestParam Integer id) {
        this.service.xoaTongMuc(id);
    }

// API trang chủ
    @GetMapping("/trang-chu")
    public List<TrangChuDto> getTrangChu() {
    return this.service.getTrangChu();
}

}
