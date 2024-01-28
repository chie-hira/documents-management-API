package com.files.management.mapper;

import com.files.management.entity.Location;
import java.util.Optional;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LocationMapper {

  @Insert("INSERT INTO locations (location, shelf_number) VALUE (#{location}, #{shelfNumber})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Location location);

  @Update("UPDATE locations SET location = #{location}, shelf_number = #{shelfNumber} WHERE id = #{id}")
  void update(Location location);

  @Select("SELECT * FROM locations WHERE id = #{id}")
  Optional<Location> findById(int locationId);

  @Select("SELECT COUNT(*) FROM locations WHERE location = #{locationName} AND shelf_number = #{shelfNumber}")
  boolean isMaterialUnique(@Param("locationName") String locationName,
      @Param("shelfNumber") String shelfNumber);

  @Select("SELECT COUNT(*) FROM locations")
  int getCount();
}
