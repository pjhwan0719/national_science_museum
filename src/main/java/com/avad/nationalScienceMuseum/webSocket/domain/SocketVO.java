package com.avad.nationalScienceMuseum.webSocket.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SocketVO {
    private String kiosk_id;
    private String counselor_id;
    private String status;
    private String room_id;
    
    private long hist_seq;
    private String start_date;
    private String end_date;
}
