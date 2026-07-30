package com.bruno.trivia.services;
import com.bruno.trivia.dtos.CustomerRequestDTO;
import com.bruno.trivia.dtos.CustomerResponseDTO;
import com.bruno.trivia.entities.Customer;
import com.bruno.trivia.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerResponseDTO findById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado!")
        );
        return toResponse(customer);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> findAll(Pageable pageable){
        Page<CustomerResponseDTO> pageCustomers = customerRepository.findAll(pageable)
                .map(c -> toResponse(c));
        return pageCustomers;
    }

    @Transactional
    public Customer createOrUpdate(CustomerRequestDTO dto){
        Optional<Customer> exist = customerRepository.findByPhone(dto.phone());

        if(exist.isPresent()){
            Customer customer = exist.get();
            dtoToEntity(dto, customer);
            customer = customerRepository.save(customer);
            return customer;
        }
        else {
            Customer customer = toCustomer(dto);
            customer = customerRepository.save(customer);
            return customer;
        }
    }

    @Transactional
    public void deleteById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado")
        );
        customerRepository.delete(customer);
    }

    private CustomerResponseDTO toResponse(Customer customer){
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone()
        );
    }

    private Customer toCustomer(CustomerRequestDTO dto){
        return new Customer(
                dto.name(),
                dto.phone()
        );
    }

    private void dtoToEntity(CustomerRequestDTO dto, Customer customer){
        customer.setName(dto.name());
        customer.setPhone(dto.phone());
    }
}
