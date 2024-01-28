# kafka-avro-schema-quickstart

Éste proyecto utiliza Quarkus, the Supersonic Subatomic Java Framework.

Los objetivos del proyecto son los siguientes:

1. Utilizar avro para definir un mensaje estandarizado y consistenciar la información que se publica en un tópico de kafka.
2. Avro debe generar las clases necesarias para validar el schema del mensaje.
3. Se utilizará un Schema Registry para validar la consistencia de los mensajes.
4. Se expone un endpoint para producir un mensaje un tópico y un endpoint para obtener los mensajes producidos, leidos desde el tópico.
5. Desactivar los "Dev Services" para utilizar contenedore externos, se crea el archivo docker-compose.yaml.
6. Agregar lombok a la implementación de quarkus. 

Mas información sobre Quarkus: https://quarkus.io/ .

## Versiones recomendadas de maven y jdk

```shell script
$ mvn --version
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: C:\dev\apache-maven-3.8.6
Java version: 17.0.2, vendor: Oracle Corporation, runtime: C:\dev\java\jdk-17.0.2
Default locale: es_PE, platform encoding: Cp1252
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"

$ java --version
openjdk 17.0.2 2022-01-18
OpenJDK Runtime Environment (build 17.0.2+8-86)
OpenJDK 64-Bit Server VM (build 17.0.2+8-86, mixed mode, sharing)
```

## Pasos para Ejecutar con docker compose

Se requiere levantar el docker-compose con el siguiente comando en git-bash con docker y docker-compose instalado:
```shell script
docker-compose up
```
Se deben activar las siguientes lineas en el archivo src/main/resources/application.properties, con esto desactivamos los dev services de quarkus y conectamos los servicios levantados desde docker-compose.
```shell script
quarkus.devservices.enabled=false
kafka.bootstrap.servers=PLAINTEXT://localhost:9092
mp.messaging.connector.smallrye-kafka.schema.registry.url=http://localhost:8081
mp.messaging.connector.smallrye-kafka.apicurio.registry.url=http://localhost:8081/apis/registry/v2
```
Iniciar el aplicativo con el siguiente comando maven:
```shell script
./mvnw clean compile quarkus:dev
```


## Pruebas con Curl

En una ventana de git-bash o bash nos suscribimos al endpoint de mensajes publicados:
```shell script
 curl -N http://localhost:8080/consumed-movies
```

En otra ventana con git-bash o bash invocamos al endpoint de producción de mensajes con los siguientes comandos:
```shell script
curl --header "Content-Type: application/json"   --request POST   --data '{"title":"Gladiator","year":2000}'   http://localhost:8080/movies
curl --header "Content-Type: application/json"   --request POST   --data '{"title":"The Pianist","year":2002}'   http://localhost:8080/movies
curl --header "Content-Type: application/json"   --request POST   --data '{"title":"The Last King of Scotland","year":2006}'   http://localhost:8080/movies
curl --header "Content-Type: application/json"   --request POST   --data '{"title":"Milk","year":2008}'   http://localhost:8080/movies
curl --header "Content-Type: application/json"   --request POST   --data '{"title":"Crazy Heart","year":2009}'   http://localhost:8080/movies
```


Obtenemos los mensajes formateados y obtenidos desde el topico de kafka:
```shell script
$ curl -N http://localhost:8080/consumed-movies
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:--  0:00:08 --:--:--     0data:'Gladiator' from 2000

100    28    0    28    0     0      3      0 --:--:--  0:00:08 --:--:--     5data:'The Pianist' from 2002

data:'The Last King of Scotland' from 2006

data:'Milk' from 2008

data:'Crazy Heart' from 2009

100   155    0   155    0     0      4      0 --:--:--  0:00:35 --:--:--     0
```


## Referencias guías

- Apicurio Registry - Avro ([guide](https://quarkus.io/guides/kafka-schema-registry-avro)): Use Apicurio as Avro schema registry
- SmallRye Reactive Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-reactive-getting-started)): Connect to Kafka with Reactive Messaging
