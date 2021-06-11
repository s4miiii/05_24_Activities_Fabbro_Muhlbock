package sample;

import bll.Activity;
import bll.Person;
import bll.Season;
import dal.dao.activitiesDbDao;
import dal.dao.personDbDao;
import dal.dao.saisonDbDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    TableColumn<Person, String> tcfirstname = null;
    TableColumn<Person, String> tclastname = null;
    @FXML
    private TableView tvPersons;
    @FXML
    private ListView lvActivities;
    @FXML
    private ComboBox cbSeason;

    private List<Season> seasonList = null;
    private List<Activity> activityList = null;
    private List<Person> personList = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Activity> activityObservableList = FXCollections.observableArrayList(getActivityList());
        lvActivities.setItems(activityObservableList);
        lvActivities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Activity activity = (Activity) t1;
                tvPersons.setItems(FXCollections.observableArrayList(personList.stream().filter(person -> person.getActivityID() == activity.getId()).collect(Collectors.toList())));
            }
        });

        ObservableList<Person> personObservableList = FXCollections.observableArrayList(getPersonList());
        tvPersons.setItems(personObservableList);
        createColumns();
        configureTableView();

        seasonList = getSeasonList();
        List<String> arrayList = seasonList.stream().map( season -> {return season.getIdentifier();}).collect(Collectors.toList());
        cbSeason.getItems().addAll(arrayList);

        cbSeason.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                //Season season = seasonList.stream().find(s -> s.getIdentifier().equals((String) t1 ));
                Season season = getSeasonFromIdentifier((String)t1);
                lvActivities.setItems(FXCollections.observableList(activityList.stream().filter(activity -> activity.getSeasonid() == season.getId()).collect(Collectors.toList())));
            }
        });
    }

    private Season getSeasonFromIdentifier(String identifier) {
        for (Season s : seasonList){
            if(s.getIdentifier().equals(identifier))
                return s;
        }

        return null;
    }

    public List<Season> getSeasonList(){
        seasonList = Season.getAll(new saisonDbDao());
        return seasonList;
    }

    public List<Person> getPersonList(){
        personList = Person.getAllPerson(new personDbDao());
        return personList;
    }

    public List<Activity> getActivityList(){
        activityList = Activity.getAllActivities(new activitiesDbDao());
        return activityList;
    }

    public void createColumns(){
        tcfirstname = new TableColumn<Person, String>("Firstname");
        tclastname = new TableColumn<Person, String>("Lastname");

        tcfirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        tclastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        tvPersons.getColumns().addAll(tcfirstname,tclastname);
    }

    public void configureTableView(){
        this.tvPersons.setEditable(true);
        tcfirstname.setCellFactory(TextFieldTableCell.forTableColumn());
        tcfirstname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> tcEditEvent) {
                Person p = tcEditEvent.getTableView().getItems().get(tcEditEvent.getTablePosition().getRow());
                p.setFirstname(tcEditEvent.getNewValue());
                p.update(new personDbDao());
            }
        });
        tclastname.setCellFactory(TextFieldTableCell.forTableColumn());
        tclastname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> tcEditEvent) {
                Person p = tcEditEvent.getTableView().getItems().get(tcEditEvent.getTablePosition().getRow());
                p.setLastname(tcEditEvent.getNewValue());
                p.update(new personDbDao());
            }
        });
    }
}


