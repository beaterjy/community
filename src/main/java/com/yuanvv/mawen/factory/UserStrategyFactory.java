package com.yuanvv.mawen.factory;

import com.yuanvv.mawen.strategy.UserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStrategyFactory {

    @Autowired
    private List<UserStrategy> strategies;

    public UserStrategy getStrategy(String name) {
        for (UserStrategy strategy : strategies) {
            if (strategy.getSupportedType().getName().equals(name)) {
                return strategy;
            }
        }
        return null;
    }

}
