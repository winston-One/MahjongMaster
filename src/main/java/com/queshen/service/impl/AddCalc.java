package com.queshen.service.impl;

import com.queshen.service.ICalc;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @create 2023/2/25 10:24
 * @Description: Man can conquer nature
 **/
@Service("add")
public class AddCalc implements ICalc {
    @Override
    public int operation(int i, int j) {
        return i+j;
    }
}
