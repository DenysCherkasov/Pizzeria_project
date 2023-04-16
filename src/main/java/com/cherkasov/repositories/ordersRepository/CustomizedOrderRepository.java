package com.cherkasov.repositories.ordersRepository;

import com.cherkasov.dto.OrderMainInfoDto;
import com.cherkasov.dto.OrderStatisticDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomizedOrderRepository {
    List<OrderMainInfoDto> getOrdersInfo();
    List<OrderMainInfoDto> findOrdersByUserId(String id);
    Optional<OrderStatisticDto> getStatistic(LocalDate date);


}
