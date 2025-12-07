package com.pece.agencia.api.core.controller;

import com.pece.agencia.api.AbstractTest;
import com.pece.agencia.api.core.domain.model.Contratacao;
import com.pece.agencia.api.core.domain.model.Disponibilidade;
import com.pece.agencia.api.core.domain.model.Pacote;
import com.pece.agencia.api.core.domain.repository.ContratacaoRepository;
import com.pece.agencia.api.core.domain.repository.PacoteRepository;
import com.pece.agencia.api.core.utils.ContratacaoFixture;
import com.pece.agencia.api.core.utils.TokenFor;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.support.TransactionTemplate;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ContratacaoControllerTest extends AbstractTest {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PacoteRepository  pacoteRepository;

    @Autowired
    private ContratacaoFixture contratacaoFixture;

    private void setDisponibilidade(String pacoteId, int disponibilidade) {
        transactionTemplate.executeWithoutResult((tx) -> {
            Pacote pacote = pacoteRepository.findById(UUID.fromString(pacoteId)).get();
            pacote.setDisponibilidade(new Disponibilidade(disponibilidade));
            pacoteRepository.save(pacote);
        });
    }

    @AfterEach
    public void resetDisponibilidade() {
        setDisponibilidade("0018e0da-d903-4181-862b-0127bae799ea", 8);
        setDisponibilidade("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93", 10);
        setDisponibilidade("c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", 6);
    }



    // Helper to get the date 7 days from now in yyyy-MM-dd
    private String getDataIda() {
        return LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_DATE);
    }

    private @NotNull String validPayload() {
        return """
        {
          "dataIda": "%s",
          "cartao": {
            "numero": "4242424242424242",
            "cvc": "314",
            "validade": "2026-05"
          }
        }
        """.formatted(getDataIda());
    }

    @Nested
    @DisplayName("Obter uma contratacao")
    class SingleEntityTest {

        @Test
        @DisplayName("Deve retornar erro 400 se o id não for UUID válido")
        void deveRetornarErro400IdInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get("/v1/contratacoes/abc")
                            .header("Authorization", "Bearer " + token)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Deve retornar erro 401 se não autenticado")
        void deveRetornarErro401SemToken() throws Exception {
            String contratacaoId = "00000000-0000-0000-0000-000000000001";
            mockMvc.perform(get("/v1/contratacoes/" + contratacaoId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Deve retornar todos os atributos do cliente ao buscar por ID existente")
        void deveObterClientePorIdExistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String idExistente = "0d30f4e6-c175-4a2e-8a23-dd6db47adc3f";
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/contratacoes/" + idExistente)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(idExistente))
                    .andExpect(jsonPath("$.cliente.id").value("1914289b-1a28-433b-b472-d22b06f18841"))
                    .andExpect(jsonPath("$.cliente.nome").value("Camila Mendes"))
                    .andExpect(jsonPath("$.cliente.dataNascimento").value("1995-07-30"))
                    .andExpect(jsonPath("$.cliente.email").value("camila.mendes78@br.com"))
                    .andExpect(jsonPath("$.cliente.telefone").value("(49) 98929-3816"))
                    .andExpect(jsonPath("$.cliente.endereco.id").isNotEmpty())
                    .andExpect(jsonPath("$.cliente.endereco.cep").value("48389672"))
                    .andExpect(jsonPath("$.cliente.endereco.logradouro").value("Alameda do Comércio"))
                    .andExpect(jsonPath("$.cliente.endereco.numero").value("1217"))
                    .andExpect(jsonPath("$.cliente.endereco.complemento").isEmpty())
                    .andExpect(jsonPath("$.cliente.endereco.bairro").value("Jardim Paulista"))
                    .andExpect(jsonPath("$.cliente.endereco.localidade.id").value("3f725fe9-ba1f-4f8f-8542-1582e80c3724"))
                    .andExpect(jsonPath("$.cliente.endereco.localidade.nomeCidade").value("Brasília"))
                    .andExpect(jsonPath("$.cliente.endereco.localidade.estado").value("DF"))
                    .andExpect(jsonPath("$.cliente.endereco.localidade.codigoLocadoraVeiculo").value("D8561091"))
                    .andExpect(jsonPath("$.pacote.id").value("35ec6833-53be-4f36-9b69-cbd4bd87a6d3"))
                    .andExpect(jsonPath("$.pacote.nome").value("Retiro Espiritual em Diamantina"))
                    .andExpect(jsonPath("$.pacote.localidade.id").value("5b936770-56b7-40ba-b372-9f980906fcbb"))
                    .andExpect(jsonPath("$.pacote.localidade.nomeCidade").value("Diamantina"))
                    .andExpect(jsonPath("$.pacote.localidade.estado").value("MG"))
                    .andExpect(jsonPath("$.pacote.localidade.codigoLocadoraVeiculo").value("1BFFB83B"))
                    .andExpect(jsonPath("$.pacote.dataInicio").value("2024-11-01"))
                    .andExpect(jsonPath("$.pacote.dataFim").value("2024-11-08"))
                    .andExpect(jsonPath("$.pacote.disponibilidade").value("3"))
                    .andExpect(jsonPath("$.pacote.desconto").value("0.3"))
                    .andExpect(jsonPath("$.pacote.items[0].type").value("HOTEL"))
                    .andExpect(jsonPath("$.pacote.items[0].id").value("30768b5b-1e37-427a-8804-b1129264456b"))
                    .andExpect(jsonPath("$.pacote.items[0].preco").value("450.0"))
                    .andExpect(jsonPath("$.pacote.items[0].nomeHotel").value("Hotel das Palmeiras"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.id").isNotEmpty())
                    .andExpect(jsonPath("$.pacote.items[0].endereco.cep").value("21892910"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.logradouro").value("Alameda São João"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.numero").value("695"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.complemento").isEmpty())
                    .andExpect(jsonPath("$.pacote.items[0].endereco.bairro").value("Pinheiros"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.nomeCidade").value("Curitiba"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.estado").value("PR"))
                    .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                    .andExpect(jsonPath("$.pacote.items[0].email").value("reservas@hoteldaspalmeiras.com"))
                    .andExpect(jsonPath("$.pacote.items[0].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$.pacote.items[0].idPlataforma").value("379872"))
                    .andExpect(jsonPath("$.pacote.items[1].type").value("ALUGUEL_VEICULO"))
                    .andExpect(jsonPath("$.pacote.items[1].id").value("81c49a1e-a0ad-4dc3-82cf-44402a903120"))
                    .andExpect(jsonPath("$.pacote.items[1].preco").value("125.0"))
                    .andExpect(jsonPath("$.pacote.items[1].nomeLocadora").value("Rota Livre"))
                    .andExpect(jsonPath("$.pacote.items[1].categoriaCarro").value("Econômico"))
                    .andExpect(jsonPath("$.pacote.items[1].email").value("contato@rotalivre.com.br"))
                    .andExpect(jsonPath("$.pacote.items[1].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$.pacote.items[2].type").value("TRANSLADO_AEREO"))
                    .andExpect(jsonPath("$.pacote.items[2].id").value("f563b1e1-e386-4537-9ea9-1739876409de"))
                    .andExpect(jsonPath("$.pacote.items[2].preco").value("350.0"))
                    .andExpect(jsonPath("$.pacote.items[2].nomeCompanhia").value("FlyExpress"))
                    .andExpect(jsonPath("$.pacote.items[2].vooIdaNumero").value("EX1234"))
                    .andExpect(jsonPath("$.pacote.items[2].vooVoltaNumero").value("EX5678"))
                    .andExpect(jsonPath("$.pacote.items[2].vooIdaHora").value("08:00:00"))
                    .andExpect(jsonPath("$.pacote.items[2].vooVoltaHora").value("10:30:00"))
                    .andExpect(jsonPath("$.pacote.items[2].email").value("contato@flyexpress.com.br"))
                    .andExpect(jsonPath("$.pacote.items[2].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$.pacote.duracao").value("5"))
                    .andExpect(jsonPath("$.pacote.tipoDesconto").value("FIXO"))
                    .andExpect(jsonPath("$.pacote.custo").value("647.5"))
                    .andExpect(jsonPath("$.pacote.valorDesconto").value("277.5"))
                    .andExpect(jsonPath("$.dataIda").value("2025-09-10"))
                    .andExpect(jsonPath("$.dataVolta").value("2025-09-14"))
                    .andExpect(jsonPath("$.dataCompra").value("2025-02-22"))
                    .andExpect(jsonPath("$.valorTotal").value("285.9549"))
                    .andExpect(jsonPath("$.desconto").value("0.1"))
                    .andExpect(jsonPath("$.valorPago").value("317.7277"))
                    .andExpect(jsonPath("$.stripeChargeId").value("ch_3e09ab0d57f54cd5b200cc37"))
                    .andExpect(jsonPath("$.reservaHotel").value("RH4f97d4af"))
                    .andExpect(jsonPath("$.reservaVooIda").value("ETI27ba149f5d"))
                    .andExpect(jsonPath("$.assentoVooIda").value("40-F"))
                    .andExpect(jsonPath("$.embarqueVooIda").value("2026-02-25T00:00:00"))
                    .andExpect(jsonPath("$.reservaVooVolta").value("ETV6a0a96739a"))
                    .andExpect(jsonPath("$.assentoVooVolta").value("33-F"))
                    .andExpect(jsonPath("$.embarqueVooVolta").value("2025-05-19T00:00:00"))
                    .andExpect(jsonPath("$.reservaVeiculo").value("RVe1c3e076"));
        }

        @Test
        @DisplayName("Deve retornar 404 ao buscar contratacao inexistente")
        void deveRetornar404ClienteInexistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String idInexistente = "00000000-0000-0000-0000-000000009999";
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/contratacoes/" + idInexistente)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Contratar pacotes")
    class ContratarTests {

        @Nested
        @DisplayName("Contratacoes com sucesso")
        class SucessoTests {

            @Nested
            @DisplayName("Contratacoes com desconto em lote")
            class DescontoEmLotesTests {

                public static final String PACOTE_COM_DESCONTO_EM_LOTE = "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8";
                public static final String CAMILA_CLIENTE_ID = "1914289b-1a28-433b-b472-d22b06f18841";

                @BeforeEach
                @AfterEach
                public void resetContratacoes() {
                    contratacaoFixture.deleteContratacoesByPacote("c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", LocalDate.now().minusDays(1));
                }

                @Test
                @DisplayName("Sem contratacoes anteriores no lote, deve aplicar desconto 0%")
                public void semContratacoes(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(6);

                    mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c1d433cd-4f0c-496a-88d3-dcf4fefb50b8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isCreated())
                            .andExpect(header().exists("Location"))
                            .andExpect(x -> assertContratacaoResultPlataformaNg(x, "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", CAMILA_CLIENTE_ID, Money.of(BigDecimal.ZERO, "BRL")));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(5);

                }

                @Test
                @DisplayName("Com contratacoes anteriores ao periodo, deve aplicar desconto 0%")
                public void comContratacoesExpiradas(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    contratacaoFixture.criarContratacao(CAMILA_CLIENTE_ID, PACOTE_COM_DESCONTO_EM_LOTE, LocalDate.now().minusYears(1));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(6);

                    mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c1d433cd-4f0c-496a-88d3-dcf4fefb50b8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isCreated())
                            .andExpect(header().exists("Location"))
                            .andExpect(x -> assertContratacaoResultPlataformaNg(x, "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", CAMILA_CLIENTE_ID, Money.of(BigDecimal.ZERO, "BRL")));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(5);
                }

                @Test
                @DisplayName("Com contratacoes de outro cliente, deve aplicar desconto 0%")
                public void comContratacoesOutroCliente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    contratacaoFixture.criarContratacao("99972361-4c77-4a0a-aebf-59299b177d4a", PACOTE_COM_DESCONTO_EM_LOTE, LocalDate.now());

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(6);

                    mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c1d433cd-4f0c-496a-88d3-dcf4fefb50b8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isCreated())
                            .andExpect(header().exists("Location"))
                            .andExpect(x -> assertContratacaoResultPlataformaNg(x, "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", CAMILA_CLIENTE_ID, Money.of(BigDecimal.ZERO, "BRL")));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(5);
                }

                @Test
                @DisplayName("Com uma contratacao, deve aplicar desconto")
                public void comContratacoes(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    contratacaoFixture.criarContratacao(CAMILA_CLIENTE_ID, PACOTE_COM_DESCONTO_EM_LOTE, LocalDate.now());

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(6);

                    mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c1d433cd-4f0c-496a-88d3-dcf4fefb50b8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isCreated())
                            .andExpect(header().exists("Location"))
                            .andExpect(x -> assertContratacaoResultPlataformaNg(x, "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", CAMILA_CLIENTE_ID, Money.of(new BigDecimal(40.5), "BRL")));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(5);
                }

                @Test
                @DisplayName("Com cem contratacoes, deve aplicar desconto maximo")
                public void comMuitasContratacoes(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    for (int i=0; i<100; i++) {
                        contratacaoFixture.criarContratacao(CAMILA_CLIENTE_ID, PACOTE_COM_DESCONTO_EM_LOTE, LocalDate.now());
                    }

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(6);

                    mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c1d433cd-4f0c-496a-88d3-dcf4fefb50b8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isCreated())
                            .andExpect(header().exists("Location"))
                            .andExpect(x -> assertContratacaoResultPlataformaNg(x, "c1d433cd-4f0c-496a-88d3-dcf4fefb50b8", CAMILA_CLIENTE_ID, Money.of(new BigDecimal("121.5"), "BRL")));

                    assertThat(disponibilidadePacote(PACOTE_COM_DESCONTO_EM_LOTE)).isEqualTo(5);
                }
            }

            private int disponibilidadePacote(String id) {
                return transactionTemplate.execute((tx) -> {
                    Pacote pacote = pacoteRepository.findById(UUID.fromString(id)).get();
                    return pacote.getDisponibilidade().valor();
                });
            }

            private void assertContratacaoResultPlataformaRegular(MvcResult result, String pacoteId, String clientId, MonetaryAmount valorDescontoPromocional) {
                transactionTemplate.executeWithoutResult((tx) -> {
                    String location = result.getResponse().getHeader("Location");
                    String id = location.substring(location.lastIndexOf("/") + 1);
                    assertContratacaoResultPlataformaRegular(id, pacoteId, clientId, valorDescontoPromocional);
                });
            }

            private void assertContratacaoResultPlataformaRegular(String contratacaoId, String pacoteId, String clientId, MonetaryAmount valorDescontoPromocional) {
                Contratacao contratacao = contratacaoRepository.findById(UUID.fromString(contratacaoId)).get();

                assertThat(contratacao.getId().toString()).isEqualTo(contratacaoId);
                assertThat(contratacao.getCliente().getId()).isEqualTo(UUID.fromString(clientId));
                assertThat(contratacao.getPacoteContratado().getId()).isEqualTo(UUID.fromString(pacoteId));
                assertThat(contratacao.getPeriodoViagem().inicio()).isNotNull();
                assertThat(contratacao.getPeriodoViagem().fim()).isEqualTo(contratacao.getPeriodoViagem().inicio().plus(contratacao.getPacoteContratado().getDuracaoViagem()));
                assertThat(contratacao.getMomentoCompra()).isEqualTo(LocalDate.now());
                assertThat(contratacao.getValorTotal()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase());
                assertThat(contratacao.getValorDesconto()).isEqualTo(valorDescontoPromocional);
                assertThat(contratacao.getValorPago()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase().subtract(valorDescontoPromocional));
                assertThat(contratacao.getCodigoPagamento()).isNotNull();
                assertThat(contratacao.getReservaHotel().numero()).hasSizeLessThanOrEqualTo(12);
                assertThat(contratacao.getReservaVooIda().eticket()).isNotNull();
                assertThat(contratacao.getReservaVooIda().assento().numero()).isNotNull();
                assertThat(contratacao.getReservaVooIda().horarioEmbarque()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().eticket()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().assento().numero()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().horarioEmbarque()).isNotNull();
                assertThat(contratacao.getReservaVeiculo().localizador()).isNotBlank();
            }

            private void assertContratacaoResultPlataformaNg(MvcResult result, String pacoteId, String clientId, MonetaryAmount valorDescontoPromocional) {
                transactionTemplate.executeWithoutResult((tx) -> {
                    String location = result.getResponse().getHeader("Location");
                    String id = location.substring(location.lastIndexOf("/") + 1);
                    assertContratacaoResultPlataformaNg(id, pacoteId, clientId, valorDescontoPromocional);
                });
            }

            private void assertContratacaoResultPlataformaNg(String contratacaoId, String pacoteId, String clientId, MonetaryAmount valorDescontoPromocional) {
                Contratacao contratacao = contratacaoRepository.findById(UUID.fromString(contratacaoId)).get();

                assertThat(contratacao.getId().toString()).isEqualTo(contratacaoId);
                assertThat(contratacao.getCliente().getId()).isEqualTo(UUID.fromString(clientId));
                assertThat(contratacao.getPacoteContratado().getId()).isEqualTo(UUID.fromString(pacoteId));
                assertThat(contratacao.getPeriodoViagem().inicio()).isNotNull();
                assertThat(contratacao.getPeriodoViagem().fim()).isEqualTo(contratacao.getPeriodoViagem().inicio().plus(contratacao.getPacoteContratado().getDuracaoViagem()));
                assertThat(contratacao.getMomentoCompra()).isEqualTo(LocalDate.now());
                assertThat(contratacao.getValorTotal()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase());
                assertThat(contratacao.getValorDesconto()).isEqualTo(valorDescontoPromocional);
                assertThat(contratacao.getValorPago()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase().subtract(valorDescontoPromocional));
                assertThat(contratacao.getCodigoPagamento()).isNotNull();
                assertThat(contratacao.getReservaHotel().numero()).hasSizeGreaterThan(20);
                assertThat(contratacao.getReservaVooIda().eticket()).isNotNull();
                assertThat(contratacao.getReservaVooIda().assento()).isNotNull();
                assertThat(contratacao.getReservaVooIda().horarioEmbarque()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().eticket()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().assento()).isNotNull();
                assertThat(contratacao.getReservaVooVolta().horarioEmbarque()).isNotNull();
                assertThat(contratacao.getReservaVeiculo()).isNotNull();
            }

            @Test
            @DisplayName("Deve contratar pacote com sucesso usando cartão válido (cliente fidelizado)")
            void deveContratarPacoteComSucessoClienteFidelizado(@TokenFor(username = "joão.ferreira", password = "x") String token) throws Exception {
                assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(8);

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isCreated())
                        .andExpect(header().exists("Location"))
                        .andExpect(x -> assertContratacaoResultPlataformaRegular(x,  "0018e0da-d903-4181-862b-0127bae799ea", "9c33a2f5-bd6f-442d-be44-48ccd5dfe2c2", Money.of(new BigDecimal("231.25"), "BRL")));

                assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(7);
            }

            @Test
            @DisplayName("Deve contratar pacote com sucesso usando cartão válido (plataforma regular)")
            void deveContratarPacoteComSucessoPlataformaRegular(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(8);

                mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isCreated())
                        .andExpect(header().exists("Location"))
                        .andExpect(x -> assertContratacaoResultPlataformaRegular(x,  "0018e0da-d903-4181-862b-0127bae799ea", "1914289b-1a28-433b-b472-d22b06f18841", Money.of(new BigDecimal("194.25"), "BRL")));

                assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(7);
            }

            @Test
            @DisplayName("Deve contratar pacote com sucesso usando cartão válido (plataforma ng)")
            void deveContratarPacoteComSucessoPlataformaNg(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                assertThat(disponibilidadePacote("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93")).isEqualTo(10);

                mockMvc.perform(post("/v1/pacotes/2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isCreated())
                        .andExpect(header().exists("Location"))
                        .andExpect(x -> assertContratacaoResultPlataformaNg(x, "2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93", "1914289b-1a28-433b-b472-d22b06f18841", Money.of(new BigDecimal("511.7"), "BRL")));

                assertThat(disponibilidadePacote("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93")).isEqualTo(9);
            }
        }


        @Nested
        @DisplayName("Contratacoes com falhas")
        class FalhaTests {
            @Nested
            @DisplayName("Contratacoes com falhas de infra-estrutura")
            class FalhaInfraEstruturaTests {
                @Test
                @DisplayName("Deve retornar erro se stripe estiver fora do ar")
                void deveRetornarErroStripeFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    stripeToxyproxy.disable();

                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isServiceUnavailable())
                            .andExpect(header().exists("Retry-After"));
                }

                @Test
                @DisplayName("Deve retornar erro se plataforma de hotaleria regular estiver fora do ar")
                void deveRetornarErroPlataformaHotelariaRegularFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    plataformaHotelRegularToxyproxy.disable();

                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isServiceUnavailable())
                            .andExpect(header().exists("Retry-After"));
                }

                @Test
                @DisplayName("Deve retornar erro se plataforma de hotaleria ng estiver fora do ar")
                void deveRetornarErroPlataformaHotelariaNGFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    plataformaHotelNgToxyproxy.disable();

                    mockMvc.perform(post("/v1/pacotes/2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isServiceUnavailable())
                            .andExpect(header().exists("Retry-After"));
                }

                @Test
                @DisplayName("Deve retornar erro se plataforma de companhia aerea estiver fora do ar")
                void deveRetornarErroPlataformaCompanhiaAereaFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    plataformaCompanhiaAereaToxyproxy.disable();

                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isServiceUnavailable())
                            .andExpect(header().exists("Retry-After"));
                }

                @Test
                @DisplayName("Deve retornar erro se plataforma de locadora veiculos estiver fora do ar")
                void deveRetornarErroPlataformalocadoraVeiculosFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    plataformaLocadoraVeiculoToxyproxy.disable();

                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isServiceUnavailable())
                            .andExpect(header().exists("Retry-After"));
                }
            }


            @Nested
            @DisplayName("Contratacoes com falhas de negocios")
            class FalhaNegociosTests {

                @Test
                @DisplayName("Deve retornar erro ao tentar contratar pacote sem disponibilidade")
                void deveRetornarErroPacoteSemDisponibilidade(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    mockMvc.perform(post("/v1/pacotes/c56c9b84-c83d-4cec-8781-488d97af483d/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isUnprocessableEntity());
                }

                @Test
                @DisplayName("Deve retornar erro ao tentar contratar pacote já vencido")
                void deveRetornarErroPacoteVencido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {

                    mockMvc.perform(post("/v1/pacotes/2836ad8e-f71f-4f4d-909b-973a2567384b/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isUnprocessableEntity());
                }

                @Test
                @DisplayName("Deve retornar erro ao contratar com cartao negado")
                void deveFalharAoUsarCartaoNegado(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    String payload = """
                            {
                              "dataIda": "%s",
                              "cartao": {
                                "numero": "4000008260003178",
                                "cvc": "314",
                                "validade": "2026-05"
                              }
                            }
                            """.formatted(getDataIda());
                    mockMvc.perform(post("/v1/pacotes/ce507a91-29c9-42a9-b0d2-ba0a3295cea8/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(payload))
                            .andExpect(status().isPaymentRequired());
                }

                @Test
                @DisplayName("Deve retornar erro ao enviar payload sem campo obrigatório (cartao)")
                void deveRetornarErroPayloadSemCartao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    String payload = """
                            {
                              "dataIda": "%s"
                            }
                            """.formatted(getDataIda());
                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(payload))
                            .andExpect(status().isBadRequest());
                }

                @Test
                @DisplayName("Deve retornar erro ao enviar data de ida no passado")
                void deveRetornarErroDataIdaNoPassado(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    String dataPassada = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE);
                    String payload = """
                            {
                              "dataIda": "%s",
                              "cartao": {
                                "numero": "4242424242424242",
                                "cvc": "314",
                                "validade": "2026-05"
                              }
                            }
                            """.formatted(dataPassada);
                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(payload))
                            .andExpect(status().isBadRequest());
                }

                @Test
                @DisplayName("Deve retornar erro ao tentar contratar sem autenticação (sem token)")
                void deveRetornarErroSemToken() throws Exception {
                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isUnauthorized());
                }

                @Test
                @DisplayName("Deve retornar erro ao tentar contratar com token inválido")
                void deveRetornarErroTokenInvalido() throws Exception {
                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer token_invalido")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isUnauthorized());
                }


                @Test
                @DisplayName("Deve retornar erro ao enviar payload com tipos errados (numero do cartão como inteiro)")
                void deveRetornarErroPayloadTiposErrados(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                    String payload = """
                            {
                              "dataIda": "%s",
                              "cartao": {
                                "numero": 4242424242424242,
                                "cvc": "314",
                                "validade": "4242424242424242"
                              }
                            }
                            """.formatted(getDataIda());
                    mockMvc.perform(post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(payload))
                            .andExpect(status().isBadRequest());
                }

                @Test
                @DisplayName("Deve retornar erro ao tentar contratar pacote inexistente")
                void deveRetornarErroPacoteInexistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {

                    mockMvc.perform(post("/v1/pacotes/00000000-0000-0000-0000-000000000000/contratacoes")
                                    .header("Authorization", "Bearer " + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(validPayload()))
                            .andExpect(status().isNotFound());
                }
            }
        }
    }
}