package ar.com.sistema.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaDeLogs")
    @SequenceGenerator(name = "secuenciaDeLogs", sequenceName = "LOG_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(length = 20, nullable = false)
    String userId;

    @Column(nullable = false)
    LocalDateTime dated;

    @Column(nullable = false)
    String logger;

    @Column(length = 10, nullable = false)
    String level;

    @Column(length = 10000, nullable = false)
    String message;
}
