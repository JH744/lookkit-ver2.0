package synerjs.lookkit2nd.codi;

import org.springframework.web.bind.annotation.*;
import synerjs.lookkit2nd.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/codi")
public class CodiController {

    private final CodiService codiService;

    @Autowired
    public CodiController(CodiService codiService) {
        this.codiService = codiService;
    }

    @GetMapping
    public List<Codi> getAllCodisets() {
        return codiService.getAllCodis();
    }

    @GetMapping("/{codiId}")
    public Codi getCodiById(@PathVariable("codiId") Long codiId) {
        return codiService.getCodiById(codiId);
    }

    @PostMapping("/rent")
    public OrderDTO rentCodi(
        @RequestParam(name = "codiId") Long codiId, 
        @RequestParam(name = "startDate") String startDate, 
        @RequestParam(name = "endDate") String endDate) {

    Codi codi = codiService.getCodiById(codiId);
    LocalDate rentalStart = LocalDate.parse(startDate);
    LocalDate rentalEnd = LocalDate.parse(endDate);
    int days = (int) ChronoUnit.DAYS.between(rentalStart, rentalEnd);
    
    Integer basePrice = codi.getCodiPrice();  // basePrice는 Integer 타입
    Integer additionalDayPrice = 10000;  // 추가 비용도 Integer로 정의
    Integer totalPrice = (days > 3) 
        ? basePrice + (additionalDayPrice * (days - 3))
        : basePrice;

    return OrderDTO.builder()
            .itemId(codi.getCodiId())
            .itemName(codi.getCodiName())
            .startDate(rentalStart)
            .endDate(rentalEnd)
            .price(basePrice)
            .totalPrice(totalPrice)
            .build();
}
}
