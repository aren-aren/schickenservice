package com.dongil.schickenservice.commons.files;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Insert("""
    INSERT INTO attach (id, origin_name, url, extension, parent_id, tbl_id, name)
    VALUES (nextval(seq3), #{originName}, #{url}, #{extension}, #{parentId}, #{tblId}, #{name})
    """)
    int insertFile(FileVO fileVO);

    @Delete("""
    DELETE FROM attach WHERE id = #{id}
    """)
    int deleteFile(String id);

    @Select("""
    SELECT * FROM attach WHERE id = #{id}
    """)
    FileVO selectFile(FileVO fileVO);
}
