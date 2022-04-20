package pl.MarcinKaminski.Cantor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.MarcinKaminski.Cantor.model.Currency;
import pl.MarcinKaminski.Cantor.model.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MainService {
    public static final String URL = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";

    public List<Currency> fetchTable() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Table[]> entity = restTemplate.getForEntity(URL, Table[].class);
        Table body = Objects.requireNonNull(entity.getBody())[0];
        log.info("response: \n{}", (body));
        return body.getRates();

    }

    public List<String> getCurrencyNames() {
        return
                fetchTable().stream()
                        .map(Currency::getCurrency)
                        .collect(Collectors.toList());
    }

    public String calculateCurrency(String currencyInn, String currencyOut, String amount) {
        StringBuilder stringBuilder= new StringBuilder();
        double doubleAmount= Double.parseDouble(amount);
        double result = getRateByCurrencyName(currencyInn) * doubleAmount / getRateByCurrencyName(currencyOut);

       return stringBuilder
               .append("For ")
               .append(amount)
               .append(" ")
               .append(currencyInn)
               .append(" you can buy :")
               .append(result)
               .append(" ")
               .append(currencyOut)
               .toString();

    }
    private double getRateByCurrencyName(String currency){
        return fetchTable().stream()
                .filter(currency1 -> currency1.getCurrency().equals(currency))
                .mapToDouble(Currency::getMid)
                .findFirst()
                .orElse(0);
    }
}
