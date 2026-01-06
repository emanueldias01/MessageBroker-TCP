# MessageBroker TCP

## Visão Geral

Este projeto implementa um **Message Broker TCP** em Java, com suporte a **comunicação segura via SSL/TLS**, permitindo a troca de mensagens entre **produtores (PRODUCER)** e **consumidores (CONSUMER)** através de filas lógicas.

O broker mantém as mensagens em memória e distribui mensagens de produtores para consumidores conectados à mesma fila.

O projeto é composto por:

* **Servidor** (Broker)
* **Cliente** (Library de conexão)
* **Modelo de mensagens**
  
## Conceitos Principais

### Fila (Queue)

* Identificada por um `queueId`
* Criada dinamicamente no servidor quando não existe
* Compartilhada entre produtores e consumidores

### Roles

* `PRODUCER`: envia mensagens para uma fila
* `CONSUMER`: escuta mensagens de uma fila

### Segurança

* Comunicação criptografada com **SSL/TLS**
* Autenticação baseada em certificados
* Confiança configurada via **TrustStore**

---

## Fluxo de Conexão

1. Cliente cria socket SSL
2. Cliente envia `MessageQueueSelection`
3. Servidor:

   * Localiza ou cria a fila
   * Retorna confirmação de acesso
4. Cliente entra em modo PRODUCER ou CONSUMER

---

## Protocolo de Comunicação

### 1. Seleção de Fila

Objeto enviado pelo cliente ao conectar:

* `queueId`: Identificador da fila
* `role`: PRODUCER ou CONSUMER

Resposta do servidor:

* String: `"Acesso concedido a fila!"`

---

### 2. Produção de Mensagens

Fluxo:

1. Producer envia um objeto `Message`
2. Servidor:

   * Armazena a mensagem na fila
   * Retorna um `ServerMessage` com status

Status possíveis:

* `RECEIVED`: mensagem recebida com sucesso
* `ERROR_MESSAGE`: erro no processamento

---

### 3. Consumo de Mensagens

Fluxo:

1. Consumer se registra na fila
2. Servidor bloqueia até existir mensagem
3. Mensagem é enviada para todos os consumidores conectados

---

## Componentes do Servidor

### Classe `Server`

Responsabilidades:

* Criar `SSLServerSocket`
* Aceitar conexões de clientes
* Gerenciar filas (`QueueMessages`)
* Roteamento de mensagens

Principais métodos:

* `createServerSocket(int port)`
* `getSocketConnectionClient()`
* `resolveConnection(Socket socket)`

---

## Componentes do Cliente

### Classe `Client`

Responsabilidades:

* Criar conexão SSL com o servidor
* Selecionar fila e role
* Enviar ou consumir mensagens

Principais métodos:

* `createSocket(String ip, int port)`
* `sendConnectionMessage(MessageQueueSelection selection)`
* `sendMessage(Message message)`
* `listenMessage()`
* `disconnectSocket()`

---

## Exemplo de Uso

### Producer

```java
Client client = new Client();
client.createSocket("127.0.0.1", 8443);

MessageQueueSelection selection = new MessageQueueSelection("fila-pedidos", Role.PRODUCER);
client.sendConnectionMessage(selection);

client.sendMessage(new Message("Pedido criado"));
```

### Consumer

```java
Client client = new Client();
client.createSocket("127.0.0.1", 8443);

MessageQueueSelection selection = new MessageQueueSelection("fila-pedidos", Role.CONSUMER);
client.sendConnectionMessage(selection);

Message msg = client.listenMessage();
System.out.println(msg.getBodyMessage());
```

---

## Configuração de SSL/TLS

### Geração de Certificados

#### Servidor

```bash
keytool -genkeypair \
  -alias meuserver \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore keystore.p12 \
  -validity 365 \
  -dname "CN=127.0.0.1" \
  -ext "san=ip:127.0.0.1,dns:localhost"
```

Exportar certificado público:

```bash
keytool -export \
  -alias meuserver \
  -file server_publico.crt \
  -keystore keystore.p12
```

---

#### Cliente

```bash
keytool -import \
  -alias meuserver \
  -file server_publico.crt \
  -keystore cliente_truststore.p12 \
  -storetype PKCS12
```

---

### Configuração no Código

```java
System.setProperty("javax.net.ssl.trustStore", trustStorePath);
System.setProperty("javax.net.ssl.trustStorePassword", password);
System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
```
