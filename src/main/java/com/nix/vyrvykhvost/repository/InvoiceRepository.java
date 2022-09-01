package com.nix.vyrvykhvost.repository;

import com.nix.vyrvykhvost.model.Invoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.*;

public interface InvoiceRepository {

    void save(Invoice invoice);


    Optional<Invoice> findById(String id);

    void update(Invoice invoice);
    public List<Invoice> findAllGreaterSumInvoices(double sum);
    public int getInvoiceCount();
    public Map<Double, Integer > sortBySum();
}
