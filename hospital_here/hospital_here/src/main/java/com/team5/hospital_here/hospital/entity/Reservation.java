package com.team5.hospital_here.hospital.entity;



import com.team5.hospital_here.user.entity.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String department;
    private LocalDate date;
    private LocalTime timeSlot;

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키
    private User user;

}
