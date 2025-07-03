# 📦 Inventory Management - Arquitetura Hexagonal

Este projeto é um sistema de gerenciamento de estoque desenvolvido como estudo prático para aplicação da **Arquitetura Hexagonal (Ports and Adapters)**. O objetivo principal é demonstrar, mesmo em uma aplicação simples, os benefícios de uma arquitetura desacoplada, flexível e sustentável.

---

## 🧠 Objetivos

- Demonstrar a aplicabilidade da Arquitetura Hexagonal em sistemas de pequeno porte.
- Evidenciar os benefícios do **baixo acoplamento** e da **inversão de dependência (DIP)**.
- Exibir a possibilidade de troca de interfaces/adaptadores sem impactar o núcleo do sistema.
- Aplicar princípios **SOLID** no design do sistema.
- Servir como base técnica e acadêmica para trabalhos relacionados à engenharia de software.

---

## 🛠 Tecnologias Utilizadas

| Camada | Tecnologia |
|--------|-------------|
| **Linguagem** | Java 21 |
| **Frameworks** | Spring Boot, JUnit |
| **Persistência** | Postgres(configurado via docker-compose.yml) |
| **Frontend** | HTML + JavaScript |
| **Arquitetura** | Hexagonal (Ports and Adapters) |

---


---

## 🧪 Teste com Fake Interface

Foi realizada a substituição da interface externa original por uma **interface fake** para simular um cliente alternativo. Essa troca ocorreu **sem qualquer alteração no núcleo da aplicação**, o que comprova, na prática, a flexibilidade e o desacoplamento promovidos pela Arquitetura Hexagonal.

---

## 📖 Mais detalhes

Este projeto foi utilizado como parte de um trabalho acadêmico com abordagem qualitativa e método dedutivo. A aplicação prática visa reforçar os conceitos abordados na fundamentação teórica, sobretudo os princípios SOLID e o DIP.


---

## 💡 Um arquivo docker-compose.yml foi incluído no repositório para facilitar a criação e execução do ambiente da aplicação, permitindo que todos os serviços necessários sejam inicializados de forma automatizada.




