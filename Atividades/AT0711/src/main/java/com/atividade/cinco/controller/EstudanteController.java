package com.atividade.cinco.controller;

import com.atividade.cinco.model.Estudante;
import com.atividade.cinco.service.AbstractCrudService;
import com.atividade.cinco.service.EstudanteService;
import com.atividade.cinco.view.EstudanteView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class EstudanteController extends AbstractCrudController<Estudante, EstudanteView, Integer> implements Initializable {

    @FXML
    private TableView<EstudanteView> tabela;
    @FXML
    private TableColumn<EstudanteView, Integer> idCol;
    @FXML
    private TableColumn<EstudanteView, String> nomeCompletoCol;
    @FXML
    private TableColumn<EstudanteView, String> dataDeNascimentoCol;
    @FXML
    private TableColumn<EstudanteView, Integer> matriculaCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField nomeCompletoField;
    @FXML
    private TextField dataDeNascimentoField;
    @FXML
    private TextField matriculaField;

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
    private EstudanteService estudanteService;

    @Override
    protected AbstractCrudService<Estudante, Integer> getService() {
        return estudanteService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCompletoCol.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        dataDeNascimentoCol.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
        matriculaCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        super.initialize();
    }

    @Override
    protected Estudante performCreate(Estudante entidade) {
        // A entidade já vem montada pelo método viewToModel()
        return estudanteService.create(entidade);
    }

    @Override
    protected Estudante performUpdate(Integer id) {
        // Pega os dados mais recentes dos campos da tela
        String novoNome = nomeCompletoField.getText();
        String novaData = dataDeNascimentoField.getText();

        // Chama o método de serviço específico
        // Note que não alteramos o número de matrícula, que é um identificador.
        return estudanteService.updateEstudante(id, novoNome, novaData);
    }


    @Override
    protected EstudanteView modelToView(Estudante e) {
        return new EstudanteView(e.getId(), e.getNomeCompleto(), e.getDataDeNascimento(), e.getMatricula());
    }

    @Override
    protected Estudante viewToModel() {
        Estudante e = new Estudante();
        e.setNomeCompleto(nomeCompletoField.getText());
        e.setDataDeNascimento(dataDeNascimentoField.getText());
        e.setMatricula(Integer.parseInt(matriculaField.getText()));
        return e;
    }

    @Override
    protected void preencherCampos(EstudanteView est) {
        idField.setText(String.valueOf(est.getId()));
        nomeCompletoField.setText(est.getNomeCompleto());
        dataDeNascimentoField.setText(est.getDataDeNascimento());
        matriculaField.setText(String.valueOf(est.getMatricula()));
    }

    @Override
    protected void limparCampos() {
        idField.clear();
        nomeCompletoField.clear();
        dataDeNascimentoField.clear();
        matriculaField.clear();
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        nomeCompletoField.setDisable(desabilitado);
        dataDeNascimentoField.setDisable(desabilitado);
        matriculaField.setDisable(desabilitado);
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
    protected TableView<EstudanteView> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(EstudanteView viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Estudante entidade, Integer id) {
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