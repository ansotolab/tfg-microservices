package com.lca.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lca.customer.exception.ProductDetailNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.model.ProductDetail;
import com.lca.customer.service.ProductDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductDetailController.class)
public class ProductDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductDetailService productDetailService;

    ProductDetail productDetail = new ProductDetail();

    @Before
    public void setUp() {
        productDetail.setId(1L);
        productDetail.setText("Texto de prueba.");
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");
        //customer.setId(1L);
        productDetail.setCustomer(customer);
    }

    @Test
    public void shouldGetOneProductDetail() {
        given(productDetailService.getProductDetail(1L)).willReturn(this.productDetail);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/details/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.text", is("Texto de prueba.")));
        });
    }

    @Test
    public void shouldThrowErrorWhenGetProductDetailThatNotExists() {
        given(productDetailService.getProductDetail(1L)).willThrow(ProductDetailNotFound.class);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/details/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    public void shouldUpdateProductDetailSuccesfully() {
        given(productDetailService.updateProductDetail(productDetail)).willReturn(productDetail);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(put("/details/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDetail)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json(objectMapper.writeValueAsString(productDetail)));
        });
    }

    @Test
    public void shouldThrowErrorWhenUpdateProductDetailThatNotExists() {
        given(productDetailService.updateProductDetail(productDetail)).willThrow(ProductDetailNotFound.class);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(put("/details/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDetail)))
                    .andExpect(status().isNotFound());
        });
    }
}
