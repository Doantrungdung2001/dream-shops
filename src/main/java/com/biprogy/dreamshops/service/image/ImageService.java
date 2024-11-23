package com.biprogy.dreamshops.service.image;

import com.biprogy.dreamshops.dto.ImageDto;
import com.biprogy.dreamshops.exceptions.ResourceNotFoundException;
import com.biprogy.dreamshops.model.Image;
import com.biprogy.dreamshops.model.Product;
import com.biprogy.dreamshops.repository.ImageRepository;
import com.biprogy.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;

    private final IProductService iProductService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id:" +id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("No image found with id:" +id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> multipartFile, Long productId) {
        Product product = iProductService.getProductById(productId);
        List<ImageDto> saveImageDto = new ArrayList<>();

        for (MultipartFile file: multipartFile) {
            try {
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDowloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(saveImage.getId());
                imageDto.setImageName(saveImage.getFilename());
                imageDto.setDownloadUrl(saveImage.getDowloadUrl());
                saveImageDto.add(imageDto);
            } catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        return saveImageDto;
    }

    @Override
    public void updateImage(MultipartFile multipartFile, Long id) {
        Image image = getImageById(id);

        try {
            image.setFilename(multipartFile.getOriginalFilename());
            image.setFilename(multipartFile.getOriginalFilename());
            image.setImage(new SerialBlob(multipartFile.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
