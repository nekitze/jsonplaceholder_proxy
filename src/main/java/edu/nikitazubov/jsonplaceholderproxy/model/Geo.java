package edu.nikitazubov.jsonplaceholderproxy.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Geo {
    private String lat;
    private String lng;
}
