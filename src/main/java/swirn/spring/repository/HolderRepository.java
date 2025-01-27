package swirn.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swirn.spring.domain.entity.Holder;

@Repository
public interface HolderRepository extends JpaRepository<Holder, Long> {

}
