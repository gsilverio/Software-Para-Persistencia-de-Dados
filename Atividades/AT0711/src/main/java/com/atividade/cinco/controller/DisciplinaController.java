package com.atividade.cinco.controller;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.repository.DisciplinaRepository;
import com.atividade.cinco.service.AbstractCrudService;
import com.atividade.cinco.service.DisciplinaService;
import com.atividade.cinco.view.DisciplinaView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DisciplinaController extends AbstractCrudController<Disciplina, DisciplinaView, Integer> implements Initializable {

    @FXML
    private TableView<DisciplinaView> tabela;

    @FXML
    private TableColumn<DisciplinaView, Integer> idCol;
    @FXML
    private TableColumn<DisciplinaView, String> codigoCol;
    @FXML
    private TableColumn<DisciplinaView, String> tituloCol;
    @FXML
    private TableColumn<DisciplinaView, String> ementaCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField codigoField;
    @FXML
    private TextField tituloField;
    @FXML
    private TextField ementaField;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button deletarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button salvarButton;

    @Autowired
    private DisciplinaService disciplinaService;

    @Override
    protected AbstractCrudService<Disciplina, Integer> getService() {
        return disciplinaService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        ementaCol.setCellValueFactory(new PropertyValueFactory<>("ementa"));
        super.initialize();
    }

    @Override
    protected Disciplina performCreate(Disciplina entidade) {
        // A entidade já vem montada pelo método viewToModel()
        return disciplinaService.create(entidade);
    }

    @Override
    protected Disciplina performUpdate(Integer id) {
        // Pega os dados mais recentes dos campos da tela para o update
        String novoCodigo = codigoField.getText();
        String novoTitulo = tituloField.getText();
        String novaEmenta = ementaField.getText();

        // Chama o método de serviço específico com as regras de negócio
        return disciplinaService.updateDisciplina(id, novoCodigo, novoTitulo, novaEmenta);
    }



    @Override
    protected DisciplinaView modelToView(Disciplina d) {
        return new DisciplinaView(d.getId(), d.getCodigo(), d.getTitulo(), d.getEmenta());
    }

    @Override
    protected Disciplina viewToModel() {
        Disciplina d = new Disciplina();
        d.setCodigo(codigoField.getText());
        d.setTitulo(tituloField.getText());
        d.setEmenta(ementaField.getText());
        return d;
    }

    @Override
    protected void preencherCampos(DisciplinaView disc) {
        idField.setText(String.valueOf(disc.getId()));
        codigoField.setText(disc.getCodigo());
        tituloField.setText(disc.getTitulo());
        ementaField.setText(disc.getEmenta());
    }

    @Override
    protected void limparCampos() {
        idField.clear();
        codigoField.clear();
        tituloField.clear();
        ementaField.clear();
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        codigoField.setDisable(desabilitado);
        tituloField.setDisable(desabilitado);
        ementaField.setDisable(desabilitado);
    }

    @Override
    protected void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, boolean cancelar, boolean salvar) {
        adicionarButton.setDisable(adicionar);
        atualizarButton.setDisable(atualizar);
        deletarButton.setDisable(deletar);
        cancelarButton.setDisable(cancelar);
        salvarButton.setDisable(salvar);
    }

    @Override
    protected TableView<DisciplinaView> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(DisciplinaView viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Disciplina entidade, Integer id) {
        entidade.setId(id);
    }

    // Métodos que chamam a superclasse
    @FXML
    public void onAdicionar() {
        super.onAdicionar();
    }

    @FXML
    public void onSalvar() {
        super.onSalvar();
    }

    @FXML
    public void onAtualizar() {
        super.onAtualizar();
    }

    @FXML
    public void onDeletar() {
        super.onDeletar();
    }

    @FXML
    public void onCancelar() {
        super.onCancelar();
    }
}
