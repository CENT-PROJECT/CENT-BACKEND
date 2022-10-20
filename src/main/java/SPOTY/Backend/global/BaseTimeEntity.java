package SPOTY.Backend.global;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  //Auditing 기능을 포함시킨다.
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime modifiedDate;

}

