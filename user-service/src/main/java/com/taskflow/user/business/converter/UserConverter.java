package com.taskflow.user.business.converter;

import com.taskflow.user.business.dto.AddressDTO;
import com.taskflow.user.business.dto.PhoneDTO;
import com.taskflow.user.business.dto.UserDTO;
import com.taskflow.user.infrastructure.entity.Address;
import com.taskflow.user.infrastructure.entity.Phone;
import com.taskflow.user.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public User toUser(UserDTO userDTO){
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .addresses(toListAddress(userDTO.getAddressDTOS()))
                .phones(toPhoneList(userDTO.getPhoneDTOS()))
                .build();
    }

    public List<Address> toListAddress(List<AddressDTO> addressDTOS){
        return  addressDTOS.stream().map(this::toAddress).toList();
    }

   /*

       public List<Address> toListAddress(List<AddressDTO> addressDTOS){
            List<Address> addresses = new ArrayList<>();
            for(AddressDTO addressDTO : addressDTOS){
                addresses.add(toAddress(addressDTO));
            }
            return  addresses;
        }
    */

    public Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .street(addressDTO.getStreet())
                .addressLine2(addressDTO.getAddressLine2())
                .houseNumber(addressDTO.getHouseNumber())
                .city(addressDTO.getCity())
                .county(addressDTO.getCounty())
                .eirCode(addressDTO.getEirCode())
                .build();
    }

    public List<Phone> toPhoneList(List<PhoneDTO> phoneDTOS){
        return  phoneDTOS.stream().map(this::toPhone).toList();
    }

    public Phone toPhone(PhoneDTO phoneDTO){
        return Phone.builder()
                .areaCode(phoneDTO.getAreaCode())
                .phoneNumber(phoneDTO.getPhoneNumber())
                .build();
    }

    public UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .addressDTOS(toListAddressDTO(user.getAddresses()))
                .phoneDTOS(toPhoneListDTO(user.getPhones()))
                .build();
    }

    public List<AddressDTO> toListAddressDTO(List<Address> addressList){
        return  addressList.stream().map(this::toAddressDTO).toList();
    }


    public AddressDTO toAddressDTO(Address address){
        return AddressDTO.builder()
                .street(address.getStreet())
                .addressLine2(address.getAddressLine2())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .county(address.getCounty())
                .eirCode(address.getEirCode())
                .build();
    }

    public List<PhoneDTO> toPhoneListDTO(List<Phone> phoneList){
        return  phoneList.stream().map(this::toPhoneDTO).toList();
    }

    public PhoneDTO toPhoneDTO(Phone phone){
        return PhoneDTO.builder()
                .areaCode(phone.getAreaCode())
                .phoneNumber(phone.getPhoneNumber())
                .build();
    }

}
