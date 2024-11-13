package synerjs.lookkit2nd.codi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodiService {

    private final CodiRepository codiRepository;

    @Autowired
    public CodiService(CodiRepository codiRepository) {
        this.codiRepository = codiRepository;
    }

    public List<Codi> getAllCodis() {
        return codiRepository.findAll();
    }

    public Codi getCodiById(Long codiId) {
        return codiRepository.findById(codiId).orElse(null);
    }
}

