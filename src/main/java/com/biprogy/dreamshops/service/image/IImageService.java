package com.biprogy.dreamshops.service.image;

import com.biprogy.dreamshops.dto.ImageDto;
import com.biprogy.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> multipartFile, Long productId);
    void updateImage(MultipartFile multipartFile, Long id);
}
