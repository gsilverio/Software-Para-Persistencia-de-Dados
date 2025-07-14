package com.atividade.cinco.controller;

import com.atividade.cinco.events.TurmasChangedEvent;
import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Turma;
import com.atividade.cinco.service.AbstractCrudService;
import com.atividade.cinco.service.DisciplinaService;
import com.atividade.cinco.service.TurmaService;
import com.atividade.cinco.view.TurmaView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class TurmaController extends AbstractCrudController<Turma, TurmaView, Integer> implements Initializable {

    @FXML
    private TableView<TurmaView> tabela;

    @FXML
    private TableColumn<TurmaView, Integer> idCol;
    @FXML
    private TableColumn<TurmaView, String> codigoCol;
    @FXML
    private TableColumn<TurmaView, String> disciplinaCol;
    @FXML
    private TableColumn<TurmaView, Integer> alunosMatriculadosCol;
    @FXML
    private TableColumn<TurmaView, Integer> vagasDisponiveisCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField codigoField;
    @FXML
    private ComboBox<Disciplina> disciplinaComboBox;
    @FXML
    private Label alunosMatriculadosLabel;
    @FXML
    private TextField vagasDisponiveisField;

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
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Override
    protected AbstractCrudService<Turma, Integer> getService(){
        return turmaService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar colunas
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        disciplinaCol.setCellValueFactory(new PropertyValueFactory<>("disciplinaCodigo"));
        alunosMatriculadosCol.setCellValueFactory(new PropertyValueFactory<>("alunosMatriculados"));
        vagasDisponiveisCol.setCellValueFactory(new PropertyValueFactory<>("vagasDisponiveis"));

        // Carregar disciplinas no ComboBox
        List<Disciplina> disciplinas = disciplinaService.findAll();
        disciplinaComboBox.setItems(FXCollections.observableArrayList(disciplinas));
        disciplinaComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Disciplina d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? "" : d.getCodigo());
            }
        });
        disciplinaComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Disciplina d, boolean empty) {
                super.updateItem(d, empty);
                setText(empty || d == null ? "" : d.getCodigo());
            }
        });

        disciplinaComboBox.setOnShowing(e->{attComboBoxDisciplinas();});

        super.initialize();

    }

    private void attComboBoxDisciplinas(){
        System.out.printf("Atualizando");

        List<Disciplina> disciplinaList = disciplinaService.loadAll();
        disciplinaComboBox.setItems(FXCollections.observableArrayList(disciplinaList));
    }


    @Override
    protected Turma performCreate(Turma entidade) {
        // A entidade já vem montada pelo método viewToModel()
        return getService().create(entidade);
    }

    @Override
    protected Turma performUpdate(Integer id) {
        // Pega os dados mais recentes dos campos, incluindo o ComboBox
        String novoCodigo = codigoField.getText();
        int novasVagas = Integer.parseInt(vagasDisponiveisField.getText());
        Disciplina novaDisciplina = disciplinaComboBox.getValue();

        if (novaDisciplina == null) {
            throw new IllegalStateException("É necessário selecionar uma disciplina.");
        }

        // Chama o método de serviço específico
        return turmaService.updateTurma(id, novoCodigo, novasVagas, novaDisciplina.getId());
    }


    @Override
    protected TurmaView modelToView(Turma t) {
        return new TurmaView(
            t.getId(),
            t.getCodigo(),
            t.getDisciplina() != null ? t.getDisciplina().getId() : 0,
            t.getDisciplina() != null ? t.getDisciplina().getCodigo() : "",
            t.getVagasDisponiveis(),
            t.getAlunosMatriculados()
        );
    }

    @EventListener
    public void onTurmasChanged(TurmasChangedEvent event) {
        System.out.println("Evento de mudança de turma recebido. Atualizando a tabela de turmas...");
        getTabela().setItems(loadAll());
    }

    @Override
    protected Turma viewToModel() {
        Turma t = new Turma();
        t.setCodigo(codigoField.getText());
        t.setDisciplina(disciplinaComboBox.getValue());

        // Parsing seguro
        String vagasText = vagasDisponiveisField.getText();
        int vagas = 0;
        if (vagasText != null && !vagasText.isBlank()) {
            try {
                vagas = Integer.parseInt(vagasText);
            } catch (NumberFormatException ex) {
                vagas = 0;
            }
        }
        t.setVagasDisponiveis(vagas);

        // alunosMatriculados é derivado
        return t;
    }

    @Override
    protected void preencherCampos(TurmaView turma) {
        idField.setText(String.valueOf(turma.getId()));
        codigoField.setText(turma.getCodigo());
        alunosMatriculadosLabel.setText(String.valueOf(turma.getAlunosMatriculados()));
        vagasDisponiveisField.setText(String.valueOf(turma.getVagasDisponiveis()));

        // Selecionar disciplina por ID
        Disciplina d = disciplinaService.loadFromId(turma.getDisciplinaId());
        disciplinaComboBox.setValue(d);
    }

    @Override
    protected void limparCampos() {
        idField.clear();
        codigoField.clear();
        alunosMatriculadosLabel.setText("");
        vagasDisponiveisField.clear();
        disciplinaComboBox.setValue(null);
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        codigoField.setDisable(desabilitado);
        disciplinaComboBox.setDisable(desabilitado);
        vagasDisponiveisField.setDisable(desabilitado);
        // Label alunosMatriculados é só leitura
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
    protected TableView<TurmaView> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(TurmaView viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Turma entidade, Integer id) {
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
