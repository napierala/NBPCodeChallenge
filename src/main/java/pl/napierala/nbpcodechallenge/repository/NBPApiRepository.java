package pl.napierala.nbpcodechallenge.repository;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.napierala.nbpcodechallenge.nbp.NBPRate;

@FeignClient(value = "nbpAPI", url = "http://api.nbp.pl/api/exchangerates/rates/a")
public interface NBPApiRepository {

    @GetMapping(value = "/{currencyCode}", produces = "application/json")
    @Headers("Accept: application/json")
    NBPRate getRate(@PathVariable(name = "currencyCode") String currencyCode);
}