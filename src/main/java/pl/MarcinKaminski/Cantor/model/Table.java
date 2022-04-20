package pl.MarcinKaminski.Cantor.model;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private String table;
    private String no;
    private String effectiveDate;
    private List<Currency> rates;
}
