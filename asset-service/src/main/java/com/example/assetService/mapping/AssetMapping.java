package com.example.assetService.mapping;

import com.example.assetService.client.AssetServiceClient;
import com.example.assetService.dto.request.AssetRequest;
import com.example.assetService.dto.request.DepreciationRequest;
import com.example.assetService.dto.response.AssetResponse;
import com.example.assetService.dto.response.UserResponse;
import com.example.assetService.model.Asset;
import com.example.assetService.model.AssetType;
import com.example.assetService.model.Brand;
import com.example.assetService.model.Storage;
import com.example.assetService.repository.AccessaryRepository;
import com.example.assetService.repository.AssetTypeRepository;
import com.example.assetService.service.AccesaryService;
import com.example.assetService.service.AssetTypeService;
import com.example.assetService.service.BrandService;
import com.example.assetService.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AssetMapping {
    private final AssetServiceClient assetServiceClient;
    private final AssetTypeService assetTypeService;
    private final BrandService brandService;
    private final StorageService storageService;
    private final AccesaryService accesaryService;
    public AssetResponse getAssetResponse(Asset asset) {
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setAssetId(asset.getAssetId());
        assetResponse.setAssetName(asset.getAssetName());
        assetResponse.setAssetTypeId(asset.getAssetType());
        AssetType assetType = assetTypeService.findAssetTypeById(asset.getAssetType());
        assetResponse.setAssetTypeName(assetType.getAssetName());
        assetResponse.setAmountOfYear(assetType.getAmountOfYear());
        assetResponse.setAssetGroupId(assetType.getAssetGroup().getId());
        assetResponse.setAssetGroup(assetType.getAssetGroup().getName());
        assetResponse.setPrice(asset.getPrice());
        assetResponse.setStatus(asset.getAssetStatus());
        switch (Math.toIntExact(asset.getAssetStatus())){
            case 0: assetResponse.setStatusName("Chưa sử dụng");break;
            case 1: assetResponse.setStatusName("Đang sử dụng");break;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assetResponse.setDateInStored(dateFormat.format(asset.getDateInStored()));
        if(asset.getDateUsed() != null)
            assetResponse.setDateUsed(dateFormat.format(asset.getDateUsed()));
        assetResponse.setUserIdUsed(asset.getUserUsedId());
        assetResponse.setDeptIdUsed(asset.getDeptUsedId());
        assetResponse.setSerial(asset.getSerialNumber());
        Brand brand = brandService.findById(asset.getBrandId());
        assetResponse.setAssetBrandId(asset.getBrandId());
        assetResponse.setAssetBrandName(brand.getBrandName());
        Storage storage = storageService.findById(asset.getStorageId());
        assetResponse.setAssetStorageId(asset.getStorageId());
        assetResponse.setAssetStorageName(storage.getStorageName());
        assetResponse.setAccessaries(accesaryService.findByAssetId(asset.getAssetId()));
        if(asset.getUserUsedId()!=null){
            assetResponse.setUser(assetServiceClient.fetchUser(Long.valueOf(asset.getUserUsedId())));
        }
        return assetResponse;
    }

    public Asset getAsset(AssetRequest assetRequest){
        Asset asset = new Asset();
        asset.setDateInStored(new Date());
        AssetType assetType = assetTypeService.findAssetTypeById(asset.getAssetType());
        asset.setTime(Long.valueOf(assetType.getAmountOfYear()));
        asset.setAssetName(assetRequest.getAssetName());
        asset.setAssetType(assetRequest.getAssetTypeId());
        asset.setAssetStatus(assetRequest.getStatus());
        asset.setPrice(assetRequest.getPrice());
        asset.setSerialNumber(assetRequest.getSerial());
        return asset;
    }
    public Asset updateAsset(Asset asset, Long userId){
        UserResponse userResponse = assetServiceClient.fetchUser(Long.valueOf(userId));
        if(userResponse == null)
            return null;
        asset.setAssetStatus(Long.valueOf(1));
        asset.setDateUsed(new Date());
        asset.setUserUsedId(userId);
        asset.setDeptUsedId(Long.valueOf(userResponse.getDept().getId()));
        return asset;
    }
}
