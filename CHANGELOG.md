# Changelog

Todas as alterações relevantes do projeto são registradas aqui.

## [V1](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v1)
- Versão inicial do projeto

## [V2](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v2)
- Mudanças após alteração da linguagem ubíqua (**testes falhando**) 

## [V3](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v3)
- Inclusão de geração via swagger 
- Alteração de delegate de localidade para funcionamento/servir de exemplo para alunos (**testes falhando**)

## [V4](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v4)
- Inclusão de mappers no controller

## [V5](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v5)
- Refactoring de mappers com delegação

## [V6](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v6)
- Inclusão de [map-struct](https://mapstruct.org/)

## [V7](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v7)
- Refactoring de serviço de contratação dado o principio da responsabilidade única
- Inclusão de cascading para salvar items associados à contratação

## [V8](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v8)
- Inclusão de artefatos para preparação da tarefa plataforma-ng (**testes falhando**)
- Novas coleções/ambientes do postman tratando da plataforma-ng

## <del>[V9](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v9-hotel-ng-sem-acl)</del>
- <del> Implementação da plataforma NG (**sem ACL**)</del>

## [V9](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v9)
- Implementação da plataforma NG (**com ACL**)

## [V10](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v10)
- Inclusão de cadeia de responsabilidade

## [V11](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v11)
- Inclusão de ACL para locação de veiculos

## [V12](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v12)
- Inclusão de ACL para reserva de translado aéreo
- Inclusão de ACL para gateway de pagamentos

## [V13](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v13)
- Setup do spring modulith (**testes falhando**)
- Modularização de locação de veículos

## [V14](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v14)
- Modularização de translado aéreo
- Modularização de pagamentos
- Modularização de hotelaria

## [V15](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v15)
- Inclusão de falhas de negócio em pagamentos (**testes falhando**)
- Inclusão de tratamento de erros em pagamento (**testes falhando**)
- Inclusão de tratamento de erros em outros contextos 
  - Hotelaria
  - Locação de Veículos
  - Translado Aéreo

## [V16](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v16)
- Inclusão de controller advisers para tratamento de erros

## [V17](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v17)
- Inclusão de endereço como objeto valor (**testes falhando**)
- Ajustes de regressao de endereco
  - Testes não testando o valor de ID de endereço de modo ipsis litteris
  - Ajustes em mapeadores da v1 para usar os IDs das entidade contendo endereços

## [V18](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v18)
- Inclusao de alterações de DBA para VOs (**testes falhando**)

## [V19](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v19)
- Inclusao de VOs relativos às alterações do DBA
- Inclusão de outros VOs
  - Email 
  - CEP
  - Telefone
  - ReservaHotel
  - ReservaVeiculo
  - ReservaVoo.Assento
  - ReservaVoo.ETicket
  - Disponibilidade
  - Uso de [Period](https://docs.oracle.com/javase/8/docs/api/java/time/Period.html) para representar duração do pacote
  - CodigoPagamento
- Inclusao de valor monetário ([moneta](https://github.com/JavaMoney/jsr354-ri)) (**compilacao falhando** -- problema de tipagem)
- Ajustes em erro de compilacao por tipagem faltando (**testes falhando**)
- Correção de erros de testes em para correção de bug de falta de tipagem
- Inclusão de VO de percentual

## [V20](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v20)

- Inclusao de VOs em contexto delimitado de translado aereo
- Inclusao de VOs em contexto delimitado de hotelaria
- Inclusao de VOs em contexto delimitado de pagamentos
- Inclusao de VOs em contexto delimitado de locacao de veículos

## [V21](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v21)

- Inclusao de reuso de VOs
  - Email
  - Periodo
  - DadosCartao

## [V22](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v22)

- Inclusão de desconto de fidelidade (1a versão) 

## [V23](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v23)

- Inclusão de camadas de architecture 

## [V24](https://github.com/bsofiato/tecnicas-projeto-software/tree/branches/v24)

- Inclusão de artefatos para a implementação de desconto de contratacao em lotes (**testes quebrando**)

