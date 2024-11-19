package synerjs.lookkit2nd.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synerjs.lookkit2nd.codi.Codi;
import synerjs.lookkit2nd.codi.CodiService;

import java.util.List;

@RestController
@RequestMapping("/api/main/")
@RequiredArgsConstructor
public class MainController {
    private final CodiService coordisetService;



    /**
     * 최신 8개 코디 세트 조회 API
     */
    @GetMapping("/codis/latest")
    public ResponseEntity<List<Codi>> getLatestEightCodiSets() {
        return ResponseEntity.ok(coordisetService.getLatestEightCodiSets());
    }

    /**
     * 코디 세트와 연관된 상품 10개 조회 API
     */
    @GetMapping("/codiset")
    public ResponseEntity<List<Codi>> getAllCoordiWithProducts() {
        return ResponseEntity.ok(coordisetService.getAllCoordiWithProducts());
    }

}
