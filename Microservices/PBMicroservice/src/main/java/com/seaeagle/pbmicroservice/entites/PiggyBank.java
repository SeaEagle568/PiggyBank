package com.seaeagle.pbmicroservice.entites;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "PiggyBanks")
@Table(
        name = "piggy_banks",
        uniqueConstraints = {
                @UniqueConstraint(name = "real_id_unique", columnNames = "real_id"),
                @UniqueConstraint(name = "address_unique", columnNames = "address")
        }
)
@TypeDef(
        name = "list-array",  //This kek allows to use Lists in DB (based)
        typeClass = ListArrayType.class
)
@Scope(value = "prototype")
@ToString
@NoArgsConstructor
public class PiggyBank {

    @SequenceGenerator(
            name = "pb_sequence",
            sequenceName = "pb_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "pb_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )

    @Getter
    @Setter
    private Long id;

    @Column(
            name = "real_id",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String realId;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String name;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "varchar(1024)"
    )
    @Getter
    @Setter
    private String description;

    @Column(
            name = "author",
            nullable = false
    )
    @Getter
    @Setter
    private Long author;

    @Column(
            name = "address",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String address;

    @Column(
            name = "destination",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String destination;

    @Column(
            name = "investors",
            columnDefinition = "integer[]"
    )
    @Type(type = "list-array")
    @Getter
    @Setter
    private List<Long> investors;

    @Column(
            name = "status",
            nullable = false,
            columnDefinition = "varchar(256)"
    )
    @Getter
    @Setter
    private String status;

    @Column(
            name = "creation_time",
            nullable = false,
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP"
    )
    @Getter
    @Setter
    private Timestamp creationTime;

    @Column(
            name = "modification_time",
            columnDefinition = "TIMESTAMP"
    )
    @Getter
    @Setter
    private Timestamp modificationTime;

    public PiggyBank(String realId, String name, String description, Long author, String address, String destination, List<Long> investors, String status) {
        this.realId = realId;
        this.name = name;
        this.description = description;
        this.author = author;
        this.address = address;
        this.destination = destination;
        this.investors = investors;
        this.status = status;
    }
}
