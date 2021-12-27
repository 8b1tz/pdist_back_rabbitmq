package br.com.ifpb.gpsback.repository;

import br.com.ifpb.gpsback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifpb.gpsback.model.Task;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
