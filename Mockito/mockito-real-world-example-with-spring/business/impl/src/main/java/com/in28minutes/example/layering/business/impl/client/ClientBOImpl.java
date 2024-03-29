package com.in28minutes.example.layering.business.impl.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.example.layering.business.api.client.ClientBO;
import com.in28minutes.example.layering.data.api.client.ClientDO;
import com.in28minutes.example.layering.data.api.client.ProductDO;
import com.in28minutes.example.layering.model.api.client.Amount;
import com.in28minutes.example.layering.model.api.client.Client;
import com.in28minutes.example.layering.model.api.client.Currency;
import com.in28minutes.example.layering.model.api.client.Product;
import com.in28minutes.example.layering.model.impl.client.AmountImpl;

/**
 * Data Object for the Client entity.
 */
@Service
public class ClientBOImpl implements ClientBO {

	@Autowired
	ProductDO productDO;

	@Autowired
	ClientDO clientDO;

	@Override
	public Amount getClientProductsSum(long clientId) {

		List<Product> existingProducts = productDO.getAllProducts(clientId);

//		System.out.println("existingProducts : "+existingProducts.size());
		return new AmountImpl(calculateClientProductSum(existingProducts), Currency.EURO);
	}

	@Override
	public void saveChangedProducts(long clientId, List<Product> userEnteredProducts) {

		List<Product> databaseProducts = productDO.getAllProducts(clientId);
		
		for (Product product : databaseProducts) {
//			System.out.println("databaseProducts : "+product.getId());
		}

		updateExistingProductsWhichAreModified(clientId, userEnteredProducts, databaseProducts);

		insertNewProducts(clientId, userEnteredProducts, databaseProducts);

		deleteStaleProducts(clientId, databaseProducts, userEnteredProducts);

	}

	@Override
	public void calculateAndSaveClientProductSum(Client client) {
		BigDecimal clientProductSum = calculateClientProductSum(client.getProducts());
		client.setProductAmount(clientProductSum);
		clientDO.saveClient(client);
	}

	private void deleteStaleProducts(long clientId, List<Product> existingProducts, List<Product> newProducts) {
		Map<Long, Product> newProductsMap = convertToMap(newProducts);
		for (Product product1 : existingProducts) {
			if (!newProductsMap.containsKey(product1.getId())) {
				System.out.println("product1 : "+product1.getId());
				productDO.deleteProduct(clientId, product1);
			}
		}
	}

	private void insertNewProducts(long clientId, List<Product> newProducts, List<Product> existingProducts) {
		Map<Long, Product> existingProductsMap = convertToMap(existingProducts);
		for (Product newProduct : newProducts) {
			System.out.println("newProduct : "+newProduct.getId());
			if (!existingProductsMap.containsKey(newProduct.getId())) {
				productDO.insertProduct(clientId, newProduct);
			}
		}
	}

	private void updateExistingProductsWhichAreModified(long clientId, List<Product> newProducts,
			List<Product> existingProducts) {
		Map<Long, Product> existingProductsMap = convertToMap(existingProducts);
		for (Product newProduct : newProducts) {
			if (existingProductsMap.containsKey(newProduct.getId())) {
				productDO.updateProduct(clientId, newProduct);
			}
		}
	}

	private Map<Long, Product> convertToMap(List<Product> products) {
		Map<Long, Product> productMap = new HashMap<Long, Product>();
		for (Product existingProduct : products) {
			System.out.println("existingProduct : "+existingProduct.getId());
			productMap.put(existingProduct.getId(), existingProduct);
		}
		return productMap;
	}

	private BigDecimal calculateClientProductSum(List<Product> existingProducts) {
		BigDecimal clientProductsSum = BigDecimal.ZERO;

		for (Product product : existingProducts) {
			clientProductsSum = clientProductsSum.add(product.getAmount().getValue());
		}
		return clientProductsSum;
	}

}