package com.moxie.cloud.services.server.mapper;

import com.moxie.cloud.services.server.entity.CtccNumber;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2019/1/25
 */

@Mapper
public interface CtccNumberMapper {

    @Insert({"insert into ctcc_number (number,company,purpose,releaseTime,createTime,lastModifyTime) values (#{number},#{company},#{purpose},#{releaseTime},#{createTime},#{lastModifyTime})"})
    Boolean insertCtccNumber(CtccNumber ctccNumber);


    @Select({"select * from ctcc_number where number= #{number}"})
    CtccNumber getCtccNumberByNumber(String number);

    @Update({"update ctcc_number set number=#{number},company=#{company},purpose=#{purpose},releaseTime=#{releaseTime},createTime=#{createTime},lastModifyTime=#{lastModifyTime} where id = #{id}"})
    Boolean updateCtccnumberById(CtccNumber ctccNumber);

}
