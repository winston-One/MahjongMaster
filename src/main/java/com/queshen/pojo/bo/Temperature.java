package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WinstonYv
 * @create 2023/5/10 17:48
 * @Description: Man can conquer nature
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Temperature {

    private Integer id;

    private Double xNode;

    private Double yNode;

    private Double zNode;

    private Double temperature;

}
