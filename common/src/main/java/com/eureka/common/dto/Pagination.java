package com.eureka.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Pagination {

    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
    private Integer number;
}
