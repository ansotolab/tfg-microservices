package com.lca.customer.service;

import com.lca.customer.exception.ImageExceededSize;
import com.lca.customer.exception.ImageInvalidType;
import com.lca.customer.exception.ImageNotFound;
import com.lca.customer.model.Image;
import com.lca.customer.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    public void shouldSaveJPGImageSuccessfully() {
        byte [] data = new byte[2 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, data);
        Image image = new Image("image", "jpg", data);

        given(imageRepository.save(any(Image.class))).willReturn(image);

        assertDoesNotThrow(() -> {
            Image imageReal = imageService.store(file);
            assertThat(imageReal).isNotNull();
        });
    }

    @Test
    public void shouldSaveGIFImageSuccessfully() {
        byte [] data = new byte[2 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("image", "image.gif", MediaType.IMAGE_GIF_VALUE, data);
        Image image = new Image("image", "gif", data);

        given(imageRepository.save(any(Image.class))).willReturn(image);

        assertDoesNotThrow(() -> {
            Image imageReal = imageService.store(file);
            assertThat(imageReal).isNotNull();
        });
    }

    @Test
    public void shouldSavePNGImageSuccessfully() {
        byte [] data = new byte[2 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("image", "image.png", MediaType.IMAGE_PNG_VALUE, data);
        Image image = new Image("image", "png", data);

        given(imageRepository.save(any(Image.class))).willReturn(image);

        assertDoesNotThrow(() -> {
            Image imageReal = imageService.store(file);
            assertThat(imageReal).isNotNull();
        });
    }

    @Test
    public void shouldThrowErrorWhenSaveWithSizeExceeded() {
        byte [] data = new byte[4 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, data);

        assertThrows(ImageExceededSize.class, () -> {
            Image image = imageService.store(file);
        });
    }

    @Test
    public void shouldThrowErrorWhenSaveWithInvalidType() {
        byte [] data = new byte[2 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("image", "image.txt", MediaType.TEXT_PLAIN_VALUE, data);

        assertThrows(ImageInvalidType.class, () -> {
            Image image = imageService.store(file);
        });
    }

    @Test
    public void shouldGetImageSuccessfully() {
        byte [] data = new byte[2 * 1024 * 1024];
        Image image = new Image("image", "gif", data);

        given(imageRepository.findById(anyLong())).willReturn(Optional.of(image));

        assertDoesNotThrow(() -> {
            Image imageReal = imageService.getFile(1L);
            assertThat(imageReal).isNotNull();
        });
    }

    @Test
    public void shouldThorwErrorWhenImageDoesNotExists() {
        given(imageRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ImageNotFound.class, () -> {
            imageService.getFile(1L);
        });
    }
}
