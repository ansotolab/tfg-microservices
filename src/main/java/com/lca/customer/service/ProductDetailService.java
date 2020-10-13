package com.lca.customer.service;

import com.lca.customer.exception.ProductDetailNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.model.ProductDetail;
import com.lca.customer.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    public ProductDetail updateProductDetail(ProductDetail productDetail) {
        Optional<ProductDetail> productDetailSearched = productDetailRepository.findById(productDetail.getId());

        if (!productDetailSearched.isPresent())
        {
            throw new ProductDetailNotFound("No se ha encontrado la descripción.");
        }
        else
        {
            productDetailSearched.get().setText(productDetail.getText());
            return productDetailRepository.save(productDetailSearched.get());
        }
    }

    public ProductDetail getProductDetail(long id) {
        Optional<ProductDetail> productDetailSearched = productDetailRepository.findById(id);

        if (!productDetailSearched.isPresent())
        {
            throw new ProductDetailNotFound("No se ha encontrado la descripción.");
        }
        else
        {
            return productDetailSearched.get();
        }
    }
}
