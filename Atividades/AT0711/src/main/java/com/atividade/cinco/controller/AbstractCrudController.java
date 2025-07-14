package com.atividade.cinco.controller;

import com.atividade.cinco.service.AbstractCrudService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Controller;

/**
 * Controlador base genérico para operações CRUD.
 *
 * @param <E>  Entidade de domínio
 * @param <V>  ViewModel usado na TableView
 * @param <ID> Tipo da chave primária
 */
@Controller
public abstract class AbstractCrudController<E, V, ID> {

    // Fornece o repositório específico
    protected abstract AbstractCrudService<E, ID> getService();

    // Converte entidade em ViewModel
    protected abstract V modelToView(E entidade);

    // Converte dados da tela em entidade
    protected abstract E viewToModel();

    protected abstract E performCreate(E entidade);

    protected abstract E performUpdate(ID id);

    // Preenche os campos da tela com dados
    protected abstract void preencherCampos(V item);

    // Limpa os campos da tela
    protected abstract void limparCampos();

    // Habilita ou desabilita os campos
    protected abstract void desabilitarCampos(boolean desabilitado);

    // Habilita ou desabilita botões
    protected abstract void desabilitarBotoes(
            boolean adicionar, boolean atualizar,
            boolean deletar, boolean cancelar, boolean salvar);

    // Retorna a tabela
    protected abstract TableView<V> getTabela();

    // Extrai a chave primária do ViewModel
    protected abstract ID getIdFromViewModel(V viewModel);

    // Seta o ID na entidade
    protected abstract void setIdOnEntity(E entidade, ID id);

    // Inicializa dados e vincula seleção
    public void initialize() {
        getTabela().setItems(loadAll());
        getTabela().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        preencherCampos(newSelection);
                        desabilitarBotoes(false, false, false, true, true);
                    }
                });
        desabilitarCampos(true);
    }

    // Carrega todos os registros
    protected ObservableList<V> loadAll() {
        ObservableList<V> lista = FXCollections.observableArrayList();
        for (E entidade : getService().loadAll()) {
            lista.add(modelToView(entidade));
        }
        return lista;
    }

    // Ação Adicionar
    public void onAdicionar() {
        getTabela().getSelectionModel().clearSelection();
        desabilitarCampos(false);
        desabilitarBotoes(true, true, true, false, false);
        limparCampos();
    }

    // Ação Salvar
    public void onSalvar() {
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        try {
            E entidadeAtualizada;

            if (selecionado != null) {
                // UPDATE: Delega para o método específico da classe filha
                ID id = getIdFromViewModel(selecionado);
                entidadeAtualizada = performUpdate(id); // <--- MUDANÇA CHAVE

                V viewAtualizada = modelToView(entidadeAtualizada);
                int index = getTabela().getItems().indexOf(selecionado);
                getTabela().getItems().set(index, viewAtualizada);
                getTabela().getSelectionModel().select(viewAtualizada);
            } else {
                // CREATE: Delega para o método específico da classe filha
                E novaEntidade = viewToModel(); // Pega os dados da tela
                entidadeAtualizada = performCreate(novaEntidade); // <--- MUDANÇA CHAVE

                V viewItem = modelToView(entidadeAtualizada);
                getTabela().getItems().add(viewItem);
                getTabela().getSelectionModel().select(viewItem);
            }

            desabilitarCampos(true);
            // O estado dos botões pode ser ajustado conforme necessário após salvar.
            // Por exemplo, pode querer habilitar tudo novamente.
            desabilitarBotoes(false, false, false, true, true);

        } catch (Exception e) {
            // É uma boa prática logar o erro também
            // log.error("Erro ao salvar", e);
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    // Ação Atualizar
    public void onAtualizar() {
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Nenhum item selecionado para atualizar.").show();
            return;
        }

        try {
            // 1. Pega o ID do item selecionado na tabela
            ID id = getIdFromViewModel(selecionado);

            // 2. MUDANÇA PRINCIPAL: Delega a lógica de atualização para o método
            //    abstrato, que será implementado pela classe filha (ex: MatriculaController).
            //    Não precisamos mais do viewToModel() aqui, pois o performUpdate
            //    terá acesso direto aos campos da tela.
            E entidadeAtualizada = performUpdate(id);

            // 3. Converte a entidade atualizada de volta para o ViewModel da tabela
            V viewAtualizada = modelToView(entidadeAtualizada);

            // 4. Atualiza o item na tabela (na interface gráfica)
            int index = getTabela().getItems().indexOf(selecionado);
            if (index >= 0) {
                getTabela().getItems().set(index, viewAtualizada);
            }

            // 5. Seleciona novamente o item atualizado
            getTabela().getSelectionModel().select(viewAtualizada);

            // --- Mantendo o comportamento original do seu método ---
            // 6. Habilita os campos para edição
            desabilitarCampos(false);

            // 7. Habilita/desabilita os botões
            desabilitarBotoes(true, true, true, false, false);

        } catch (Exception e) {
            // Captura e exibe qualquer erro que venha da camada de serviço
            new Alert(Alert.AlertType.ERROR, "Erro ao atualizar: " + e.getMessage()).show();
        }
    }
    
    public void onDeletar() {
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Nenhum item selecionado para deletar.").show();
            return;
        }
        try {
            ID id = getIdFromViewModel(selecionado);
    
            // Carrega a entidade completa do banco
            E entidade = getService().loadFromId(id);
    
            if (entidade == null) {
                new Alert(Alert.AlertType.WARNING, "Registro não encontrado no banco.").show();
                return;
            }
    
            // Deleta
            getService().delete(entidade);
    
            // Remove da tabela
            getTabela().getItems().remove(selecionado);
            limparCampos();
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao deletar: " + e.getMessage()).show();
        }
    }


    // Ação Cancelar
    public void onCancelar() {
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        limparCampos();
        getTabela().getSelectionModel().clearSelection();
    }
}
