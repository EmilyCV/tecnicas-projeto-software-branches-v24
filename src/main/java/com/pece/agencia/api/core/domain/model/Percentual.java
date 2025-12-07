package com.pece.agencia.api.core.domain.model;

import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.compare.ComparableUtils;
import org.jetbrains.annotations.NotNull;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public record Percentual(BigDecimal valor) implements Comparable<Percentual> {
    public Percentual {
        Objects.requireNonNull(valor);
    }

    public MonetaryAmount aplicar(MonetaryAmount amount) {
        return amount.multiply(valor);
    }

    public Percentual multiplicar(double multiplicador) {
        return new Percentual(valor.multiply(BigDecimal.valueOf(multiplicador)));
    }

    public Percentual max(Percentual o1) {
        return ComparableUtils.max(this, o1);
    }
    public Percentual min(Percentual o1) {
        return ComparableUtils.min(this, o1);
    }

    @Override
    public int compareTo(@NotNull Percentual o) {
        return this.valor.compareTo(o.valor);
    }
}
