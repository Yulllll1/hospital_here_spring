package com.team5.hospital_here.bookMark.entity;

public class BookMarkMapper {

    public static BookMarkEntity toBookMarkEntity(BookMarkDTO bookMarkDTO)
    {
        BookMarkEntity bookMarkEntity = new BookMarkEntity();
        bookMarkEntity.setId(bookMarkDTO.getId());


        return bookMarkEntity;
    }

    public static BookMarkDTO toBookMarkDTO(BookMarkEntity bookMarkEntity)
    {
        BookMarkDTO bookMarkDTO = new BookMarkDTO();
        bookMarkDTO.setId(bookMarkEntity.getId());
        bookMarkDTO.setUserId(bookMarkEntity.getUser().getId());
        bookMarkDTO.setHospitalId(bookMarkEntity.getHospital().getId());
        return bookMarkDTO;

    }

}
