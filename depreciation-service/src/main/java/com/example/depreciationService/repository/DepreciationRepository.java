package com.example.depreciationService.repository;

import com.example.depreciationService.model.Depreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DepreciationRepository extends JpaRepository<Depreciation,Long> {
    List<Depreciation> findByAssetId(Long assetId);
    @Query(value = "SELECT * FROM public.depreciation WHERE public.depreciation.to_date IS NULL AND asset_id=?1",
            countQuery = "SELECT * FROM public.depreciation WHERE public.depreciation.to_date IS NULL AND asset_id=?1",
            nativeQuery = true)
    Optional<Depreciation> findDepreciationIsNull(Long assetId);
    @Query(value = "SELECT * FROM public.depreciation WHERE public.depreciation.to_date IS NULL",
            countQuery = "SELECT * FROM public.depreciation WHERE public.depreciation.to_date IS NULL",
            nativeQuery = true)
    List<Depreciation> getAllDepreciationNoToDate();
    @Query(value = "SELECT * FROM public.depreciation WHERE (from_date >= ?1 AND from_date <=?2) OR to_date IS NULL",nativeQuery = true)
    List<Depreciation> getDepreciationByFromDateAndToDate(Date fromDate, Date toDate);
//    @Query
//    List<Object> countDepreciation();

}
