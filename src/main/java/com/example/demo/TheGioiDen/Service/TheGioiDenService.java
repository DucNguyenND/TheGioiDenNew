package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.Repository.*;
import com.example.demo.TheGioiDen.Request.SanPhamResDto;
import com.example.demo.TheGioiDen.Res.ThuMucRestDto;
import com.example.demo.TheGioiDen.Res.TongMucRestDto;
import com.example.demo.TheGioiDen.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheGioiDenService {
    @Autowired
    private ITheGioiDenRepository theGioiDenRepository;

    @Autowired
    private IAnhSanPhamRepository anhSanPhamRepository;

    @Autowired
    private IDanhMucSanPhamRepository danhMucSanPhamRepository;

    @Autowired
    private IThuMucRepository thuMucRepository;

    @Autowired
    private IBannerRepository bannerRepository;

    @Autowired
    private ITongMucRepository tongMucRepository;


    public List<SanPham> getAllSanPham(Integer page, Integer size) {
        page=page*size;
        return this.theGioiDenRepository.getAllSanPham(size, page);
    }

    public List<SanPham> getAllSanPhamByIdDanhMuc(Integer page, Integer id, Integer size) {
        page=page*size;
        return this.theGioiDenRepository.getAllSanPhamByIdDanhMuc( id, size,page);
    }

    public List<AnhSanPham> getSanPhamById(Integer id) {
        return this.anhSanPhamRepository.getAnhSanPhamByIdSanPham(id);
    }

    public SanPhamResDto findByID(Integer id) {
        SanPhamResDto sanPhamResDto = new SanPhamResDto();
        sanPhamResDto.setSanPham(this.theGioiDenRepository.getSanPhamById(id));
        sanPhamResDto.setListAnh(this.anhSanPhamRepository.getAnhSanPhamByIdSanPham(id));
        return sanPhamResDto;
    }

    @Transactional
    public Integer xoaSanPham(Integer id) {
        this.theGioiDenRepository.deleteByIdSanPham(id);
        return this.anhSanPhamRepository.deleteByIdSanPham(id);
    }

    @Transactional
    public Boolean themSanPham(SanPhamResDto sanPham) {
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
                sanPham.getSanPham().getDanhMucSanPhamId(),
                sanPham.getSanPham().getHieuSuat(),
                sanPham.getSanPham().getGocChieu(),
                sanPham.getSanPham().getMucDoUuTien()

        );
        Integer id = this.theGioiDenRepository.findByIdMax();
        if (sanPham.getListAnh() != null) {
            List<AnhSanPham> list = sanPham.getListAnh();
            for (int i = 0; i < sanPham.getListAnh().size(); i++) {
                this.anhSanPhamRepository.insertItem(list.get(i).getLinkAnh(), id);
            }
        }
        return true;
    }

    @Transactional
    public Boolean xoaAnh(Integer id) {
        this.anhSanPhamRepository.deleteById(id);
        return true;
    }

    @Transactional
    public AnhSanPham themMoiAnhByIdSanPham(Integer id, AnhSanPham anhSanPham) {
        this.anhSanPhamRepository.insertItem(anhSanPham.getLinkAnh(), id);
        return anhSanPham;
    }

    @Transactional
    public Boolean themDanhMuc(DanhMucSanPham danhMucSanPham) {
        this.danhMucSanPhamRepository.insertItem(danhMucSanPham.getTenDanhMuc(), danhMucSanPham.getAnhDanhMuc(),danhMucSanPham.getIdThuMuc(),danhMucSanPham.getMucDoUuTien());
        return true;
    }

    @Transactional
    public Boolean xoaDanhMuc(Integer id) {
        this.danhMucSanPhamRepository.deleteById(id);
        return true;
    }

    @Transactional
    public DanhMucSanPham suaDanhMuc(DanhMucSanPham danhMucSanPham) {
        return this.danhMucSanPhamRepository.save(danhMucSanPham);

    }

    @Transactional
    public void suaSanPham(SanPhamResDto objInput) {
        this.theGioiDenRepository.updateItem(
                objInput.getSanPham().getTenSanPham(),
                objInput.getSanPham().getThuongHieu(),
                objInput.getSanPham().getMaSp(),
                objInput.getSanPham().getGiaSp(),
                objInput.getSanPham().getDienAp(),
                objInput.getSanPham().getCongSuat(),
                objInput.getSanPham().getChiSoHoanMau(),
                objInput.getSanPham().getTuoiTho(),
                objInput.getSanPham().getAnhSang(),
                objInput.getSanPham().getKichThuoc(),
                objInput.getSanPham().getMoTa(),
                objInput.getSanPham().getLinkAnhChinh(),
                objInput.getSanPham().getDanhMucSanPhamId(),
                objInput.getSanPham().getId(),
                objInput.getSanPham().getHieuSuat(),
                objInput.getSanPham().getGocChieu(),
                objInput.getSanPham().getMucDoUuTien()
        );
        this.anhSanPhamRepository.deleteByIdSanPham(objInput.getSanPham().getId());
        if ( objInput.getListAnh()!=null){
            for (int i = 0; i < objInput.getListAnh().size(); i++) {
                this.themMoiAnhByIdSanPham(objInput.getSanPham().getId(), objInput.getListAnh().get(i));
            }

        }

    }

    public List<DanhMucSanPham> getAllDanhMuc() {
        return this.danhMucSanPhamRepository.findAllDanhMuc();
    }

    public List<SanPham> search(KeySearchSanPhamReq keySearchSanPhamReq) {
        keySearchSanPhamReq.setPage(keySearchSanPhamReq.getPage()*keySearchSanPhamReq.getSize());
        return this.theGioiDenRepository.search(keySearchSanPhamReq.getTenSanPham(), keySearchSanPhamReq.getIdDanhMuc(), keySearchSanPhamReq.getSize(), keySearchSanPhamReq.getPage());
    }

    @Transactional
    public void themThuMuc(String tenThuMuc,Integer mucDoUuTien,Integer idTongMuc){
        this.thuMucRepository.insertItem(tenThuMuc,mucDoUuTien,idTongMuc);
    }

    @Transactional
    public void suaThuMuc(ThuMucDto thuMucDto){
        this.thuMucRepository.save(thuMucDto);
    }

    @Transactional
    public void xoaThuMuc(Integer id){
        this.thuMucRepository.deleteByIdThuMuc(id);
    }

    public List<ThuMucDto> getAllThuMuc(){
        return this.thuMucRepository.getAllThuMuc();
    }

    public List<ThuMucRestDto> getAllThuMucDanhMuc(){
        List<ThuMucDto> thuMucRestDtoList=this.thuMucRepository.getAllThuMuc();
        List<ThuMucRestDto> list=new ArrayList<>();
        if (thuMucRestDtoList!=null){
            for (int i = 0;i<thuMucRestDtoList.size() ; i++) {
                ThuMucRestDto thuMucRestDto=new ThuMucRestDto();
                thuMucRestDto.setTenThuMuc(thuMucRestDtoList.get(i).getTenThuMuc());
                thuMucRestDto.setId(thuMucRestDtoList.get(i).getId());
                thuMucRestDto.setMucDoUuTien(thuMucRestDtoList.get(i).getMucDoUuTien());
                thuMucRestDto.setListDanhMuc(this.danhMucSanPhamRepository.findByIdThuMuc(thuMucRestDto.getId()));
                list.add(thuMucRestDto);
            }
        }
        return list;
    }

    @Transactional
    public void themMoiBanner(List<String> listAnhBanner){
        for (int i = 0; i < listAnhBanner.size(); i++) {
            this.bannerRepository.insertItem(listAnhBanner.get(i));
        }
    }

    @Transactional
    public Integer xoaBanner(Integer id){
        return this.bannerRepository.deleteByIdBanner(id);
    }

    public List<BannerDto> getAllBanner(){
        return this.bannerRepository.getAllBanner();
    }

    @Transactional
    public void mucDoUuTienSanPham(List<SanPham> sanPham){
        for (int i = 0; i < sanPham.size(); i++) {
            this.theGioiDenRepository.mucDoUuTien(sanPham.get(i).getMucDoUuTien(),sanPham.get(i).getId());
        }
    }

    @Transactional
    public void mucDoUuTienDanhMucSanPham(List<DanhMucSanPham> danhMucSanPhams){
        for (int i = 0; i < danhMucSanPhams.size(); i++) {
            this.danhMucSanPhamRepository.mucDoUuTien(danhMucSanPhams.get(i).getMucDoUuTien(),danhMucSanPhams.get(i).getId());
        }
    }

    @Transactional
    public void mucDoUuTienThuMuc(List<ThuMucDto> thuMucDtos){
        for (int i = 0; i < thuMucDtos.size(); i++) {
            this.thuMucRepository.mucDoUuTien(thuMucDtos.get(i).getMucDoUuTien(),thuMucDtos.get(i).getId());
        }
    }

    // Tổng mục

    @Transactional
    public void themTongMuc(String tenTongMuc,Integer mucDoUuTien){
        this.tongMucRepository.insertItem(tenTongMuc,mucDoUuTien);
    }

    @Transactional
    public void suaTongMuc(TongMucDto tongMucDto){
        this.tongMucRepository.save(tongMucDto);
    }

    @Transactional
    public void xoaTongMuc(Integer id){
        this.tongMucRepository.deleteByIdTongMuc(id);
    }

    public List<TongMucDto> getAllTongMuc(){
        return this.tongMucRepository.getAllTongMuc();
    }

    public List<TongMucRestDto> getAllTonngMucChild(){
        List<ThuMucRestDto> thuMucRestDtoList=this.getAllThuMucDanhMuc();
        List<TongMucDto> list=this.tongMucRepository.getAllTongMuc();
        List<TongMucRestDto> tongMuc=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TongMucRestDto tongMucRestDto=new TongMucRestDto();
            tongMucRestDto.setTenTongMuc(list.get(i).getTenTongMuc());
            tongMucRestDto.setId(list.get(i).getId());
            tongMucRestDto.setMucDoUuTien(list.get(i).getMucDoUuTien());
            List<ThuMucRestDto> thuMucRestDto=new ArrayList<>();
            for (int j = 0; j < thuMucRestDtoList.size(); j++) {
                if (tongMucRestDto.getId()==thuMucRestDtoList.get(j).getId()){
                    thuMucRestDto.add(thuMucRestDtoList.get(j));
                }
            }
            tongMucRestDto.setListThuMuc(thuMucRestDto);
            tongMuc.add(tongMucRestDto);
        }
        return tongMuc;
    }

    @Transactional
    public void mucDoUuTienTongMuc(List<TongMucDto> TongMucDto){
        for (int i = 0; i < TongMucDto.size(); i++) {
            this.tongMucRepository.mucDoUuTien(TongMucDto.get(i).getMucDoUuTien(),TongMucDto.get(i).getId());
        }
    }

}
