package com.teampj.boardExample.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 표준 jpa 어노테이션
public class AuditingFields { // 메타 데이터 추출

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // pathing rule (springframework formatter 관련 datetimeformat)
  @CreatedDate
  @Column(nullable = false, updatable = false) // updatable = false == 이 필드는 없데이트가 불가하다
  private LocalDateTime createdAt;  // 생성일시

  @CreatedBy
  @Column (nullable = false, updatable = false, length = 100)
  private String createdBy; // 생성자

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime modifiedAt; // 수정일시

  @LastModifiedBy
  @Column(nullable = false, length = 100)
  private String modifiedBy; // 수정자

}
