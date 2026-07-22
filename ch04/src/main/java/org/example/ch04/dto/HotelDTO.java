package org.example.ch04.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDTO {
    private String city;
    private List<String> names;
}
