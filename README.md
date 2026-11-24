# Bikeshare | Programação 02

O objetivo do BIKESHARE é construir, como projeto da disciplina de **Programação Orientada a Objetos**, ministrada pelo professor **Mario Hozano** na **Universidade Federal de Alagoas (UFAL)**, a lógica de negócio completa de um sistema de bicicletas compartilhadas.

<p align="center">
  <img src="https://user-images.githubusercontent.com/91018438/204195385-acc6fcd4-05a7-4f25-87d1-cb7d5cc5c852.png" alt="animated" />
</p>

## Sobre o projeto

O Bikeshare gerencia usuarios, estações, bicicletas convencionais e elétricas e alugueis. O sistema permite cadastrar, consultar, alterar e remover usuários, cadastrar e consultar estações, adicionar e remover bicicletas, retirar e devolver bicicletas, calcular tarifas e manter o estado persistido entre execuções.

O projeto foi desenvolvido sem interface gráfica e sem banco de dados. A lógica de negócio é acessada pela classe `Facade` e validada por scripts de aceitação com EasyAccept.

Principais recursos implementados no Milestone 1:

- cadastro e consulta de usuários;
- alteração e remoção de usuários;
- cadastro e consulta de estações;
- gerenciamento de bicicletas convencionais e elétricas;
- retirada e devolução de bicicletas;
- cálculo de tarifas;
- planos avulso e assinante;
- métodos de pagamento;
- bloqueio por inadimplência;
- persistência em arquivo.

## Estrutura do projeto

- `src/br/ufal/ic/p2/bikeshare/` contém o código-fonte principal;
- `src/br/ufal/ic/p2/bikeshare/Facade.java` é o ponto de entrada dos testes;
- `src/br/ufal/ic/p2/bikeshare/SistemaBikeshare.java` coordena os repositórios;
- `src/br/ufal/ic/p2/bikeshare/usuario/` contém usuários e regras relacionadas;
- `src/br/ufal/ic/p2/bikeshare/estacao/` contém estações;
- `src/br/ufal/ic/p2/bikeshare/bicicleta/` contém bicicletas e repositórios;
- `src/br/ufal/ic/p2/bikeshare/aluguel/` contém alugueis e o serviço de aluguel;
- `src/br/ufal/ic/p2/bikeshare/plano/` e `pagamento/` concentram regras polimórficas;
- `tests/` contém os scripts de aceitação;
- `lib/easyaccept.jar` contém a biblioteca de testes.

## Requisitos

- Java JDK 11 ou superior;
- terminal PowerShell, CMD, Bash ou Zsh;
- acesso aos arquivos `lib/easyaccept.jar` e `tests/`.

No diretório raiz do projeto, execute os comandos a partir da pasta que contém `src/`, `tests/` e `lib/`.

## Como executar no Windows

No PowerShell:

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -cp "lib\easyaccept.jar" -d out $sources

java -cp "out;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1.txt
```

Para executar outro script de aceitação:

```powershell
java -cp "out;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us9.txt
```

Os scripts terminados em `_1.txt` devem ser executados depois do script base correspondente, sem apagar `bikeshare_data.ser` entre eles. Por exemplo:

```powershell
java -cp "out;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1.txt
java -cp "out;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1_1.txt
```

Antes de uma validação isolada, apague o arquivo de persistência existente:

```powershell
Remove-Item bikeshare_data.ser -ErrorAction SilentlyContinue
```

## Como executar no Linux

No terminal:

```bash
rm -rf out bikeshare_data.ser
mkdir out
javac -cp "lib/easyaccept.jar" -d out $(find src -name "*.java")

java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1.txt
```

Para outro script:

```bash
java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us9.txt
```

Exemplo de execução persistida:

```bash
rm -rf bikeshare_data.ser
java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1.txt
java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1_1.txt
```

Não apague `bikeshare_data.ser` entre o script base e o script `_1` correspondente.

## Como executar no macOS

No Terminal, use os mesmos comandos do Linux:

```bash
rm -rf out bikeshare_data.ser
mkdir out
javac -cp "lib/easyaccept.jar" -d out $(find src -name "*.java")

java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us1.txt
```

Para executar mais testes:

```bash
java -cp "out:lib/easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.bikeshare.Facade tests/us9.txt
```

## Scripts disponíveis

Os scripts usam o padrão `tests/usX.txt` e `tests/usX_1.txt`. Os primeiros scripts cobrem:

- US1: cadastro de usuários;
- US2: consulta de usuários;
- US3: alteração de usuários;
- US4: remoção de usuários;
- US5: cadastro de estações;
- US6: gerenciamento de bicicletas;
- US7: consulta de disponibilidade;
- US8: retirada de bicicletas;
- US9: devolução de bicicletas.
