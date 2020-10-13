package com.lca.customer.controller;

import com.lca.customer.model.Image;
import com.lca.customer.service.ImageService;
import com.lca.customer.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            Image image = imageService.store(file);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", image.getId());
            result.put("name", image.getName());
            result.put("type", image.getType());
            return  ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("error", "It has been an error uploading the file.");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable long id) {
        Image image = imageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }
}
