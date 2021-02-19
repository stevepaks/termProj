package com.craft.termproj.bo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeaveBoTestContext {

    @Bean
    public LeaveBo leaveBo() {

        return new LeaveBo();
    }

}