package com.example.demo.TheGioiDen.Service;

import com.example.demo.TheGioiDen.Repository.*;
import com.example.demo.TheGioiDen.Request.SanPhamResDto;
import com.example.demo.TheGioiDen.Request.TrangChuDto;
import com.example.demo.TheGioiDen.Res.ThuMucRestDto;
import com.example.demo.TheGioiDen.Res.TongMucRestDto;
import com.example.demo.TheGioiDen.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private IPhanLoaiRepository phanLoaiRepository;

    @Autowired
    private IProductRepository productRepository;


    public List<SanPham> getAllSanPham(Integer page, Integer size) {
        page=page*size;
        return this.theGioiDenRepository.getAllSanPham(size, page);
    }
    public SanPham getSanPhamById(int id){
        Optional<SanPham> sanPham = theGioiDenRepository.findById(id);
        return sanPham.orElse(null);
    }

    public Product getSanPhamByIdReturnProduct(int id){
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return null;
        }
        Product sp = product.get();
        return sp;
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
        sanPhamResDto.setIdDanhMuc(sanPhamResDto.getSanPham().getDanhMucSanPhamId());
        DanhMucSanPham danhMucSanPham = this.danhMucSanPhamRepository.findById(sanPhamResDto.getSanPham().getDanhMucSanPhamId())
                .orElse(null);
        sanPhamResDto.setTenDanhMuc(danhMucSanPham==null?null:danhMucSanPham.getTenDanhMuc());
        ThuMucDto thuMucDto=this.thuMucRepository.getThuMucById(danhMucSanPham.getIdThuMuc());
        sanPhamResDto.setIdThuMuc(thuMucDto==null?null:thuMucDto.getId());
        sanPhamResDto.setTenThuMuc(thuMucDto==null?null:thuMucDto.getTenThuMuc());
        TongMucDto tongMucDto=this.tongMucRepository.getTongMucById(thuMucDto==null?null:thuMucDto.getIdTongMuc());
        sanPhamResDto.setIdTongMuc(tongMucDto==null?null:tongMucDto.getId());
        sanPhamResDto.setTenTongMuc(tongMucDto==null?null:tongMucDto.getTenTongMuc());
        sanPhamResDto.setListAnh(this.anhSanPhamRepository.getAnhSanPhamByIdSanPham(id));
        sanPhamResDto.setListCongSuat(this.phanLoaiRepository.findCongSuatByIdSanPham(id));
        sanPhamResDto.setListKichThuoc(this.phanLoaiRepository.findKichThuocByIdSanPham(id));
        sanPhamResDto.setListDynamic(this.phanLoaiRepository.findDynamicByIdSanPham(id));
        return sanPhamResDto;
    }
    public SanPham findById(Integer id){
        return theGioiDenRepository.findById(id).get();
    }

    @Transactional
    public Integer xoaSanPham(Integer id) {
        this.theGioiDenRepository.deleteById(id);
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
                sanPham.getSanPham().getMucDoUuTien(),
                sanPham.getThongSo()

        );
        Integer id = this.theGioiDenRepository.findByIdMax();
        if (sanPham.getListAnh() != null) {
            List<AnhSanPham> list = sanPham.getListAnh();
            for (AnhSanPham anh : list) {
                this.anhSanPhamRepository.insertItem(anh.getLinkAnh(), id);
            }
        }

        if (sanPham.getListCongSuat() != null) {
            List<PhanLoaiDto> list = sanPham.getListCongSuat();
            for (PhanLoaiDto phanLoai : list) {
                this.phanLoaiRepository.insertItem("Công suất ",phanLoai.getGroupValue(),1,phanLoai.getGiaTien(),id);
            }
        }

        if (sanPham.getListKichThuoc() != null) {
            List<PhanLoaiDto> list = sanPham.getListKichThuoc();
            for (PhanLoaiDto phanLoai : list) {
                this.phanLoaiRepository.insertItem("Kích thước ",phanLoai.getGroupValue(),2,null,id);
            }
        }

        if (sanPham.getListDynamic() != null) {
            List<PhanLoaiDto> list = sanPham.getListDynamic();
            for (PhanLoaiDto phanLoai : list) {
                this.phanLoaiRepository.insertItem(phanLoai.getTenPhanLoai(),phanLoai.getGroupValue(),3,null,id);
            }
        }
        return true;
    }

    @Transactional
    public Boolean themListSp(List<SanPhamResDto> listSanPham){
        listSanPham.stream().forEach(sanPhamResDto -> this.themSanPham(sanPhamResDto));
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
                objInput.getSanPham().getMucDoUuTien(),
                objInput.getSanPham().getThongSo()
        );
        this.anhSanPhamRepository.deleteByIdSanPham(objInput.getSanPham().getId());
        this.phanLoaiRepository.deleteByIdSanPham(objInput.getSanPham().getId());
        if ( objInput.getListAnh()!=null){
            for (int i = 0; i < objInput.getListAnh().size(); i++) {
                this.themMoiAnhByIdSanPham(objInput.getSanPham().getId(), objInput.getListAnh().get(i));
            }

        }
        if (objInput.getListCongSuat() != null) {
            List<PhanLoaiDto> list = objInput.getListCongSuat();
            for (int i = 0; i < list.size(); i++) {
                this.phanLoaiRepository.insertItem("Công suất ",list.get(i).getGroupValue(),1,list.get(i).getGiaTien(),objInput.getSanPham().getId());
            }
        }

        if (objInput.getListKichThuoc() != null) {
            List<PhanLoaiDto> list = objInput.getListKichThuoc();
            for (int i = 0; i < list.size(); i++) {
                this.phanLoaiRepository.insertItem("Kích thước ",list.get(i).getGroupValue(),2,null,objInput.getSanPham().getId());
            }
        }

        if (objInput.getListDynamic() != null) {
            List<PhanLoaiDto> list = objInput.getListDynamic();
            for (int i = 0; i < list.size(); i++) {
                this.phanLoaiRepository.insertItem(list.get(i).getTenPhanLoai(),list.get(i).getGroupValue(),3,null,objInput.getSanPham().getId());
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


    public List<TrangChuDto> getTrangChu() {
        List<DanhMucSanPham> list=this.danhMucSanPhamRepository.findDanhMucTrangChu();
        List<TrangChuDto> listTrangChu=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TrangChuDto trangChuDto=new TrangChuDto();
            trangChuDto.setDanhMucSanPham(list.get(i));
            List<SanPham> listSanPham=this.theGioiDenRepository.getAllSanPhamByIdDanhMuc(list.get(i).getId());
            trangChuDto.setListSanPham(listSanPham);
            listTrangChu.add(trangChuDto);
        }
        return listTrangChu;
    }

}
