package com.nix.vyrvykhvost.service;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.model.Invoice;
import com.nix.vyrvykhvost.model.Product;
import com.nix.vyrvykhvost.repository.InvoiceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class InvoiceService {
    private static InvoiceService instance;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService(InvoiceRepository repository) {
        this.invoiceRepository = repository;
    }


    public void createFromProducts(List<Product> invoiceProducts) {
        Invoice invoice = new Invoice();

        invoice.setTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(Product::getPrice).sum());
        invoice.setProducts(new ArrayList<>(invoiceProducts));

        invoiceRepository.save(invoice);
    }

    public void updateDate(LocalDateTime newDate, String id) {
        invoiceRepository.findById(id).ifPresentOrElse(invoice -> {
            invoice.setTime(newDate);
            invoiceRepository.update(invoice);
        }, () -> {
            throw new IllegalArgumentException("Unable to update date, invoice with id=" + id + " does not exist!");
        });
    }
    public List<Invoice> findAllGreaterSumInvoices(double sum) {
        return invoiceRepository.findAllGreaterSumInvoices(sum);
    }
    public int getInvoiceCount() {
        return invoiceRepository.getInvoiceCount();
    }
    public Map<Double, Integer > sortBySum() {
        return invoiceRepository.sortBySum();
    }

}