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

    public User convertToEntity(UserDTO userDTO){
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .addresses(convertToAddressList(userDTO.getAddressDTOS()))
                .phones(convertToPhoneList(userDTO.getPhoneDTOS()))
                .build();
    }

    public List<Address> convertToAddressList(List<AddressDTO> addressDTOS){
        return  addressDTOS.stream().map(this::convertToAddress).toList();
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

    public Address convertToAddress(AddressDTO addressDTO){
        return Address.builder()
                .street(addressDTO.getStreet())
                .addressLine2(addressDTO.getAddressLine2())
                .houseNumber(addressDTO.getHouseNumber())
                .city(addressDTO.getCity())
                .county(addressDTO.getCounty())
                .eirCode(addressDTO.getEirCode())
                .build();
    }

    public List<Phone> convertToPhoneList(List<PhoneDTO> phoneDTOS){
        return  phoneDTOS.stream().map(this::toPhone).toList();
    }

    public Phone toPhone(PhoneDTO phoneDTO){
        return Phone.builder()
                .areaCode(phoneDTO.getAreaCode())
                .phoneNumber(phoneDTO.getPhoneNumber())
                .build();
    }

    public UserDTO convertToDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .addressDTOS(convertAddressDTOList(user.getAddresses()))
                .phoneDTOS(convertToPhoneDTOList(user.getPhones()))
                .build();
    }

    public List<AddressDTO> convertAddressDTOList(List<Address> addressList){
        return  addressList.stream().map(this::convertToAddressDTO).toList();
    }


    public AddressDTO convertToAddressDTO(Address address){
        return AddressDTO.builder()
                .street(address.getStreet())
                .addressLine2(address.getAddressLine2())
                .houseNumber(address.getHouseNumber())
                .city(address.getCity())
                .county(address.getCounty())
                .eirCode(address.getEirCode())
                .build();
    }

    public List<PhoneDTO> convertToPhoneDTOList(List<Phone> phoneList){
        return  phoneList.stream().map(this::convertToPhoneDTO).toList();
    }

    public PhoneDTO convertToPhoneDTO(Phone phone){
        return PhoneDTO.builder()
                .areaCode(phone.getAreaCode())
                .phoneNumber(phone.getPhoneNumber())
                .build();
    }

    public User updateEntityFromDTO(UserDTO userDTO, User entity){
        return User.builder()
                .firstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : entity.getFirstName())
                .lastName(userDTO.getLastName() != null ? userDTO.getLastName() : entity.getLastName())
                .id(entity.getId())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : entity.getPassword())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : entity.getEmail())
                .addresses(entity.getAddresses())
                .phones(entity.getPhones())
                .build();
    }

}
