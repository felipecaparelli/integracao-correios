# integracao-correios [![Build Status](https://travis-ci.org/felipecaparelli/integracao-correios.svg?branch=master)](https://travis-ci.org/felipecaparelli/integracao-correios)
Biblioteca Java para Integração com o Web Service dos Correios (Prazo e Frete)

## Índice

- [Introdução](#intro)
- [Configurações](#configs)
- [Uso básico](#basic)
- [Parâmetros Complementares](#params)
- [Dependências](#libs)

## <a name="intro"></a> Introdução

Este projeto tem como propósito oferecer uma alternativa simples para a execução do [web service SOAP](http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?WSDL) provido pela empresa **Correios** para os recursos de consulta de prazo e preço do frete. O serviço SOAP do Correios não é muito complexo, mas a idéia é abstrair o uso do SOAP para que você possa focar apenas na integração da sua loja virtual com o serviço que precisa.

## <a name="configs"></a> Configurações

Se você deseja utilizar este projeto como uma biblioteca do seu projeto deverá seguir os seguintes passos:

1. Baixe o projeto `git clone https://github.com/felipecaparelli/integracao-correios.git`
2. Execute o maven `mvn clean install`
3. Se você não utiliza o maven em seu projeto, copie a biblioteca /integracao-correios/correios-client/target/correios-client-0.0.1-SNAPSHOT.jar para seu diretório WEB-INF/lib, junto com uma tonelada de dependências listadas no [final deste arquivo](#libs).
4. Voila!


## <a name="basic"></a> Uso básico


O serviço já vem com os dados básicos da pesquisa preenchidos, sendo o serviço padrão utilizado o 'SEDEX Varejo'

```java
			CorreiosPrecoPrazo result = new ConsultaCorreios().calcularPrecoPrazo("06053040", "80540220")[0];

			System.out.println("Preço do Frete: " + result.getPrecoFrete());
			System.out.println(String.format("Prazo de Entrega: %d dias", result.getPrazoEntrega()));
```

Deixo claro que o objeto que construo no retorno não possui a mesma interface do objeto retornado pelo serviço do Correios, mas contém todos os dados que eles devolvem. Na realidade, a intenção do objeto que eu retorno é deixar mais claras as informações providas.

Como o foco do projeto é sem um facilitador para programadores Java na integração com o serviço dos Correios, os dados parametrizados e retornados são em geral constituídos de variaveis que sejam coerentes com os tipos necessários (por exemplo, valores monetários que utilizaremos para cálculos são retornados como Double). 

## <a name="params"></a> Parâmetros Complementares

A API permite parametrizar todos os dados aceitos no web service do Correios, seguindo as mesmas [regras da validação do mesmo](https://www.correios.com.br/para-voce/correios-de-a-a-z/pdf/calculador-remoto-de-precos-e-prazos/manual-de-implementacao-do-calculo-remoto-de-precos-e-prazos):

```java
			CorreiosPrecoPrazo result = new ConsultaCorreios()
													.servicos(CorreiosTipoServico.PAC_VAREJO) //tipo de serviço 'PAC'
													.entregarEmMaos(IndicadorSN.SIM) //indicador que define se a entrega deve ser em mãos
													.calcularPrecoPrazo("06053040", "80540220")[0];

			System.out.println("Preço do Frete: " + result.getPrecoFrete());
			System.out.println(String.format("Prazo de Entrega: %d dias", result.getPrazoEntrega()));
```

Perceba que o retorno é um array de objetos do tipo CorreiosPrecoPrazo, pois o serviço do Correios retorna 1 elemento para cada tipo de serviço consultado. Se você parametrizar mais de um serviço terá múltiplos retornos:

```java
			CorreiosPrecoPrazo[] results = new ConsultaCorreios()
													.servicos(CorreiosTipoServico.PAC_VAREJO, CorreiosTipoServico.SEDEX_10_VAREJO)
													.calcularPrecoPrazo("06053040", "80540220");

			for (CorreiosPrecoPrazo result : results) {
				System.out.println("Preço do Frete: " + result.getPrecoFrete());
				System.out.println(String.format("Prazo de Entrega: %d dias", result.getPrazoEntrega()));
			}
```

Essa é uma versão inicial e tem muito para melhorar. Se tiver interesse em evoluir e implementar os outros serviços disponibilizados pelo Correios fique a vontade para fazer um fork ou colaborar com este projeto ;)


## <a name="libs"></a> Dependências

Todas as bibliotecas deste projeto estão diretamente ligadas à biblioteca [soap-ws](https://github.com/reficio/soap-ws), que é utilizada para realizar a chamada SOAP do web service do Correios.

* junit-4.12.jar
* hamcrest-core-1.3.jar
* soap-builder-1.0.0-SNAPSHOT.jar
* soap-common-1.0.0-SNAPSHOT.jar
* wsdl4j-1.6.2.jar
* commons-lang3-3.1.jar
* xmlunit-1.3.jar
* soap-legacy-1.0.0-SNAPSHOT.jar
* xml-apis-1.0.b2.jar
* batik-ext-1.7.jar
* xercesImpl-2.10.0.jar
* xbean-fixed-2.4.0.jar
* xbean_xpath-2.4.0.jar
* xmlbeans-xmlpublic-2.5.0.jar
* stax-api-1.0.1.jar
* jsr173-1.0.jar
* saxon-9.1.0.8.jar
* saxon-dom-9.1.0.8.jar
* wsrf-xbeans-1.0.jar
* XmlSchema-1.4.5.jar
* soap-client-1.0.0-SNAPSHOT.jar
* httpclient-4.2.3.jar
* httpcore-4.2.2.jar
* log4j-1.2.17.jar
* commons-logging-1.1.1.jar
* commons-io-2.3.jar
* commons-codec-1.6.jar
* guava-osgi-11.0.1.jar
* soap-server-1.0.0-SNAPSHOT.jar
* spring-ws-core-2.1.1.RELEASE.jar
* spring-xml-2.1.1.RELEASE.jar
* spring-context-3.1.2.RELEASE.jar
* spring-expression-3.1.2.RELEASE.jar
* spring-asm-3.1.2.RELEASE.jar
* spring-aop-3.1.2.RELEASE.jar
* aopalliance-1.0.jar
* spring-oxm-3.1.2.RELEASE.jar
* commons-lang-2.5.jar
* spring-web-3.1.2.RELEASE.jar
* spring-webmvc-3.1.2.RELEASE.jar
* spring-context-support-3.1.2.RELEASE.jar
* stax-api-1.0-2.jar
* spring-core-3.1.2.RELEASE.jar
* spring-beans-3.1.2.RELEASE.jar
* jetty-embedded-6.1.26.jar
* jetty-6.1.26.jar
* servlet-api-2.5-20081211.jar
* jetty-sslengine-6.1.26.jar
* jsp-api-2.1.jar
* jetty-java5-threadpool-6.1.26.jar
* jetty-util-6.1.26.jar
* slf4j-api-1.6.6.jar
* slf4j-log4j12-1.6.6.jar
