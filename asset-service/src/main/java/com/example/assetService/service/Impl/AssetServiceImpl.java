package com.example.assetService.service.Impl;

import com.example.assetService.client.AssetServiceClient;
import com.example.assetService.dto.response.UserResponse;
import com.example.assetService.model.Asset;
import com.example.assetService.repository.AssetRepository;
import com.example.assetService.repository.AssetTypeRepository;
import com.example.assetService.service.AssetService;
import com.example.assetService.service.ExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;
    private final AssetServiceClient assetServiceClient;
    private final ExcelUploadService excelUploadService;

    public void saveAssetsToDatabase(MultipartFile file){
        if(ExcelUploadService.isValidExcelFile(file)){
            try {
                List<Asset> assets = excelUploadService.getAssetsDataFromExcel(file.getInputStream());
                this.assetRepository.saveAll(assets);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public Page<Asset> getAssets(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sort));
        return assetRepository.findAll(pageable);
    }

    @Override
    public Asset findAssetById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        if(asset.isPresent())
            return asset.get();
        return null;
    }

    @Override
    public Page<Asset> findAssetByDeptId(Long deptId, int page,int size,String sort){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return assetRepository.findByDeptUsedId(deptId,pageable);
    }

    @Override
    public Page<Asset> findAssetByUserId(Long userId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return assetRepository.findByUserUsedId(userId,pageable);
    }

    @Override
    public Page<Asset> findAssetByAssetStatus(Long assetStatus, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
       return assetRepository.findByAssetStatus(assetStatus,pageable);
    }

    @Override
    public Page<Asset> findAssetByDate(Date fromDate, Date toDate, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size);
        return assetRepository.findByStoredDate1(fromDate,toDate,pageable);
    }

    @Override
    public Page<Asset> findAssetByName(String name, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size);
        name = covertToString(name.toLowerCase());
        String[] parts = name.split(" ");
        String keyword = "%(";
        for(String key : parts){
            keyword+=key+"|";
        }
        keyword+=")%";
        return assetRepository.findByKeyword(keyword,pageable);
    }

    @Override
    public Page<Asset> filterAssets(String name, Long deptId, Long userId, Long status,Long assetType, Date fromDate, Date toDate, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(sort));
        String keyword = "%(";
        name = covertToString(name.toLowerCase());
        String[] parts = name.split(" ");
        for(String key : parts){
            keyword+=key+"|";
        }
        keyword+=")%";
        return assetRepository.filterAssets(keyword,deptId,userId, assetType,fromDate,toDate,status,pageable);
    }

    public static String covertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createAsset(Asset asset) {
        if(assetRepository.save(asset)!=null)
            return true;
        return false;
    }


    @Override
    public UserResponse getAssets1() {
        return assetServiceClient.fetchUser(Long.valueOf(1));
    }

    @Override
    public Page<Asset> findAssetByAssetType(Long assetTypeId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return assetRepository.findByAssetType(assetTypeId,pageable);
    }
}
