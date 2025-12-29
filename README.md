# MessageBroker-TCP

## Como gerar os certificados para o uso de SSL
### No servidor:
 - Gerar o Keystore: (Substitua 127.0.0.1 pelo IP real se não for rodar na mesma máquina).
 ```bash
keytool -genkeypair -alias meuserver -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore eystore.p12 -validity 365 -dname "CN=127.0.0.1" -ext "san=ip:127.0.0.1,dns:localhost"
```

 - Exportar o Certificado Público:
 ```bash
keytool -export -alias meuserver -file server_publico.crt -keystore keystore.p12
 ```

 ### No Cliente:
 - Leve o arquivo `server_publico.crt` para a pasta do cliente e rode:
```bash
keytool -import -alias meuserver -file server_publico.crt -keystore cliente_truststore.p12 -storetype PKCS12
```

### Observações:

 - No código, defina as propriedades de localização, senha truststorte-type:
 ```java
    System.setProperty("javax.net.ssl.trustStore", trustStorePath);
    System.setProperty("javax.net.ssl.trustStorePassword", password);
    System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
 ```
