package com.atividade.cinco.controller;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Estudante;
import com.atividade.cinco.model.Matricula;
import com.atividade.cinco.model.Turma;
import com.atividade.cinco.model.enuns.StatusMatricula;
import com.atividade.cinco.service.AbstractCrudService;
import com.atividade.cinco.service.EstudanteService;
import com.atividade.cinco.service.MatriculaService;
import com.atividade.cinco.service.TurmaService;
import com.atividade.cinco.view.MatriculaView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


@Controller
public class MatriculaController extends AbstractCrudController<Matricula, MatriculaView, Integer> implements Initializable {

    @FXML
    private TableView<MatriculaView> tabela;

    @FXML
    private TableColumn<MatriculaView, Integer> idCol;
    @FXML
    private TableColumn<MatriculaView, String> estudanteCol;
    @FXML
    private TableColumn<MatriculaView, String> turmaCol;
    @FXML
    private TableColumn<MatriculaView, String> statusCol;

    @FXML
    private TextField idField;
    @FXML
    private ComboBox<Estudante> estudanteComboBox;
    @FXML
    private ComboBox<Turma> turmaComboBox;
    @FXML
    private ComboBox<StatusMatricula> statusComboBox;

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
    private MatriculaService matriculaService;

    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private TurmaService turmaService;

    @Override
    protected AbstractCrudService<Matricula, Integer> getService(){ return matriculaService;};



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar colunas
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        estudanteCol.setCellValueFactory(new PropertyValueFactory<>("estudanteNome"));
        turmaCol.setCellValueFactory(new PropertyValueFactory<>("turmaCodigo"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Estudantes
        List<Estudante> estudantes = estudanteService.findAll();
        estudanteComboBox.setItems(FXCollections.observableArrayList(estudantes));
        estudanteComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Estudante e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? "" : e.getNomeCompleto());
            }
        });
        estudanteComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Estudante e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? "" : e.getNomeCompleto());
            }
        });

        System.out.println("Estudantes carregados: " + estudantes.size());
        attComboBoxTurmas();

        // Turmas
        List<Turma> turmas = turmaService.loadAll();
        turmaComboBox.setItems(FXCollections.observableArrayList(turmas));
        turmaComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Turma t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? "" : t.getCodigo());
            }
        });
        turmaComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Turma t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty || t == null ? "" : t.getCodigo());
            }
        });

        System.out.println("Turmas carregadas: " + turmas.size());

        // Status enum
        statusComboBox.setItems(FXCollections.observableArrayList(StatusMatricula.values()));

        super.initialize();
    }

    private void attComboBoxTurmas(){
        System.out.printf("Atualizando");

        List<Turma> turmaList = turmaService.loadAll();
        turmaComboBox.setItems(FXCollections.observableArrayList(turmaList));
    }

    @Override
    protected Matricula performCreate(Matricula entidade) {
        // A entidade já vem montada pelo método viewToModel(), pegando
        // os valores dos ComboBoxes de estudante, turma e status.
        return matriculaService.create(entidade);
    }

    @Override
    protected Matricula performUpdate(Integer id) {
        // A principal ação de update em uma matrícula é alterar seu status.
        // Os campos de estudante e turma geralmente não são alterados.
        StatusMatricula novoStatus = statusComboBox.getValue();

        if (novoStatus == null) {
            throw new IllegalStateException("É necessário selecionar um status para a matrícula.");
        }

        // Chama o método de serviço específico que contém toda a regra de negócio
        // (verificar vagas, etc.)
        return matriculaService.updateStatusMatricula(id, novoStatus);
    }


    @Override
    protected MatriculaView modelToView(Matricula m) {
        return new MatriculaView(
            m.getId(),
            m.getEstudante() != null ? m.getEstudante().getNomeCompleto() : "",
            m.getEstudante() != null ? m.getEstudante().getId() : 0,
            m.getTurma() != null ? m.getTurma().getCodigo() : "",
            m.getTurma() != null ? m.getTurma().getId() : 0,
            m.getStatus() != null ? m.getStatus().name() : ""
        );
    }

    @Override
    protected Matricula viewToModel() {
        Matricula m = new Matricula();
        m.setEstudante(estudanteComboBox.getValue());
        m.setTurma(turmaComboBox.getValue());
        m.setStatus(statusComboBox.getValue());
        return m;
    }

    @Override
    protected void preencherCampos(MatriculaView matricula) {
        idField.setText(String.valueOf(matricula.getId()));

        // Estudante
        Estudante e = estudanteService.loadFromId(matricula.getEstudanteId());
        estudanteComboBox.setValue(e);

        // Turma
        Turma t = turmaService.loadFromId(matricula.getTurmaId());
        turmaComboBox.setValue(t);

        System.out.println("Preenchendo campos: estudanteId=" + matricula.getEstudanteId() + " turmaId=" + matricula.getTurmaId());

        // Status
        if (matricula.getStatus() != null && !matricula.getStatus().isBlank()) {
            StatusMatricula status = StatusMatricula.valueOf(matricula.getStatus());
            statusComboBox.setValue(status);
        } else {
            statusComboBox.setValue(null);
        }
    }

    @Override
    protected void limparCampos() {
        idField.clear();
        estudanteComboBox.setValue(null);
        turmaComboBox.setValue(null);
        statusComboBox.setValue(null);
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        estudanteComboBox.setDisable(desabilitado);
        turmaComboBox.setDisable(desabilitado);
        statusComboBox.setDisable(desabilitado);
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
    protected TableView<MatriculaView> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(MatriculaView viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Matricula entidade, Integer id) {
        entidade.setId(id);
    }

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
