package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "CounselorEntityBuilder")
@ToString
@Getter
@Entity
@Table(name="videosupport_counselor")
public class CounselorEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    @Column(name = "id")
    private String id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "name")
    private String name;

    @Column(name = "is_main")
    private String is_main;

    @Column(name = "is_use")
    private String is_use;

    @Column(name = "is_login")
    private String is_login;

    @Column(name = "is_del")
    private String is_del;

    @Column(name = "insert_date")
    private String insert_date;

    @Column(name = "update_date")
    private String update_date;

    @OneToMany(mappedBy = "counselorEntity" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CounselorSectorEntity> counselorSectorEntityList;

    /**
     * PJH
     * - CounselorEntity builder
     */
    public static CounselorEntityBuilder builder() {
        return CounselorEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link CounselorDTO}
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
                .insert_date(insert_date)
                .update_date(update_date)
                .build();
    }

    /**
     * PJH
     * - Cast to {@link CounselorInfoDTO}
     */
    public CounselorInfoDTO toCounselorInfoDTO() {
        return CounselorInfoDTO.builder(counselorSectorEntityList)
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

    public void change_pw(String pw) {
        this.pw = pw;
    }

    public void change_name(String name) {
        this.name = name;
    }

    public void change_isLogin(String is_login) {
        this.is_login = is_login;
    }

    public void change_isUse(String is_use) {
        this.is_use = is_use;
    }

    public void change_isMain(String is_main) {
        this.is_main = is_main;
    }

    public void change_isDel(String is_del) {
        this.is_del = is_del;
    }

    public void change_updateDate(String update_date) {
        this.update_date = update_date;
    }

    public void change_counselorSectorDTOList(List<CounselorSectorEntity> counselorSectorEntityList) {
        this.counselorSectorEntityList = counselorSectorEntityList;
    }

}
