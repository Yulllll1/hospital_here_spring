package com.team5.hospital_here.user.entity.doctorEntity;


import com.team5.hospital_here.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_profile")
public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // TODO: Hospital Entity 추가 되면 작업
//    @Column(nullable = false)
//    private Hospital hospital;

    @Column(nullable = false)
    private String major;
}
