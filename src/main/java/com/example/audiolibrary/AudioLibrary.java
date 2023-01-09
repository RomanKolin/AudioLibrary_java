package com.example.audiolibrary;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.util.Callback;

public class AudioLibrary extends Application
{
    @FXML
    Scene scene;
    @FXML
    Label label2change;
    @FXML
    Label label3change;
    @FXML
    Label label4change;
    @FXML
    Label label5change;
    @FXML
    Label label6change;
    @FXML
    Label label7numberofartistssongs;
    @FXML
    TextField textfield1change;
    @FXML
    TextField textfield2change;
    @FXML
    TextField textfield3change;
    @FXML
    TextField textfield4change;
    @FXML
    TextField textfield5change;
    @FXML
    TextField textfield6search;
    @FXML
    ComboBox combobox1genre;
    @FXML
    ComboBox combobox2numberofsongs;
    @FXML
    ComboBox combobox3relatedartistbandsfeaturing;
    @FXML
    TableView<String[]> tableview1audiolibrary;
    @FXML
    Button button1audiolibrary;
    @FXML
    Button button2genre;
    @FXML
    Button button3musicartistband;
    @FXML
    Button button4composers;
    @FXML
    Button button5bloggers;
    @FXML
    Button button6covers;
    @FXML
    Button button7soundtracks;
    @FXML
    Button button8favourites;
    @FXML
    Button button9add;
    @FXML
    Button button10edit;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(AudioLibrary.class.getResource("AudioLibrary.fxml"));
        System.setProperty("prism.lcdtext", "false");
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("/ContextMenu.css");
        stage.setTitle("Audio library");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/AudioLibraryicon.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    public void initialize()
    {
        button1audiolibrary.fire();
        OpenOnLastfm();
    }

    int tabl, ncombobox;
    ObservableList<String[]> obslist = FXCollections.observableArrayList();

