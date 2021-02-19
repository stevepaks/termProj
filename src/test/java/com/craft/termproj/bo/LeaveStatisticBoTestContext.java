package com.craft.termproj.bo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeaveStatisticBoTestContext {

    @Bean
    public LeaveStatisticBo leaveStatisticBo() {

        return new LeaveStatisticBo();
    }

}