package diplom.sura.rassai.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diplom.sura.rassai.models.PasswordResetRequest;

@Repository
@Transactional
public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Long> {
    PasswordResetRequest findByEmail(String email);
}