    public void SeveralCombobox(ComboBox combobox1, ComboBox combobox2, ComboBox combobox3, int cnum1, int cnum2, int cnum3, int csongsnum1, int csongsnum2, int csongsnum3)
    {
        ncombobox = 0;
        try
        {
            obslist.clear();
            obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
        if (tabl == 3 || tabl == 6)
        {
            if (combobox2.getValue() != null)
                Filtering(combobox2, cnum2, csongsnum2);
            if (tabl == 3)
                if (combobox3.getValue() != null)
                    Filtering(combobox3, cnum3, csongsnum3);
        }
        Filtering(combobox1, cnum1, csongsnum1);
    }

    public void TextfieldClear()
    {
        textfield1change.setText("");
        textfield2change.setText("");
        textfield3change.setText("");
        textfield4change.setText("");
        textfield5change.setText("");
    }

    public void Filtering(ComboBox combobox, int cnum, int csongsnum)
    {
        int song = 0;

        if (combobox1genre.getValue() != null && tabl == 3 && ncombobox == 1)
            SeveralCombobox(combobox1genre, combobox2numberofsongs, combobox3relatedartistbandsfeaturing, 2, 3, 1, 3, 3, 3);
        if (combobox2numberofsongs.getValue() != null && tabl == 3 && ncombobox == 1)
            SeveralCombobox(combobox2numberofsongs, combobox1genre, combobox3relatedartistbandsfeaturing, 3, 2, 1, 3, 3, 3);
        if (combobox2numberofsongs.getValue() != null && (tabl == 4 || tabl == 5) && ncombobox == 1)
            SeveralCombobox(combobox2numberofsongs, null, null, 1, 0, 0, 1, 0, 0);
        if (combobox2numberofsongs.getValue() != null && tabl == 6 && ncombobox == 1)
            SeveralCombobox(combobox2numberofsongs, combobox3relatedartistbandsfeaturing, null, 2, 1, 0, 2, 2, 0);
        if (combobox2numberofsongs.getValue() != null && tabl == 7 && ncombobox == 1)
            SeveralCombobox(combobox2numberofsongs, null, null, 2, 0, 0, 2, 0, 0);
        if (combobox3relatedartistbandsfeaturing.getValue() != null && tabl == 3  && ncombobox == 1)
            SeveralCombobox(combobox3relatedartistbandsfeaturing, combobox1genre, combobox2numberofsongs, 1, 2, 3, 3, 3, 3);
        if (combobox3relatedartistbandsfeaturing.getValue() != null && tabl == 6 && ncombobox == 1)
            SeveralCombobox(combobox3relatedartistbandsfeaturing, combobox2numberofsongs, null, 1, 2, 0, 2, 2, 0);

        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (cnum == 1)
                if ((String.valueOf(combobox.getValue()).equals("<5") && (Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) >= 5)) ||
                    (String.valueOf(combobox.getValue()).equals(">=5") && (Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) < 5)) ||
                    ((String.valueOf(combobox.getValue()).equals("With related artist(-s)/band(-s)") || String.valueOf(combobox.getValue()).equals("With featuring")) && String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)).equals("null")) ||
                    ((String.valueOf(combobox.getValue()).equals("Without related artist(-s)/band(-s)") || String.valueOf(combobox.getValue()).equals("Without featuring")) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)).equals("null")))
                {
                    tableview1audiolibrary.getItems().remove(i);
                    i -= 1;
                }
                else
                    song += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(csongsnum));
            else if (cnum == 2)
                if ((!String.valueOf(combobox.getValue()).equals(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) && !String.valueOf(combobox.getValue()).equals(">3")) ||
                    (String.valueOf(combobox.getValue()).equals(">3") && (Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) <= 3)))
                {
                    tableview1audiolibrary.getItems().remove(i);
                    i -= 1;
                }
                else
                    song += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(csongsnum));
            else if (cnum == 3)
                if ((String.valueOf(combobox.getValue()).equals("<15") && Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) >= 15) ||
                    (!String.valueOf(combobox.getValue()).equals(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) && !String.valueOf(combobox.getValue()).equals("<15") && !String.valueOf(combobox.getValue()).equals(">55") && !String.valueOf(combobox.getValue()).equals(">3")) ||
                    (String.valueOf(combobox.getValue()).equals(">55") && Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) <= 55) ||
                    (String.valueOf(combobox.getValue()).equals(">3") && (Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum)) <= 3)))
                {
                    tableview1audiolibrary.getItems().remove(i);
                    i -= 1;
                }
                else
                    song += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(csongsnum));
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }
        label7numberofartistssongs.setText("Number of artists/songs: " + tableview1audiolibrary.getItems().size() + "/" + song);

        TextfieldClear();
        tableview1audiolibrary.getSelectionModel().select(null);
    }

    public void NumberofArtistsSongs(int rsong)
    {
        int song = 0, art = 0;

        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            tableview1audiolibrary.getSelectionModel().select(i);
            song += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rsong));
        }
        if (tabl == 1 || tabl == 2)
        {
            for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
            {
                tableview1audiolibrary.getSelectionModel().select(i);
                art += Integer.parseInt(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(1));
            }
            label7numberofartistssongs.setText("Number of artists/songs: " + art + "/" + song);
        }
        else
            label7numberofartistssongs.setText("Number of artists/songs: " + tableview1audiolibrary.getItems().size() + "/" + song);

        TextfieldClear();
        tableview1audiolibrary.getSelectionModel().select(null);
    }

    int rfnum, rsnum, rtnum;

    public void Searching(ObservableList<String[]> obslist)
    {
        textfield6search.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent ke) ->
        {
            if (ke.getCode() == KeyCode.BACK_SPACE || ke.getCode() == KeyCode.SPACE || textfield6search.getSelectedText().length() == textfield6search.getText().length())
            {
                try
                {
                    obslist.clear();
                    obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
                }
                catch (Exception e)
                {
                    throw new RuntimeException();
                }
                if (combobox1genre.getValue() != null || combobox2numberofsongs.getValue() != null || combobox3relatedartistbandsfeaturing.getValue() != null)
                {
                    ncombobox = 1;
                    Filtering(null, 0, 0);
                }
            }
        });

        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rfnum)).toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i').contains(textfield6search.getText().toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i')) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rsnum)).toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i').contains(textfield6search.getText().toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i')) && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(rtnum)).toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i').contains(textfield6search.getText().toLowerCase().replace('ö', 'o').replace('ü', 'u').replace('ÿ', 'y').replace('é', 'e').replace('ó', 'o').replace('ı', 'i').replace('î', 'i')))
            {
                tableview1audiolibrary.getItems().remove(i);
                i -= 1;
            }
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }
        if (tabl == 1 || tabl == 2 || tabl == 6 || tabl == 7)
            NumberofArtistsSongs(2);
        if (tabl == 3)
            NumberofArtistsSongs(3);
        if (tabl == 4 || tabl == 5)
            NumberofArtistsSongs(1);
        if (tabl == 8)
            NumberofArtistsSongs(4);

        tableview1audiolibrary.getSelectionModel().select(null);
    }

    ContextMenu cm;

    public void OpenOnLastfm() throws RuntimeException
    {
        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 6 || tabl == 7 || tabl == 8)
        {
            MenuItem mi;
            cm = new ContextMenu();
            mi = new MenuItem("Open on Last.fm");
            cm.getItems().add(mi);
        }
        tableview1audiolibrary.setRowFactory(rf ->
        {
            TableRow row = new TableRow();
            row.setOnMouseClicked(emouse ->
            {
                if (emouse.getButton() == MouseButton.SECONDARY)
                {
                    cm.show(row, emouse.getScreenX(), emouse.getScreenY());
                    cm.setOnAction(ecm ->
                    {
                        try
                        {
                            int cnum = 0;

                            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 6)
                                cnum = 0;
                            else if (tabl == 7 || tabl == 8)
                                cnum = 1;
                            if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 6 || tabl == 8)
                                Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum).replace("#", "%23")});
                            if ((!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).equals("null") || (String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).equals("null") && tabl == 6)) &&
                                    (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).contains(";") || (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).contains(";") && tabl == 6)) &&
                                    (tabl == 3 || tabl == 6 || tabl == 8))
                            {
                                if (tabl == 3 || tabl == 8 || (!String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).equals("null") && tabl == 6))
                                    Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)});
                                if (tabl == 6 && !String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 3)).contains(";"))
                                    Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 3)});
                                if (tabl == 6 && String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 3)).contains(";"))
                                {
                                    String[] datarr;

                                    datarr = Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 3).split("; ");
                                    for (int i = 0; i < datarr.length; i ++)
                                        Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + datarr[i]});
                                }
                            }
                            else if (String.valueOf(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1)).contains(";") || (tabl == 6 || tabl == 7))
                            {
                                String[] datarr;

                                datarr = Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 1).split("; ");
                                if (tabl == 7)
                                    datarr = Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum).split("; ");
                                for (int i = 0; i < datarr.length; i ++)
                                    Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + datarr[i]});
                                if (tabl == 6)
                                {
                                    datarr = Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(cnum + 3).split("; ");
                                    for (int i = 0; i < datarr.length; i ++)
                                        Runtime.getRuntime().exec(new String[] {"firefox", "https://www.last.fm/music/" + datarr[i]});
                                }
                            }
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException();
                        }
                    });
                }
            });
            return row;
        });
    }

    public void TextfieldEditable(Boolean edit)
    {
        textfield1change.setEditable(edit);
        textfield2change.setEditable(edit);
        textfield3change.setEditable(edit);
        textfield4change.setEditable(edit);
    }

    public void ColumnProperties(TableColumn col1, TableColumn col2, TableColumn col3, TableColumn col4, TableColumn col5)
    {
        TableColumn col = col1;
        col.setResizable(false);
        col.setReorderable(false);
        if (col2 != null)
        {
            col1 = col2;
            ColumnProperties(col1, null, col3, col4, col5);
        }
        if (col3 != null)
        {
            col1 = col3;
            ColumnProperties(col1, null, null, col4, col5);
        }
        if (col4 != null)
        {
            col1 = col4;
            ColumnProperties(col1, null, null, null, col5);
        }
        if (col5 != null)
        {
            col1 = col5;
            ColumnProperties(col1, null, null, null, null);
        }
    }

    public void Clipboard(Clipboard cb, ClipboardContent cbc, TextField textfield)
    {
        cbc.putString(textfield.getSelectedText());
        cb.setContent(cbc);
    }

    public void ContextMenu(TextField textfield)
    {
        ContextMenu cm = new ContextMenu();
        MenuItem cmmi1 = new MenuItem("Copy");
        MenuItem cmmi2 = new MenuItem("Cut");
        MenuItem cmmi3 = new MenuItem("Paste");
        ContextMenu cm1 = new ContextMenu();
        MenuItem cm1mi1 = new MenuItem("Copy");
        cm.getItems().addAll(cmmi1, cmmi2, cmmi3);
        cm1.getItems().add(cm1mi1);
        if (tabl == 1 || tabl == 2 || tabl == 8)
            textfield.setContextMenu(cm1);
        else
            textfield.setContextMenu(cm);
        textfield6search.setContextMenu(cm);

        Clipboard cb = Clipboard.getSystemClipboard();
        ClipboardContent cbc = new ClipboardContent();
        cmmi1.setOnAction(e -> Clipboard(cb, cbc, textfield));
        cm1mi1.setOnAction(e -> Clipboard(cb, cbc, textfield));
        cmmi2.setOnAction(ecm ->
        {
            int sel;

            Clipboard(cb, cbc, textfield);
            sel = textfield.getCaretPosition() - textfield.getSelectedText().length();
            textfield.setText(textfield.getText().replace(textfield.getSelectedText(), ""));
            textfield.positionCaret(sel);
            if (textfield == textfield6search)
            {
                try
                {
                    obslist.clear();
                    obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
                }
                catch (Exception e)
                {
                    throw new RuntimeException();
                }
                Searching(obslist);
            }
        });
        cmmi3.setOnAction(ecm ->
        {
            if (textfield.getSelectedText().length() > 0)
            {
                int sel;

                sel = (textfield.getCaretPosition() - textfield.getSelectedText().length()) + cb.getString().length();
                textfield.setText(textfield.getText().replace(textfield.getSelectedText(), cb.getString()));
                textfield.positionCaret(sel);
            }
            else
            {
                textfield.insertText(textfield.getCaretPosition(), cb.getString());
                textfield.positionCaret(textfield.getCaretPosition());
            }
            if (textfield == textfield6search)
            {
                try
                {
                    obslist.clear();
                    obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
                }
                catch (Exception e)
                {
                    throw new RuntimeException();
                }
                Searching(obslist);
            }
        });
    }

    int nodat;

    public void Table(String tablnam, String nam1, String nam2, String nam3, String nam4, String nam5, int siz1, int siz2, int siz3, int siz4, int siz5, int noartssongs) throws Exception
    {
        obslist.clear();

        ContextMenu(textfield1change);
        ContextMenu(textfield2change);
        ContextMenu(textfield3change);
        ContextMenu(textfield4change);
        ContextMenu(textfield5change);
        ContextMenu(textfield6search);
        label5change.setVisible(false);
        label6change.setVisible(false);
        textfield3change.setVisible(false);
        textfield4change.setVisible(false);
        textfield5change.setVisible(false);
        label3change.setVisible(true);
        textfield1change.setVisible(true);
        label4change.setVisible(true);
        textfield2change.setVisible(true);
        if (tabl == 1 || tabl == 2 || tabl == 3 || tabl == 6 || tabl == 7 || tabl == 8)
        {
            label5change.setVisible(true);
            textfield3change.setVisible(true);
            if (tabl == 3 || tabl == 6 || tabl == 8)
            {
                label6change.setVisible(true);
                textfield4change.setVisible(true);
                if (tabl == 8)
                    textfield5change.setVisible(true);
            }
        }
        textfield6search.setText("");
        combobox1genre.setVisible(false);
        combobox2numberofsongs.setVisible(false);
        combobox3relatedartistbandsfeaturing.setVisible(false);
        combobox1genre.setValue(null);
        combobox2numberofsongs.setValue(null);
        combobox3relatedartistbandsfeaturing.setValue(null);
        if (tabl == 3 || tabl == 4 || tabl == 5 || tabl == 6 || tabl == 7)
        {
            combobox2numberofsongs.setVisible(true);
            if (tabl == 3)
                combobox1genre.setVisible(true);
            if (tabl == 6)
                combobox3relatedartistbandsfeaturing.setVisible(true);
        }
        if (tabl == 1 || tabl == 2 || tabl == 8)
        {
            TextfieldEditable(false);
            button9add.setVisible(false);
            button10edit.setVisible(false);
        }
        else
        {
            TextfieldEditable(true);
            button9add.setVisible(true);
            button10edit.setVisible(true);
        }

        tableview1audiolibrary.getColumns().clear();
        tableview1audiolibrary.getSelectionModel().setCellSelectionEnabled(true);
        obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
        TableColumn first = new TableColumn<>(nam1);
        TableColumn second = new TableColumn<>(nam2);
        tableview1audiolibrary.getColumns().add(first);
        tableview1audiolibrary.getColumns().add(second);
        first.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[0]));
        second.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[1]));
        first.setPrefWidth(siz1);
        second.setPrefWidth(siz2);
        ColumnProperties(first, second, null, null, null);
        label2change.setText(tablnam);
        label3change.setText(nam1);
        label4change.setText(nam2);
        if (tabl == 1 || tabl == 2 || tabl == 3 || tabl == 6 || tabl == 7 || tabl == 8)
        {
            TableColumn third = new TableColumn<>(nam3);
            tableview1audiolibrary.getColumns().add(third);
            third.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[2]));
            third.setPrefWidth(siz3);
            ColumnProperties(first, second, third, null, null);
            label5change.setText(nam3);
            if (tabl == 3 || tabl == 6 || tabl == 8)
            {
                TableColumn fourth = new TableColumn<>(nam4);
                tableview1audiolibrary.getColumns().add(fourth);
                fourth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[3]));
                fourth.setPrefWidth(siz4);
                ColumnProperties(first, second, third, fourth, null);
                label6change.setText(nam4);
                if (tabl == 8)
                {
                    label3change.setText(nam2);
                    label4change.setText(nam3);
                    label5change.setText(nam4);
                    label6change.setText(nam5);
                    TableColumn fifth = new TableColumn<>(nam5);
                    tableview1audiolibrary.getColumns().add(fifth);
                    fifth.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) v -> new SimpleStringProperty(v.getValue()[4]));
                    fifth.setPrefWidth(siz5);
                    ColumnProperties(first, second, third, fourth, fifth);
                }
            }
        }
        tableview1audiolibrary.setItems(obslist);
        NumberofArtistsSongs(noartssongs);

        tableview1audiolibrary.getSelectionModel().selectedItemProperty().addListener((s, os, ns) ->
        {
            if (ns != null)
            {
                textfield1change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(0));
                textfield2change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(1));
                if (nodat > 2)
                {
                    textfield3change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(2));
                    switch (nodat)
                    {
                        case 4:
                            textfield4change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(3));
                            break;
                        case 5:
                            textfield1change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(1));
                            textfield2change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(2));
                            textfield3change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(3));
                            textfield4change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(4));
                            textfield5change.setText(Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(0));
                            break;
                    }
                }
            }
        });
        if (String.valueOf(textfield2change.getText()).equals("null"))
            textfield2change.setText("");

        textfield6search.textProperty().addListener((o, ov, nv) -> Searching(obslist));

        if (tabl == 1 || tabl == 2)
        {
            cm = new ContextMenu();
            cm.getItems().clear();
        }
        else
            OpenOnLastfm();
    }

    public void button1audiolibrary() throws Exception
    {
        tabl = 1;
        nodat = 3;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        Table("Audio library","Category", "Number of original artists/bands", "Number of songs", "", "", 613, 240, 135, 0, 0, 2);
    }

    public void button2genre() throws Exception
    {
        tabl = 2;
        nodat = 3;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        Table("Genre","Name(-s)", "Number of artists/bands", "Number of songs", "", "", 655, 185, 135, 0, 0, 2);
    }

    public void ComboboxAction(ComboBox combobox, int cnum, int csongnum)
    {
        textfield6search.setText("");

        if (combobox.getValue() == null)
            ncombobox = 0;
        else
            ncombobox = 1;
        Filtering(combobox, cnum, csongnum);
    }

    public void button3musicartistband() throws Exception
    {
        tabl = 3;
        nodat = 4;
        rfnum = 1;
        rsnum = 0;
        rtnum = 0;
        Table("Music artist/band","Artist/band", "Related artist(-s)/band(-s)", "Genre(-s)", "Number of songs", "", 305, 305, 230, 135, 0, 3);

        combobox1genre.setVisible(true);
        combobox2numberofsongs.setVisible(true);
        combobox3relatedartistbandsfeaturing.setVisible(true);
        combobox1genre.setItems(FXCollections.observableArrayList("Авторская песня, Шансон",
                                                                      "Альтернатива, Инди",
                                                                      "Блюз",
                                                                      "ВИА",
                                                                      "Вокальная музыка",
                                                                      "Джаз",
                                                                      "Кантри",
                                                                      "Легкая, Инструментальная музыка",
                                                                      "Метал, Ню-метал, Металкор",
                                                                      "Панк, Эмо, Постхардкор",
                                                                      "Поп",
                                                                      "Поп-рок",
                                                                      "Регги, Реггетон",
                                                                      "Рок",
                                                                      "Соул, Фанк, Диско",
                                                                      "Хип-хоп",
                                                                      "Электронная музыка"));
        combobox2numberofsongs.setItems(FXCollections.observableArrayList("<15",
                                                                              "15",
                                                                              "25",
                                                                              "55",
                                                                              ">55"));
        combobox3relatedartistbandsfeaturing.setItems(FXCollections.observableArrayList("With related artist(-s)/band(-s)",
                                                                                            "Without related artist(-s)/band(-s)"));
        combobox1genre.setOnAction(e -> ComboboxAction(combobox1genre, 2, 3));
        combobox2numberofsongs.setOnAction(e -> ComboboxAction(combobox2numberofsongs, 3, 3));
        combobox3relatedartistbandsfeaturing.setOnAction(e -> ComboboxAction(combobox3relatedartistbandsfeaturing, 1, 3));
    }

    public void button4composers() throws Exception
    {
        tabl = 4;
        nodat = 2;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        Table("Composers","Name", "Number of songs", "", "", "", 840, 135, 0, 0, 0, 1);

        combobox2numberofsongs.setItems(FXCollections.observableArrayList("<5",
                                                                              ">=5"));
        combobox2numberofsongs.setOnAction(e -> ComboboxAction(combobox2numberofsongs, 1, 1));
    }

    public void button5bloggers() throws Exception
    {
        tabl = 5;
        nodat = 2;
        rfnum = 0;
        rsnum = 0;
        rtnum = 0;
        Table("Bloggers","Nickname/name", "Number of songs", "", "", "", 840, 135, 0, 0, 0, 1);

        combobox2numberofsongs.setItems(FXCollections.observableArrayList("<5",
                                                                              ">=5"));
        combobox2numberofsongs.setOnAction(e -> ComboboxAction(combobox2numberofsongs, 1, 1));
    }

    public void button6covers() throws Exception
    {
        tabl = 6;
        nodat = 4;
        rfnum = 0;
        rsnum = 1;
        rtnum = 3;
        Table("Covers","Artist/band", "Featuring", "Number of songs", "Original artist(-s)/band(-s)", "", 280, 280, 135, 280, 0, 2);

        combobox2numberofsongs.setVisible(true);
        combobox3relatedartistbandsfeaturing.setVisible(true);
        combobox2numberofsongs.setItems(FXCollections.observableArrayList("1",
                                                                              "2",
                                                                              "3",
                                                                              ">3"));
        combobox3relatedartistbandsfeaturing.setItems(FXCollections.observableArrayList("With featuring",
                                                                                            "Without featuring"));
        combobox2numberofsongs.setOnAction(e -> ComboboxAction(combobox2numberofsongs, 2, 2));
        combobox3relatedartistbandsfeaturing.setOnAction(e -> ComboboxAction(combobox3relatedartistbandsfeaturing, 1, 2));
    }

    public void button7soundtracks() throws Exception
    {
        tabl = 7;
        nodat = 3;
        rfnum = 0;
        rsnum = 1;
        rtnum = 0;
        Table("Soundtracks","Movie/animation/series", "Artist(-s)/band(-s)", "Number of songs", "Original artist(-s)/band(-s)", "", 655, 185, 135, 0, 0, 2);

        combobox2numberofsongs.setItems(FXCollections.observableArrayList("1",
                                                                              "2",
                                                                              "3",
                                                                              ">3"));
        combobox2numberofsongs.setOnAction(e -> ComboboxAction(combobox2numberofsongs, 2, 2));
    }

    public void button8favourites() throws Exception
    {
        tabl = 8;
        nodat = 5;
        rfnum = 1;
        rsnum = 2;
        rtnum = 1;
        Table("Favourites","Number", "Artist/band", "Related artist(-s)/band(-s)", "Genre(-s)", "Number of songs", 78, 305, 305, 165, 135, 4);
    }

    int chang;

    public void ChangeData() throws Exception
    {
        String col1;

        Alert al;
        al = new Alert(Alert.AlertType.NONE);
        DialogPane dp;
        dp = al.getDialogPane();
        dp.getButtonTypes().add(ButtonType.OK);
        dp.getStylesheets().add("/Alert.css");

        if (String.valueOf(textfield1change.getText()).equals(""))
        {
            textfield2change.setText(null);
            textfield4change.setText(null);
        }
        if (String.valueOf(textfield4change.getText()).equals("") && tabl == 6)
            textfield3change.setText(null);
        if ((String.valueOf(textfield1change.getText()).equals("") || String.valueOf(textfield2change.getText()).equals("")) && tabl == 7)
            textfield3change.setText(null);
        String[] datarr = new String[5];
        datarr[0] = textfield1change.getText();
        datarr[1] = textfield2change.getText();
        datarr[2] = textfield3change.getText();
        datarr[3] = textfield4change.getText();
        datarr[4] = textfield5change.getText();
        if (chang == 9)
            al.setContentText(AudioLibraryDB.INSERT(tabl, datarr));
        else if (chang == 10)
            al.setContentText(AudioLibraryDB.UPDATE(tabl, datarr));
        al.show();

        col1 = textfield1change.getText();
        obslist.clear();
        obslist.addAll(Arrays.asList(AudioLibraryDB.SELECT(tabl)));
        tableview1audiolibrary.getSelectionModel().select(0);
        for (int i = 0; i < tableview1audiolibrary.getItems().size(); i++)
        {
            if (!Arrays.asList(tableview1audiolibrary.getSelectionModel().getSelectedItem()).get(0).equals(col1))
            {
                tableview1audiolibrary.getItems().remove(i);
                i -= 1;
            }
            tableview1audiolibrary.getSelectionModel().select(i + 1);
        }
        textfield6search.setText("");
        if (tabl == 6 || tabl == 7)
            NumberofArtistsSongs(2);
        if (tabl == 3)
            NumberofArtistsSongs(3);
        if (tabl == 4 || tabl == 5)
            NumberofArtistsSongs(1);
    }

    public void button9add() throws Exception
    {
        chang = 9;
        ChangeData();
    }

    public void button10edit() throws Exception
    {
        chang = 10;
        ChangeData();
    }
}