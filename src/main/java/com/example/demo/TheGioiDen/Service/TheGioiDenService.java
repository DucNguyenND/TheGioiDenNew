package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.Repository.IAnhSanPhamRepository;
import com.example.demo.TheGioiDen.Repository.IDanhMucSanPhamRepository;
import com.example.demo.TheGioiDen.Repository.ITheGioiDenRepository;
import com.example.demo.TheGioiDen.Request.SanPhamResDto;
import com.example.demo.TheGioiDen.entity.AnhSanPham;
import com.example.demo.TheGioiDen.entity.DanhMucSanPham;
import com.example.demo.TheGioiDen.entity.KeySearchSanPhamReq;
import com.example.demo.TheGioiDen.entity.SanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TheGioiDenService {
    @Autowired
    private ITheGioiDenRepository theGioiDenRepository;

    @Autowired
    private IAnhSanPhamRepository anhSanPhamRepository;

    @Autowired
    private IDanhMucSanPhamRepository danhMucSanPhamRepository;

    public List<SanPham> getAllSanPham(Integer page,Integer size){
        return this.theGioiDenRepository.getAllSanPham(size,page);
    }
    public List<SanPham> getAllSanPhamByIdDanhMuc(Integer page,Integer id,Integer size){
        return this.theGioiDenRepository.getAllSanPhamByIdDanhMuc(page,id,size);
    }

    public List<AnhSanPham> getSanPhamById(Integer id){
        return this.anhSanPhamRepository.getAnhSanPhamByIdSanPham(id);
    }

    @Transactional
    public Integer xoaSanPham(Integer id){
        this.theGioiDenRepository.deleteById(id);
        return this.anhSanPhamRepository.deleteByIdSanPham(id);
    }

    @Transactional
    public Boolean themSanPham(SanPhamResDto sanPham){
       this.theGioiDenRepository.insertItem(
                sanPham.getSanPham().getTenSanPham(),
                sanPham.getSanPham().getThuongHieu(),
                sanPham.getSanPham().getMaSp(),
                sanPham.getSanPham().getGiaSp(),
                sanPham.getSanPham().getDienAp(),
                sanPham.getSanPham().getCongSuat(),
                sanPham.getSanPham().getChiSoHoanMau(),
                sanPham.getSanPham().getTuoiTho(),
                sanPham.getSanPham().getAnhSang(),
                sanPham.getSanPham().getKichThuoc(),
                sanPham.getSanPham().getMoTa(),
                sanPham.getSanPham().getLinkAnhChinh(),
                sanPham.getSanPham().getDanhMucSanPhamId()

                );
       Integer id=this.theGioiDenRepository.findByIdMax();
       if (sanPham.getListAnh()!=null){
        List<AnhSanPham>list=sanPham.getListAnh();
        for (int i = 0; i < sanPham.getListAnh().size(); i++) {
            this.anhSanPhamRepository.insertItem(list.get(i).getLinkAnh(),id);
        }}
        return true;
    }

    @Transactional
    public Boolean xoaAnh(Integer id){
        this.anhSanPhamRepository.deleteById(id);
        return true;
    }

    @Transactional
    public AnhSanPham themMoiAnhByIdSanPham(Integer id,AnhSanPham anhSanPham){
        this.anhSanPhamRepository.insertItem(anhSanPham.getLinkAnh(),id);
        return anhSanPham;
    }

    @Transactional
    public Boolean themDanhMuc(DanhMucSanPham danhMucSanPham){
         this.danhMucSanPhamRepository.insertItem(danhMucSanPham.getTenDanhMuc(),danhMucSanPham.getAnhDanhMuc());
         return true;
    }

    @Transactional
    public Boolean xoaDanhMuc(Integer id){
         this.danhMucSanPhamRepository.deleteById(id);
         return true;
    }

    @Transactional
    public DanhMucSanPham suaDanhMuc(DanhMucSanPham danhMucSanPham){
        return this.danhMucSanPhamRepository.save(danhMucSanPham);

    }

    @Transactional
    public void suaSanPham(SanPham sanPham){
         this.theGioiDenRepository.updateItem(
                sanPham.getTenSanPham(),
                sanPham.getThuongHieu(),
                sanPham.getMaSp(),
                sanPham.getGiaSp(),
                sanPham.getDienAp(),
                sanPham.getCongSuat(),
                sanPham.getChiSoHoanMau(),
                sanPham.getTuoiTho(),
                sanPham.getAnhSang(),
                sanPham.getKichThuoc(),
                sanPham.getMoTa(),
                sanPham.getLinkAnhChinh(),
                sanPham.getDanhMucSanPhamId(),
                sanPham.getId()
        );

    }

    public List<DanhMucSanPham> getAllDanhMuc(){
       return this.danhMucSanPhamRepository.findAll();
    }

    public List<SanPham> search(KeySearchSanPhamReq keySearchSanPhamReq){
        return this.theGioiDenRepository.search(keySearchSanPhamReq.getTenSanPham(),keySearchSanPhamReq.getIdDanhMuc(),keySearchSanPhamReq.getSize(),keySearchSanPhamReq.getPage());
    }
}
