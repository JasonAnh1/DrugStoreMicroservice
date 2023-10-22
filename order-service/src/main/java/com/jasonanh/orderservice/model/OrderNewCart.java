package com.jasonanh.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "t_orders_new_cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderNewCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long orderId;
    private int amount;
    private long price;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private OrderNew order;
    private String skuCode;
    private PackType packType;
    @Transient
    private String uidMed;
}
