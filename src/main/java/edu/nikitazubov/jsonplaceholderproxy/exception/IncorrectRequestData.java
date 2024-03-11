package edu.nikitazubov.jsonplaceholderproxy.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IncorrectRequestData {
    private String info;
}
