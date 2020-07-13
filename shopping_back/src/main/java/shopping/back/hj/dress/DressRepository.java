package shopping.back.hj.dress;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DressRepository extends JpaRepository<Dress, Long> {
	Optional<Dress> findById(Long id);
}
