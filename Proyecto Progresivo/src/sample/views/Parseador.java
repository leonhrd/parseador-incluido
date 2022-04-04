package sample.views;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
//el extends stage es porque va a ser una ventana
public class Parseador extends Stage implements EventHandler<KeyEvent> {
    private VBox vBox;
    private ToolBar tlbMenu;
    private TextArea txtEntrada, txtSalida;
    private FileChooser flcArchivo;
    private Button btnAbrir;
    private Scene escena;
    private Image imgAbrir;
    private ImageView imvAbrir;
    private Button btnTraducir;
    private Button btnLimpiar;
    String borrador = "";
    String caracter = "";
    String cadena = "";
    int cantidad=0;
    int resta=0;
    int numCarTextSalida=0;
    char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'
            , 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'
            , 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    String[] ab_yz = {"A", "B", "C", "D", "E", "F", "G", "H", "I"
            , "J", "K", "L", "M", "N", "O", "P", "Q", "R"
            , "S", "T", "U", "V", "W", "X", "Y", "Z"};

    String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-."
            , "--.", "....", "..", ".---", "-.-"
            , ".-..", "--", "-.", "---", ".--.", "--.-"
            , ".-.", "...", "-", "..-", "...-", ".--", "-..-"
            , "-.--", "--.."};
    char numeros[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    String Numpads[] = {"NUMPAD0", "NUMPAD1", "NUMPAD2", "NUMPAD3", "NUMPAD4", "NUMPAD5", "NUMPAD6", "NUMPAD7", "NUMPAD8", "NUMPAD9"};
    String Numorse[] = {"----", ".----", "..---", "...--", "....-", ".....", "....-", "...--", "..---", ".----"};

    public Parseador() {
        CrearUI();
        this.setTitle("traductor de código morse");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI() {
        vBox = new VBox();
        tlbMenu = new ToolBar();
        imgAbrir = new Image("sample/images/iconoAbrir.jpeg");
        imvAbrir = new ImageView(imgAbrir);
        imvAbrir.setFitHeight(25);
        imvAbrir.setFitWidth(25);
        btnAbrir = new Button();
        btnAbrir.setGraphic(imvAbrir);
        btnAbrir.setOnAction(event -> {
            flcArchivo = new FileChooser();
            flcArchivo.setTitle("Buscar Archivo");
            File archivo = flcArchivo.showOpenDialog(this);
            File file = new File(String.valueOf(archivo));
            try {
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                txtEntrada.appendText(raf.readLine());
            } catch (IOException IO) {
            }
        });
        tlbMenu.getItems().addAll(btnAbrir);
        txtEntrada = new TextArea();
        txtEntrada.setPromptText("Introduce el texto a traducir");
        txtEntrada.setOnKeyPressed(this);
        txtSalida = new TextArea();
        txtSalida.setEditable(false);
        btnTraducir = new Button();
        btnTraducir.setText("Traducir");
        btnTraducir.setOnAction(event -> {
            String texto = txtEntrada.getText();
            int c = 0;
            while (c < texto.length()) {
                char linea = texto.toUpperCase().charAt(c);
                c++;
                traducirDocumento(linea);
            }
        });
        btnLimpiar = new Button();
        btnLimpiar.setText("Limpiar todo");
        btnLimpiar.setOnAction(event -> {
            limpiarTodo();
        });
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(5));
        vBox.getChildren().addAll(tlbMenu, txtEntrada, txtSalida, btnTraducir, btnLimpiar);
        escena = new Scene(vBox, 500, 300);
    }
    @Override
    public void handle(KeyEvent event) {
        caracter = event.getCode().toString();
        int i = 0;
        int l = 0;
        teclarDiferentes(caracter);

        while (i < ab_yz.length) {
            if (ab_yz[i].equals(caracter))
                buscarCaracterAlfa(caracter);
            i++;
        }
        while (l < Numpads.length) {
            if (Numpads[l].equals(caracter))
                buscarNumero(caracter);
            l++;
        }
    }
    public void buscarCaracterAlfa(String caracter) {
        int j = 0;
        int index = 0;
        while (j < ab_yz.length) {
            if (ab_yz[j].equals(caracter))
                index = j;
            j++;
        }
        buscarCaracterMorse(index);
    }
    public void buscarCaracterMorse(int index) {
        int k = 0;
        while (k < morse.length) {
            if (index == k)
                txtSalida.appendText(morse[k] + " ");
            k++;
        }
    }
    public void buscarNumero(String caracter) {
        int j = 0;
        int index = 0;
        while (j < Numpads.length) {
            if (Numpads[j].equals(caracter))
                index = j;
            j++;
        }
        buscarNumeroMorse(index);
    }
    public void buscarNumeroMorse(int index) {
        int k = 0;
        while (k < Numorse.length) {
            if (index == k)
                txtSalida.appendText(Numorse[k] + " ");

            k++;
        }
    }
    public void teclarDiferentes(String caracter) {
        switch (caracter) {
            case "ENTER":
                txtSalida.appendText("\n");
                break;
            case "BACK_SPACE":
                eliminarLetra();
                break;
            case "TAB":
                txtSalida.appendText("\t");
                break;
            case "SPACE":
                txtSalida.appendText(" ");
                break;
            case "DECIMAL":
                txtSalida.appendText(".-.-.-");
                break;
            case "QUOTE":
                txtSalida.appendText("..--..");
                break;
            case "COMMA":
                txtSalida.appendText(" --..--");
                break;
        }
    }
    public void traducirDocumento(char linea) {
        int k = 0;
        if (linea == '0' || linea == '1' || linea == '2' || linea == '3' || linea == '4' || linea == '5' || linea == '6' || linea == '7' || linea == '8' || linea == '9')
            traducirNumerosDocumento(linea);
        else {
            while (k < chars.length) {
                if (linea == chars[k])
                    txtSalida.appendText(morse[k] + " ");
                k++;
            }
        }
    }
    public void traducirNumerosDocumento(char linea) {
        int l = 0;
        while (l < numeros.length) {
            if (linea == numeros[l])
                txtSalida.appendText(Numorse[l] + " ");
            l++;
        }
    }
    public void limpiarTodo() {
        txtEntrada.setText(" ");
        txtSalida.setText(" ");
    }
    public void eliminarLetra() {
        encontrarUltimaLetra();
    }
    public void encontrarUltimaLetra() {
        String textoSalida=txtSalida.getText();
        String texto = txtEntrada.getText();
        char ultimaLetra = texto.toUpperCase().charAt(texto.length() - 1);
        int i = 0;
        int index = 0;
        while (i < chars.length) {
            if (ultimaLetra == chars[i])
                index = i;
            i++;
        }
        int i2 = 0;
        numCarTextSalida=textoSalida.length();
        while (i2 < morse.length) {
            if (index == i2) {
                System.out.println(ultimaLetra+"letra");
                System.out.println(morse[i2]+"letra en binario");
                cantidad+=morse[i2].length();
            }
            i2++;
        }
    }
}