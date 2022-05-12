package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "CounselorInfoDTOBuilder")
@ToString
@Getter
@Setter
public class CounselorInfoDTO {

    private long seq;
    private String id;
    private String name;
    private String is_main;
    private String is_use;
    private String is_login;
    private String is_del;
    private String insert_date;
    private String update_date;
    private List<SectorDTO> sectorDTOList;


    /**
     *  PJH
     *  - param CounselorSectorEntity List
     *  - get SectorEntity
     *  - to SectorDTO
     *  - return SectorDTO List
     * @param counselorSectorEntityList
     */
    public static CounselorInfoDTOBuilder builder(List<CounselorSectorEntity> counselorSectorEntityList) {
        List<SectorDTO> sectorDTOList = new ArrayList<>();
        if (counselorSectorEntityList == null || counselorSectorEntityList.isEmpty()) {
            throw new IllegalArgumentException("sectorEntityList is null or empty");
        }
        else {
            for ( CounselorSectorEntity counselorSectorEntity : counselorSectorEntityList ) {
                sectorDTOList.add(counselorSectorEntity.getSectorEntity().toSectorDTO());
            }
        }
        return CounselorInfoDTOBuilder().sectorDTOList(sectorDTOList);
    }

    /**
     * PJH
     * - Cast to {@link CounselorEntity}
     */
    public CounselorEntity toCounselorEntity() {
        return CounselorEntity.CounselorEntityBuilder()
                .seq(seq)
                .id(id)
                .name(name)
                .is_main(is_main)
                .is_use(is_use)
                .is_login(is_login)
                .is_del(is_del)
                .insert_date(insert_date)
                .update_date(update_date)
                .build();
    }
}
