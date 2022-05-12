package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "CounselorDTOBuilder")
@ToString
@Getter
@Setter
public class CounselorDTO {
    private long seq;
    private String id;
    private String pw;
    private String name;
    private String is_main;
    private String is_use;
    private String is_login;
    private String is_del;
    private String insert_date;
    private String update_date;
    private List<CounselorSectorDTO> counselorSectorDTOList;

    private String sectors;

    /**
     * PJH
     * - CounselorDTO builder
     */
    public static CounselorDTOBuilder builder() {
        return CounselorDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link CounselorEntity}
     */
    public CounselorEntity toCounselorEntity() {
        return CounselorEntity.CounselorEntityBuilder()
                .seq(seq)
                .id(id)
                .pw(pw)
                .name(name)
                .is_main(is_main)
                .is_use(is_use)
                .is_login(is_login)
                .is_del(is_del)
                .insert_date(insert_date)
                .update_date(update_date)
                .build();
    }

    /**
     * PJH
     * - 양방향 연관관계 설정
     */
    public void addCounselorSectorDTO(CounselorSectorDTO counselorSectorDTO) {
        counselorSectorDTOList.add(counselorSectorDTO);
        counselorSectorDTO.setCounselorDTO(this);
    }
}