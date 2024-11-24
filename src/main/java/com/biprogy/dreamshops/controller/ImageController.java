package com.biprogy.dreamshops.controller;

import com.biprogy.dreamshops.dto.ImageDto;
import com.biprogy.dreamshops.exceptions.ResourceNotFoundException;
import com.biprogy.dreamshops.model.Image;
import com.biprogy.dreamshops.response.ApiResponse;
import com.biprogy.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> multipartFiles,@RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = iImageService.saveImage(multipartFiles, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource byteArrayResource = null;
        try {
            byteArrayResource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +  image.getFilename() + "\"")
                .body(byteArrayResource);
    }


    @PutMapping("/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile multipartFile) {
        try {
            Image image = iImageService.getImageById(imageId);
            if(image != null) {
                iImageService.updateImage(multipartFile, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = iImageService.getImageById(imageId);
            if (image != null) {
                iImageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
