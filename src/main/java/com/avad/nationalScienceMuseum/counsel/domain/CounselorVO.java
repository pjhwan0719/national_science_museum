package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder(builderMethodName = "CounselorVOBuilder")
@EqualsAndHashCode
public class CounselorVO {
    private long seq;
    private String id;
    private String pw;
    private String name;
    private String is_main;
    private String is_use;
    private String is_login;
    private String is_del;
    private String sectors;
    private String insert_date;
    private String update_date;
    private List<CounselorSectorDTO> counselorSectorDTOList;

    /**
     * PJH
     * - CounselorVO Builder
     */
    public static CounselorVOBuilder builder() {
        return CounselorVOBuilder();
    }

    /**
     * PJH
     * - Cast to CounselorDTO
     * @return CounselorDTO
     */
    public CounselorDTO toCounselorDTO() {
        return CounselorDTO.CounselorDTOBuilder()
                .seq(seq)
                .id(id)
                .pw(pw)
                .name(name)
                .is_main(is_main)
                .is_use(is_use)
                .is_login(is_login)
                .is_del(is_del)
                .sectors(sectors)
                .insert_date(insert_date)
                .update_date(update_date)
                .counselorSectorDTOList(counselorSectorDTOList)
                .build();
    }
}
