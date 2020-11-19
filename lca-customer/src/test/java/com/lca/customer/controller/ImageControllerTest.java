package com.lca.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lca.customer.exception.ImageExceededSize;
import com.lca.customer.exception.ImageInvalidType;
import com.lca.customer.exception.ImageNotFound;
import com.lca.customer.exception.ProductDetailNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.model.Image;
import com.lca.customer.model.ProductDetail;
import com.lca.customer.service.ImageService;
import com.lca.customer.service.ProductDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.apache.http.client.methods.RequestBuilder.post;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    Image image = new Image();
    MockMultipartFile file;

    UUID uuid = UUID.randomUUID();

    MockMultipartFile fileExceeded;
    MockMultipartFile fileWrongType;

    @Before
    public void setUp() {
        byte [] data = new byte[2 * 1024 * 1024];
        image.setId(uuid);
        image.setName("image.jpg");
        image.setType("jpg");
        image.setData(data);
        file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, data);

        byte [] dataExceeded = new byte[4 * 1024 * 1024];
        fileExceeded = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, dataExceeded);

        fileWrongType = new MockMultipartFile("file", "image.txt", MediaType.TEXT_PLAIN_VALUE, data);
    }

    @Test
    public void shouldGetImage() {
        given(imageService.getFile(uuid)).willReturn(this.image);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/images/" + uuid.toString()).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().bytes(image.getData()))
                    .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
        });
    }

    @Test
    public void shouldThrowErrorWhenImageDoesNotExists() {
        given(imageService.getFile(uuid)).willThrow(ImageNotFound.class);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/images/" + uuid.toString()).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    public void shouldSaveImage() {
        given(imageService.store(file)).willReturn(this.image);
        MockMvc mockMvcWeb = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertNotNull(file);

        assertDoesNotThrow(() -> {
            mockMvcWeb.perform(multipart("/images").file(file))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(image.getId().toString())))
                    .andExpect(jsonPath("$.name", is(image.getName())))
                    .andExpect(jsonPath("$.type", is(image.getType())));
        });
    }

    @Test
    public void shouldThrowErrorWhenSaveExceededImage() {
        given(imageService.store(fileExceeded)).willThrow(ImageExceededSize.class);
        MockMvc mockMvcWeb = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertNotNull(fileExceeded);

        assertDoesNotThrow(() -> {
            mockMvcWeb.perform(multipart("/images").file(fileExceeded))
                    .andExpect(status().isBadRequest());
        });
    }

    @Test
    public void shouldThrowErrorWhenSaveWrongTypeImage() {
        given(imageService.store(fileWrongType)).willThrow(ImageInvalidType.class);
        MockMvc mockMvcWeb = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertNotNull(fileWrongType);

        assertDoesNotThrow(() -> {
            mockMvcWeb.perform(multipart("/images").file(fileWrongType))
                    .andExpect(status().isBadRequest());
        });
    }
}
