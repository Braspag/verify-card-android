# Verify Card [![Status](https://travis-ci.com/Braspag/verify-card-android.svg?branch=master)](https://travis-ci.com/Braspag/verify-card-android) [ ![Download](https://api.bintray.com/packages/braspag/verify-card/verify-card/images/download.svg) ](https://bintray.com/braspag/verify-card/verify-card/_latestVersion)

Para consultar dados de um cartão, é necessário fazer um POST no serviço VerifyCard. O VerifyCard é composto por dois serviços: Zero Auth e Consulta BIN. O Zero Auth é um serviço que indentifica se um cartão é válido ou não, através de uma operação semelhante a uma autorização, porém com valor de R$ 0,00. A Consulta BIN é um serviço disponível para clientes Cielo 3.0 que retorna as características do BIN tais como bandeira e tipo do cartão, a partir do BIN (6 primeiros dígitos do cartão). Os dois serviços podem ser consumidos simultaneamente através do VerifyCard, conforme o exemplo baixo. Também é possível que o processo de autorização seja condicionado automaticamente a um retorno de sucesso do ZeroAuth. Para habilitar este fluxo, por favor, entre em contato com nosso time de suporte.

## Instalação

- Será necessário adicionar a seguinte dependência ao **build.gradle** do seu app module:

```groovy
    implementation 'br.com.braspag:verify-card:VERSION'
```

- Ou baixar o pacote aar manualmente através da versão mais atual encontrada em [releases](https://github.com/Braspag/verify-card-android/releases), adicionar esse pacote na pasta *libs* do seu app module e depois adicionar a seguinte linha ao *build.gradle* do seu app module:

```groovy
    implementation files('libs/verify-card-release.aar')
```

## Modo de uso

### Configuração

Para iniciar o cliente do SDK será necessário informar *Client id*, *Client secret*, *Merchant Id* e o *Ambiente*:

```kotlin
val verifyCard = VerifyCard(
    clientId = "CLIENT-ID",
    clientSecret = "CLIENT-SECRET",
    merchantId = "MERCHANT-ID",
    environment = VerifyCardEnvironment.SANDBOX
)
```

### Utilização

```kotlin
verifyCard.verify(
    request = VerifyCardRequest(
        provider = "Cielo30",
        card = Card(
            cardNumber = "1111222233334444",
            holder = "Maurici F Junior",
            securityCode = "123",
            expirationDate = "01/2030",
            brand = "Master",
            type = cardType.DEBIT_CARD
        )
    )
) {
    // callback
}
```
