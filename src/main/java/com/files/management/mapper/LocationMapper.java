package com.files.management.mapper;

import com.files.management.entity.Location;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LocationMapper {
    @Insert("INSERT INTO locations (location, shelf_number) VALUE (#{location}, #{shelfNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Location location);

    @Select("SELECT COUNT(*) FROM locations WHERE location = #{locationName} AND shelf_number = #{shelfNumber}")
    boolean isMaterialUnique(@Param("locationName") String locationName, @Param("shelfNumber") String shelfNumber);
}
