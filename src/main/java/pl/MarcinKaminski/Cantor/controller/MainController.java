package pl.MarcinKaminski.Cantor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.MarcinKaminski.Cantor.model.Currency;
import pl.MarcinKaminski.Cantor.service.MainService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping(value = {"/", "/home"})
    public String getHome() {
        return "home";
    }

    @GetMapping(value = "/table")
    public String getTable(Model model) {
        List<Currency> currencyList = mainService.fetchTable();
        model.addAttribute("table", currencyList);
        return "table";
    }

    @GetMapping(value = "/cantor")
    public String getCantor(Model model) {
        model.addAttribute("currencyList", mainService.getCurrencyNames());
        return "cantor";
    }

    @PostMapping(value = "/cantor")
    public String calculateCurrency(Model model, @RequestParam String currencyIn, @RequestParam String currencyOut, @RequestParam String amount) {
        model.addAttribute("result",mainService.calculateCurrency(currencyIn,currencyOut,amount));
        model.addAttribute("currencyList", mainService.getCurrencyNames());
        log.info("Dane z formularza,\n CurrencyInn:{}\n, CurrencyOut:{}\n, amount:{}\n", currencyIn, currencyOut, amount);
        return "cantor";
    }
}