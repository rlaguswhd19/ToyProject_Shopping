package shopping.back.hj.dress;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DressRepository extends JpaRepository<Dress, Integer> {

	Optional<Dress> findById(Long id);
}
