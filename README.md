# Software-Para-Persistencia-de-Dados

Repositório destinado a materia de Software de persistencia de dados - UFG - 2025

## AT0707 - Criar e persistir uma entidade via ORM

Crie outro exemplo de entidade com pelo menos 3 tipos diferentes de atributos (String, data, decimal, etc).
Apresente um relatório com prints de telas com exemplos bem sucedidos com as 4 operações CRUD (Create, Retrieve, Update e Delete) no banco de dados SQLite. Comente cada tela produzida, explicando o respectivo codigo produzido para implementar e testar a operação.

## AT0708 - Serialização e desserialização de objetos

Crie métodos na classe Repositório para uma entidade inédita (que não seja Estudante) que realize as seguintes operações:

- String dumpData(String formato): fornecer uma string em um formato de saída (JSON ou XML) de todos objetos salvos no banco de dados.
- boolean dumpFile(String formato, File arquivo): serializa os objetos em dado formato em um arquivo de saída.
- <Entidade> createFromJSON(String json): recebe uma string em formato JSON para ser salvo em banco de dados, retorna a <Entidade> criada no banco de dados.
- <Entidade> createFromXML(String xml): recebe uma string em formato XML para ser salvo em banco de dados, retorna a <Entidade> criada no banco de dados.
- int importData(String formato, String data): recebe uma string em um formato de entrada (JSON ou XML) e importa para o banco de dados, retorna a quantidade de objetos importados.
- int importFile(String formato, File arquivo): importa objetos serializado em dado formato para o banco de dados.
