package jp.gihyo.projava.tasklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface AccountRepository extends JpaRepository<Account, Integer> {

        Account findByUserName(String userName);
    }

