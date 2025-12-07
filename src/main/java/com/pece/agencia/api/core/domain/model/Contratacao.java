package com.pece.agencia.api.core.domain.model;

import com.pece.agencia.api.common.Periodo;
import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CompositeType;
import org.hibernate.annotations.JdbcTypeCode;

import javax.money.MonetaryAmount;
import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CONTRATACAO")
@Data
public class Contratacao {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name="CLIENTE_ID")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="PACOTE_CONTRATADO_ID")
    private Pacote pacoteContratado;

    @Embedded
    @AttributeOverride(name = "inicio", column = @Column(name = "INICIO_PERIODO_VIAGEM"))
    @AttributeOverride(name = "fim", column = @Column(name = "FIM_PERIODO_VIAGEM"))
    private Periodo periodoViagem;

    @Column(name = "MOMENTO_COMPRA")
    private LocalDate momentoCompra;

    @AttributeOverride(name = "amount", column = @Column(name = "VALOR_TOTAL_MONTANTE"))
    @AttributeOverride(name = "currency", column = @Column(name = "VALOR_TOTAL_MOEDA"))
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount valorTotal;

    @AttributeOverride(name = "amount", column = @Column(name = "VALOR_DESCONTO_MONTANTE"))
    @AttributeOverride(name = "currency", column = @Column(name = "VALOR_DESCONTO_MOEDA"))
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount valorDesconto;


    @AttributeOverride(name = "amount", column = @Column(name = "VALOR_PAGO_MONTANTE"))
    @AttributeOverride(name = "currency", column = @Column(name = "VALOR_PAGO_MOEDA"))
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount valorPago;

    @Embedded
    @AttributeOverride(name="codigo", column = @Column(name = "CODIGO_PAGAMENTO"))
    private CodigoPagamento codigoPagamento;

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "RESERVA_HOTEL"))
    private ReservaHotel reservaHotel;

    @Embedded
    @AttributeOverride(name = "eticket", column = @Column(name = "ETICKET_RESERVA_IDA"))
    @AttributeOverride(name = "assento.numero", column = @Column(name = "ASSENTO_RESERVA_IDA"))
    @AttributeOverride(name = "horarioEmbarque", column = @Column(name = "HORARIO_EMBARQUE_RESERVA_IDA"))
    @AttributeOverride(name = "dadosVoo.numero", column = @Column(name = "NUMERO_VOO_RESERVA_IDA"))
    @AttributeOverride(name = "dadosVoo.horario", column = @Column(name = "HORARIO_VOO_RESERVA_IDA"))
    private ReservaVoo reservaVooIda;

    @Embedded
    @AttributeOverride(name = "eticket", column = @Column(name = "ETICKET_RESERVA_VOLTA"))
    @AttributeOverride(name = "assento.numero", column = @Column(name = "ASSENTO_RESERVA_VOLTA"))
    @AttributeOverride(name = "horarioEmbarque", column = @Column(name = "HORARIO_EMBARQUE_RESERVA_VOLTA"))
    @AttributeOverride(name = "dadosVoo.numero", column = @Column(name = "NUMERO_VOO_RESERVA_VOLTA"))
    @AttributeOverride(name = "dadosVoo.horario", column = @Column(name = "HORARIO_VOO_RESERVA_VOLTA"))
    private ReservaVoo reservaVooVolta;

    @Embedded
    @AttributeOverride(name = "localizador", column = @Column(name = "LOCALIZADOR_RESERVA_VEICULO"))
    private ReservaVeiculo reservaVeiculo;

    protected Contratacao() {
        // Para o hibernate
    }
    public Contratacao(Cliente cliente, Pacote pacoteContratado, LocalDate inicioViagem, MonetaryAmount valorDescontoPromocional) {
        this.cliente = cliente;
        this.pacoteContratado = pacoteContratado;
        this.momentoCompra = LocalDate.now();
        this.valorPago = pacoteContratado.getPrecoBase().subtract(valorDescontoPromocional);
        this.valorDesconto = valorDescontoPromocional;
        this.valorTotal = pacoteContratado.getPrecoBase();
        this.periodoViagem = this.pacoteContratado.periodoViagemIniciandoEm(inicioViagem);
    }
}
