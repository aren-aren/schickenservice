package com.dongil.schickenservice.apis.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    public CustomerVO loginProcess(CustomerVO customerVO) {
        String password = createPassword();
        customerVO.setPassword(password);

        /* password를 넣어 메일 보내기 */

        return customerVO;
    }

    @Transactional
    public CustomerVO loginCheck(CustomerVO customerVO, CustomerVO passwordVO) {
        if(customerVO.getEmail().equals(passwordVO.getEmail()) && customerVO.getPassword().equals(passwordVO.getPassword())){
            CustomerVO found = orderMapper.getCustomer(customerVO);

            if(found == null){
                orderMapper.insertCustomer(customerVO);
                found = customerVO;
            }

            return found;
        }

        return null;
    }

    private String createPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

}
