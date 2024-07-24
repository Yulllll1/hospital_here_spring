package com.team5.hospital_here.user.entity.user;


import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.user.entity.user.address.AddressDTO;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String name;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String phoneNumber;

    private Date birthday;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String address;

    private String addressDetail;

    private String img;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void update(UserDTO userDTO){
        name = userDTO.getName();
        phoneNumber = userDTO.getPhoneNumber();
        birthday = Date.valueOf(userDTO.getBirthday());
        address = userDTO.getAddress();
        addressDetail = userDTO.getAddressDetail();
        img = userDTO.getImage();
    }

    public void update(AddressDTO addressDTO){
        updateAddress(addressDTO.getAddress(), addressDTO.getAddressDetail());
    }

    public void updateAddress(String address, String addressDetail){
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
