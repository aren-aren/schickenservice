package com.dongil.schickenservice.apis.order;

import com.dongil.schickenservice.commons.mail.MailSender;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderMapper orderMapper;
    private final MailSender mailSender;

    public CustomerVO loginProcess(CustomerVO customerVO) {
        String password = createPassword();
        customerVO.setPassword(password);

        /* password를 넣어 메일 보내기 */
        try {
            mailSender.sendMail(customerVO.getEmail(), customerVO.getPassword());
        } catch (MessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }

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
