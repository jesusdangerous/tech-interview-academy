package com.interview.academy.domain.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Mail {

    private String[] to;
    private String subject;
    private String body;
}
