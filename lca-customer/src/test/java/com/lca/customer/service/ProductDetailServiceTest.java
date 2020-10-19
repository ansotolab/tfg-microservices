package com.lca.customer.service;

import com.lca.customer.exception.ProductDetailNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.model.ProductDetail;
import com.lca.customer.repository.ProductDetailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ProductDetailServiceTest {

    @Mock
    private ProductDetailRepository productDetailRepository;

    @InjectMocks
    private ProductDetailService productDetailService;

    @Test
    public void shouldSaveProductDetailSuccessfully() {
        ProductDetail productDetail = new ProductDetail("Texto de prueba.", new Customer());
        productDetail.setId(1L);

        given(productDetailRepository.save(productDetail)).willAnswer(invocation -> invocation.getArgument(0));

        ProductDetail productDetailReal = productDetailService.saveProductDetail(productDetail);

        assertThat(productDetailReal).isNotNull();
    }

    @Test
    public void shouldUpdateProductDetailSuccessfully() {
        ProductDetail productDetail = new ProductDetail("Texto de prueba.", new Customer());
        productDetail.setId(1L);

        given(productDetailRepository.findById(productDetail.getId())).willReturn(Optional.of(productDetail));
        given(productDetailRepository.save(productDetail)).willAnswer(invocation -> invocation.getArgument(0));

        ProductDetail productDetailReal = productDetailService.updateProductDetail(productDetail);

        assertThat(productDetailReal).isNotNull();
    }

    @Test
    public void shouldThrowErrorWhenUpdateWithNotExistingProductDetail() {
        ProductDetail productDetail = new ProductDetail("Texto de prueba.", new Customer());
        productDetail.setId(1L);

        given(productDetailRepository.save(productDetail)).willAnswer(invocation -> invocation.getArgument(0));
        given(productDetailRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ProductDetailNotFound.class, () -> {
            ProductDetail productDetailReal = productDetailService.updateProductDetail(productDetail);
        });
    }
}
