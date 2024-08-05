package com.team5.hospital_here.user.entity.user;


import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.user.entity.user.address.AddressDTO;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.user.phoneNumberDTO.PhoneNumberDTO;
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

    /**
     * 회원 정보 수정
     * @param userDTO update 할 회원 정보
     */
    public void update(UserDTO userDTO){
        updateName(userDTO.getName());
        updatePhoneNumber(userDTO.getPhoneNumber());
        updateBirthday(userDTO.getBirthday());
        updateAddress(userDTO.getAddress(), userDTO.getAddressDetail());
        updateImg(userDTO.getImage());
    }

    /**
     * 회원 주소 수정
     * @param addressDTO update 할 주소 정보
     */
    public void update(AddressDTO addressDTO){
        updateAddress(addressDTO.getAddress(), addressDTO.getAddressDetail());
    }

    /**
     * 회원 연락처 수정
     * @param phoneNumberDTO
     */
    public void update(PhoneNumberDTO phoneNumberDTO){
        updatePhoneNumber(phoneNumberDTO.getPhoneNumber());
    }

    public void updateAddress(String address, String addressDetail){
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateBirthday(String birthday){
        this.birthday = Date.valueOf(birthday);
    }

    public void updateImg(String img){
        this.img = img;
    }
}
